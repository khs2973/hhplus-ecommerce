package kr.hhplus.be.ecommerce.application.order;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.application.coupon.CouponService;
import kr.hhplus.be.ecommerce.application.point.PointService;
import kr.hhplus.be.ecommerce.application.user.UserService;
import kr.hhplus.be.ecommerce.domain.order.Order;
import kr.hhplus.be.ecommerce.domain.order.OrderCommand.CommandOrder;
import kr.hhplus.be.ecommerce.domain.order.OrderInfo.InfoOrder;
import kr.hhplus.be.ecommerce.domain.order.OrderProduct;
import kr.hhplus.be.ecommerce.domain.point.PointCommand;
import kr.hhplus.be.ecommerce.domain.point.UserPoint;
import kr.hhplus.be.ecommerce.domain.point.UserPointRepository;
import kr.hhplus.be.ecommerce.domain.user.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderFacade {

	private final OrderService orderService;

	private final PointService pointService;

	private final UserService userService;

	@Transactional
	public InfoOrder createOrder(CommandOrder commandOrder) {

		// 회원 조회
		User userInfo = userService.getUser(commandOrder.getUserId());
		
		// 포인트 조회
		UserPoint userPoint = pointService.searchUserPoint(commandOrder.getUserId());
		
		BigDecimal currentUserPoint = userPoint.getUserPoint();
		
		// 상품 주문, 쿠폰 할인 계산
		List<OrderProduct> orderProducts = orderService.orderProducts(commandOrder);

		// 상품의 총 금액
		BigDecimal totalPrice = orderService.caculateTotalPrice(orderProducts);

		// 포인트 차감
		pointService.usePoint(PointCommand.Use.of(userInfo.getUserId(), totalPrice));

		// 실제 결제 금액
		BigDecimal paymentAmount = totalPrice.subtract(currentUserPoint);
		BigDecimal afterUserPoint = currentUserPoint.subtract(totalPrice);
		
		// 주문 저장
		Order order = orderService.saveOrder(userInfo, totalPrice);
		orderService.saveOrderProducts(order, orderProducts);
		orderService.saveOrderHistory(order);

		return InfoOrder.of(order.getOrderId()
						  , userInfo.getUserId()
						  , paymentAmount
						  , afterUserPoint);
	}
}
