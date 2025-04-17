package kr.hhplus.be.ecommerce.application.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.application.coupon.CouponService;
import kr.hhplus.be.ecommerce.application.order.OrderCriteria.CriteriaOrderProduct;
import kr.hhplus.be.ecommerce.application.point.PointService;
import kr.hhplus.be.ecommerce.domain.order.Order;
import kr.hhplus.be.ecommerce.domain.order.OrderCommand.CommandOrder;
import kr.hhplus.be.ecommerce.domain.order.OrderInfo.InfoOrder;
import kr.hhplus.be.ecommerce.domain.order.OrderProduct;
import kr.hhplus.be.ecommerce.domain.point.PointCommand;
import kr.hhplus.be.ecommerce.domain.point.UserPointRepository;
import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.user.User;
import kr.hhplus.be.ecommerce.interfaces.common.ApiResponse;
import kr.hhplus.be.ecommerce.interfaces.order.OrderResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderFacade {

	private final OrderService orderService;
	
	private final PointService pointService;
	
	private final UserPointRepository userPointRepository;
	
	private final CouponService couponService;

	@Transactional
	public InfoOrder createOrder(CommandOrder command) {

		User user = orderService.validateUser(command.getUserId());

		BigDecimal totalPrice = BigDecimal.ZERO;
		List<OrderProduct> orderProductList = new ArrayList<>();

		for (CriteriaOrderProduct item : command.getCriteriaOrderProduct()) {
			// 상품 조회
			Product product = orderService.validateProduct(item.getProductId());
			
			BigDecimal itemTotal = product.getProductPrice()
										  .multiply(BigDecimal.valueOf(item.getQuantity()));
			
			totalPrice = totalPrice.add(itemTotal);

			// 쿠폰은 전체에 적용하는걸 가정
			orderProductList.add(OrderProduct.builder().productId(product.getProductId())
													   .orderQuantity(item.getQuantity())
													   .discountPrice(0)
													   .productPrice(product.getProductPrice()).build());
		}

		// 쿠폰 할인 적용
		BigDecimal discount = couponService.applyCoupon(command.getCouponId(), totalPrice);
		BigDecimal afterCoupon = totalPrice.subtract(discount);

		// 포인트 차감
		BigDecimal availablePoint = userPointRepository.findByUserId(user.getUserId())
													   .getUserPoint();
		BigDecimal usePoint = afterCoupon.min(availablePoint);
		
		pointService.usePoint(PointCommand.Use.of(user.getUserId(), usePoint));

		BigDecimal finalPoint = afterCoupon.subtract(usePoint);

		// 주문 저장
		Order order = orderService.saveOrder(user, finalPoint);
		orderService.saveOrderProducts(order, orderProductList);
		orderService.saveOrderHistory(order);

		// 잔여 포인트
		BigDecimal remainingPoint = availablePoint.subtract(usePoint);

		return InfoOrder.of(order.getOrderId()
						  , user.getUserId()
						  , finalPoint
						  , remainingPoint
						  , discount
						  , usePoint);
	}
}
