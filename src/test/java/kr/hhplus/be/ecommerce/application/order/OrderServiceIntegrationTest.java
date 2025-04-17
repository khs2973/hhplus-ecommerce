package kr.hhplus.be.ecommerce.application.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.domain.order.Order;
import kr.hhplus.be.ecommerce.domain.order.OrderHistory;
import kr.hhplus.be.ecommerce.domain.order.OrderHistoryRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderProduct;
import kr.hhplus.be.ecommerce.domain.order.OrderProductRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderState;
import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;
import kr.hhplus.be.ecommerce.domain.user.UserRepository;

@SpringBootTest
@Transactional
public class OrderServiceIntegrationTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@Autowired
	private OrderHistoryRepository orderHistoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Test
	@DisplayName("상품 조회 통합 테스트 - 성공")
	void validateProduct_success() {
		
		Product product = Product.builder()
								 .productName("테스트 상품")
								 .productPrice(BigDecimal.valueOf(10000))
								 .stock(50)
								 .build();
		productRepository.save(product);

		Product result = orderService.validateProduct(product.getProductId());

		assertThat(result).isNotNull();
		assertThat(result.getProductName()).isEqualTo("테스트 상품");
	}

	@Test
	@DisplayName("주문 상품 저장 통합 테스트 - 성공")
	void saveOrderProducts_success() {
		// given
		Order order = Order.builder()
						   .userId("hanghae")
						   .totalPrice(20000)
						   .orderState(OrderState.ORDERED.getCode())
						   .cdate(LocalDateTime.now())
						   .address("서울시").build();
		
		orderRepository.save(order);

		OrderProduct orderProduct1 = OrderProduct.builder()
												 .productId(1)
												 .orderQuantity(2)
												 .productPrice(BigDecimal.valueOf(5000))
												 .build();

		OrderProduct orderProduct2 = OrderProduct.builder()
												 .productId(2)
												 .orderQuantity(1)
												 .productPrice(BigDecimal.valueOf(10000))
												 .build();

		orderService.saveOrderProducts(order, List.of(orderProduct1, orderProduct2));

		List<OrderProduct> orderProducts = orderProductRepository.findAll();
		assertThat(orderProducts).hasSize(2);
		assertThat(orderProducts.get(0).getOrderId()).isEqualTo(order.getOrderId());
	}

	@Test
	@DisplayName("주문 이력 저장 통합 테스트 - 성공")
	void saveOrderHistory_success() {
		// given
		Order order = Order.builder()
						   .userId("hanghae")
						   .totalPrice(10000)
						   .orderState(OrderState.ORDERED.getCode())
						   .address("서울")
						   .cdate(LocalDateTime.now())
						   .build();
		
		orderRepository.save(order);

		orderService.saveOrderHistory(order);

		List<OrderHistory> histories = orderHistoryRepository.findAll();
		assertThat(histories).hasSize(1);
		assertThat(histories.get(0).getOrderId()).isEqualTo(order.getOrderId());
	}
}
