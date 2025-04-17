package kr.hhplus.be.ecommerce.application.order;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kr.hhplus.be.ecommerce.domain.order.Order;
import kr.hhplus.be.ecommerce.domain.order.OrderHistory;
import kr.hhplus.be.ecommerce.domain.order.OrderHistoryRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderProductRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderState;
import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;
import kr.hhplus.be.ecommerce.infrastructure.user.UserRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;

public class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderHistoryRepository orderHistoryRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private OrderProductRepository orderProductRepository;

	@Mock
	private ProductRepository productRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("상품 검증 성공")
	void validateProduct_success() {
		
//		int productId = 1;
//		
//		Product product = mock(Product.class);
//
//		when(productRepository.findByProductIdAndState(productId, 1)).thenReturn(Optional.of(product));
//
//		Product result = orderService.validateProduct(productId);
//
//		assertThat(result).isNotNull();
//		
//		verify(productRepository, times(1)).findByProductIdAndState(productId, 1);
	}

	@Test
	@DisplayName("상품 검증 실패 - 존재하지 않는 상품")
	void validateProduct_fail_notFound() {
		
//		int productId = 999;
//
//		when(productRepository.findByProductIdAndState(productId, 1)).thenReturn(Optional.empty());
//
//		assertThatThrownBy(() -> orderService.validateProduct(productId)).isInstanceOf(CustomException.class)
//																		 .hasMessageContaining(ErrorEnum.NOT_FOUND_PRODUCT.getMessage());
	}

	@Test
	@DisplayName("주문 이력 저장")
	void recordOrderHistory_success() {
		
//		Order order = mock(Order.class);
//		when(order.getOrderId()).thenReturn(1);
//		when(order.getUserId()).thenReturn("testUser");
//		when(order.getTotalPrice()).thenReturn(10000);
//		when(order.getOrderState()).thenReturn(OrderState.ORDERED.getCode());
//
//		orderService.recordOrderHistory(order);
//
//		verify(orderHistoryRepository, times(1)).save(any(OrderHistory.class));
	}
}
