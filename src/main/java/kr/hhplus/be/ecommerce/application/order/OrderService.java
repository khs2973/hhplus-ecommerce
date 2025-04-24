package kr.hhplus.be.ecommerce.application.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.hhplus.be.ecommerce.application.coupon.CouponService;
import kr.hhplus.be.ecommerce.application.order.OrderCriteria.CriteriaOrderProduct;
import kr.hhplus.be.ecommerce.domain.coupon.Coupon;
import kr.hhplus.be.ecommerce.domain.order.Order;
import kr.hhplus.be.ecommerce.domain.order.OrderCommand.CommandOrder;
import kr.hhplus.be.ecommerce.domain.order.OrderHistory;
import kr.hhplus.be.ecommerce.domain.order.OrderHistoryRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderProduct;
import kr.hhplus.be.ecommerce.domain.order.OrderProductRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderState;
import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;
import kr.hhplus.be.ecommerce.domain.user.User;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	
	private final OrderHistoryRepository orderHistoryRepository;
	
	private final OrderProductRepository orderProductRepository;
	
	private final ProductRepository productRepository;
	
	private final CouponService couponService;

	public Product getProduct(Integer productId) {
		return productRepository.findByProductId(productId)
								.orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_PRODUCT));
	}

	public Order saveOrder(User user, BigDecimal totalPrice) {
		
		Order order = Order.builder()
						   .userId(user.getUserId())
						   .address(user.getAddress())
						   .totalPrice(totalPrice.intValue())
						   .orderState(OrderState.ORDERED.getCode())
						   .cdate(LocalDateTime.now())
						   .build();

		orderRepository.save(order);
		
		return order;
	}

	public void saveOrderProducts(Order order, List<OrderProduct> orderProducts) {
		for (OrderProduct op : orderProducts) {
			op.setOrderId(order.getOrderId());
			orderProductRepository.save(op);
		}
	}

	public void saveOrderHistory(Order order) {
		OrderHistory history = OrderHistory.builder()
										   .orderId(order.getOrderId())
										   .userId(order.getUserId())
										   .totalPrice(order.getTotalPrice())
										   .orderState(order.getOrderState())
										   .cdate(LocalDateTime.now())
										   .build();

		orderHistoryRepository.save(history);
	
	}
	
	public List<OrderProduct> orderProducts(CommandOrder commandOrder) {
		
		List<OrderProduct> orderProducts = new ArrayList<>();
		
		for (CriteriaOrderProduct criteria : commandOrder.getCriteriaOrderProduct()) {
			
			// 쿠폰 조회
			Coupon coupon = couponService.getCoupon(commandOrder.getCouponId());
			
			// 상품 조회
			Product product = getProduct(criteria.getProductId());
			
			// 사용자 요청 수량과 상품 재고 검증
			product.validationProductStock(criteria.getQuantity(), product.getStock());
			
			// 상품의 총금액(쿠폰x)
			BigDecimal productTotalPrice = product.getProductPrice()
												  .multiply(BigDecimal.valueOf(criteria.getQuantity()));
			
			// 상품의 총금액(쿠폰o)
			BigDecimal discountTotalPrice = coupon.calculateDiscount(productTotalPrice);
			
			// 상품 재고 차감
			productRepository.save(product);
			
			// 상품 주문
			orderProducts.add(OrderProduct.builder()
										  .productId(product.getProductId())
										  .couponId(coupon.getCouponId())
										  .orderQuantity(criteria.getQuantity())
										  .totalPrice(discountTotalPrice)
										  .productPrice(product.getProductPrice())
										  .build());
		}
		
		return orderProducts;
		
	}
	
	public BigDecimal caculateTotalPrice(List<OrderProduct> products) {
		return products.stream()
						.map(OrderProduct::getTotalPrice)
						.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
