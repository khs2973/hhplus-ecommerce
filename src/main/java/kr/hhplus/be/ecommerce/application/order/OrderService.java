package kr.hhplus.be.ecommerce.application.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.hhplus.be.ecommerce.domain.order.Order;
import kr.hhplus.be.ecommerce.domain.order.OrderHistory;
import kr.hhplus.be.ecommerce.domain.order.OrderHistoryRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderProduct;
import kr.hhplus.be.ecommerce.domain.order.OrderProductRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderState;
import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;
import kr.hhplus.be.ecommerce.domain.user.User;
import kr.hhplus.be.ecommerce.infrastructure.user.UserRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import kr.hhplus.be.ecommerce.interfaces.order.OrderRequest;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderHistoryRepository orderHistoryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderProductRepository orderProductRepository;
	
	@Autowired
	ProductRepository productRepository;

	public Product validateProduct(Integer productId) {
		return productRepository.findByProductIdAndState(productId, 1)
								.orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_PRODUCT));
	}
	
	public Order createOrder(OrderRequest orderRequest, Product product) {

		BigDecimal totalProductPrice = product.getProductPrice()
											  .multiply(BigDecimal.valueOf(orderRequest.getOrderQuantity()));

		BigDecimal totalPrice = totalProductPrice.subtract(orderRequest.getDiscountPrice())
												 .subtract(orderRequest.getProductPrice());

		if (totalPrice.compareTo(BigDecimal.ZERO) < 0) {
			totalPrice = BigDecimal.ZERO;
		}

		User user = userRepository.findByUserId(orderRequest.getUserId())
								  .orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_USER));

		Order order = Order.builder()
						   .userId(orderRequest.getUserId())
						   .totalPrice(totalPrice.intValue())
						   .address(user.getAddress())
						   .cdate(LocalDateTime.now())
						   .orderState(OrderState.ORDERED.getCode())
						   .build();

		orderRepository.save(order);

		OrderProduct orderProduct = OrderProduct.builder()
												.orderId(order.getOrderId())
												.productId(product.getProductId())
												.orderQuantity(orderRequest.getOrderQuantity())
												.discountPrice(orderRequest.getDiscountPrice().intValue())
												.productPrice(product.getProductPrice())
												.build();

		orderProductRepository.save(orderProduct);

		return order;
	}

	public void recordOrderHistory(Order order) {

		OrderHistory orderHistory = OrderHistory.builder()
												.orderId(order.getOrderId())
												.userId(order.getUserId())
												.totalPrice(order.getTotalPrice())
												.orderState(order.getOrderState())
												.cdate(LocalDateTime.now())
												.build();

		orderHistoryRepository.save(orderHistory);
	}
}
