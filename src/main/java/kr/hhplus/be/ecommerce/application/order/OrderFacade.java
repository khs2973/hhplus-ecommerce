package kr.hhplus.be.ecommerce.application.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.application.point.PointService;
import kr.hhplus.be.ecommerce.application.user.UserService;
import kr.hhplus.be.ecommerce.domain.order.Order;
import kr.hhplus.be.ecommerce.domain.order.OrderCommand;
import kr.hhplus.be.ecommerce.domain.order.OrderInfo.InfoOrder;
import kr.hhplus.be.ecommerce.domain.order.OrderProduct;
import kr.hhplus.be.ecommerce.domain.point.PointCommand;
import kr.hhplus.be.ecommerce.domain.point.UserPoint;
import kr.hhplus.be.ecommerce.domain.user.User;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderFacade {

	private final OrderService orderService;

	private final PointService pointService;

	private final UserService userService;

	private final RedissonClient redissonClient;

	@Transactional
	public InfoOrder createOrder(OrderCommand.Create orderCommand) {

		List<RLock> locks = orderCommand.getCriteriaOrderProduct()
										.stream()
										.map(c -> redissonClient.getLock("lock:product:" + c.getProductId()))
										.collect(Collectors.toList());
		
		// 회원 조회
		User userInfo = userService.getUser(orderCommand.getUserId());
		
		// 포인트 조회
		UserPoint userPoint = pointService.searchUserPoint(orderCommand.getUserId());
		
		BigDecimal currentUserPoint = userPoint.getUserPoint();
		
		try {
			
			for (RLock lock : locks) {
				boolean available = lock.tryLock(5, 3, TimeUnit.SECONDS);
				if (!available) {
					throw new CustomException(ErrorEnum.FAIL_GET_LOCK);
				}
			}

			// 상품 주문, 쿠폰 할인 계산
			List<OrderProduct> orderProducts = orderService.orderProducts(orderCommand);
			
			// 상품의 총 금액
			BigDecimal totalPrice = orderService.caculateTotalPrice(orderProducts);
			
			// 포인트 차감
			pointService.usePoint(PointCommand.Use.of(userInfo.getUserId(), totalPrice));
			
			// 실제 결제 금액
			BigDecimal paymentAmount = totalPrice;
			BigDecimal afterUserPoint = currentUserPoint.subtract(totalPrice);

			// 주문 저장
			Order order = orderService.saveOrder(userInfo, totalPrice);
			orderService.saveOrderProducts(order, orderProducts);
			orderService.saveOrderHistory(order);

			return InfoOrder.of(order.getOrderId()
							  , userInfo.getUserId()
							  , paymentAmount
							  , afterUserPoint);

		} catch (InterruptedException e) {
			throw new CustomException(ErrorEnum.FAIL_GET_LOCK);
		}
		
	}
	
}
