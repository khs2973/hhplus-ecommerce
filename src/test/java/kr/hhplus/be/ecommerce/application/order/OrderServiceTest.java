package kr.hhplus.be.ecommerce.application.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.hhplus.be.ecommerce.application.user.UserService;
import kr.hhplus.be.ecommerce.domain.order.Order;
import kr.hhplus.be.ecommerce.domain.order.OrderHistory;
import kr.hhplus.be.ecommerce.domain.order.OrderHistoryRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderProduct;
import kr.hhplus.be.ecommerce.domain.order.OrderProductRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderRepository;
import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;
import kr.hhplus.be.ecommerce.domain.user.User;
import kr.hhplus.be.ecommerce.domain.user.UserRepository;
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private OrderHistoryRepository orderHistoryRepository;
	
	@Mock
	private OrderProductRepository orderProductRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;

	@Test
	@DisplayName("상품 조회 성공")
	void validateProduct_success() {
		Product product = Product.builder()
								 .productId(1)
								 .productName("상품")
								 .productPrice(BigDecimal.valueOf(10000))
								 .stock(10)
								 .build();

		when(productRepository.findByProductIdForUpdate(product.getProductId())).thenReturn(Optional.of(product));

		Product result = orderService.getProduct(product.getProductId());

		assertThat(result).isNotNull();
		assertThat(result.getProductName()).isEqualTo("상품");
	}

	@Test
	@DisplayName("사용자 조회 성공")
	void validateUser_success() {
		
		User user = User.builder()
						.userId("hanghae")
						.address("서울시 광진구")
						.cdate(LocalDateTime.now())
						.build();
		
		when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(user));

		User result = userService.getUser(user.getUserId());

		assertThat(result.getAddress()).isEqualTo(user.getAddress());
	}

	@Test
	@DisplayName("주문 저장 성공")
	void saveOrder_success() {
		User user = new User("hanghae", "서울시 광진구", LocalDateTime.now());
		BigDecimal price = BigDecimal.valueOf(15000);

		Order savedOrder = orderService.saveOrder(user, price);

		assertThat(savedOrder.getUserId()).isEqualTo("hanghae");
		assertThat(savedOrder.getTotalPrice()).isEqualTo(15000);
	}

	@Test
	@DisplayName("주문 상품 저장 성공")
	void saveOrderProducts_success() {
		
		Order order = Order.builder()
						   .orderId(100)
						   .build();
		
		OrderProduct p1 = OrderProduct.builder()
									  .productId(1)
									  .orderQuantity(2)
									  .productPrice(BigDecimal.valueOf(5000))
									  .build();
		
		OrderProduct p2 = OrderProduct.builder()
									  .productId(2)
									  .orderQuantity(1)
									  .productPrice(BigDecimal.valueOf(10000))
									  .build();

		orderService.saveOrderProducts(order, List.of(p1, p2));

		verify(orderProductRepository, times(2)).save(any(OrderProduct.class));
		assertThat(p1.getOrderId()).isEqualTo(100);
		assertThat(p2.getOrderId()).isEqualTo(100);
	}

	@Test
	@DisplayName("주문 이력 저장 성공")
	void saveOrderHistory_success() {
		Order order = Order.builder().orderId(1).userId("user1").totalPrice(10000).orderState(0).build();

		orderService.saveOrderHistory(order);

		ArgumentCaptor<OrderHistory> captor = ArgumentCaptor.forClass(OrderHistory.class);
		verify(orderHistoryRepository).save(captor.capture());

		OrderHistory saved = captor.getValue();
		assertThat(saved.getOrderId()).isEqualTo(1);
		assertThat(saved.getUserId()).isEqualTo("user1");
	}
}
