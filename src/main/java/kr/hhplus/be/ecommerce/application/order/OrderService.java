package kr.hhplus.be.ecommerce.application.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
import kr.hhplus.be.ecommerce.domain.user.UserRepository;
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
	
	private final UserRepository userRepository;

	public Product validateProduct(Integer productId) {
		return productRepository.findByProductId(productId)
								.orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_PRODUCT));
	}

	public User validateUser(String userId) {
		return userRepository.findByUserId(userId)
							 .orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_USER));
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
}
