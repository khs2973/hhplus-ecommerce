package kr.hhplus.be.ecommerce.application.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.domain.order.Order;
import kr.hhplus.be.ecommerce.domain.order.OrderProduct;
import kr.hhplus.be.ecommerce.domain.order.OrderProductRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderRepository;
import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductCommand;
import kr.hhplus.be.ecommerce.domain.product.ProductInfo;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;

@SpringBootTest
@Transactional
public class ProductServiceIntegrationTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderProductRepository orderProductRepository;
	
	private Integer productId;
	
	@BeforeEach
	void setUp() {
		
		Product product1 = Product.builder()
								 .productName("상품1")
								 .productPrice(new BigDecimal("10000"))
								 .stock(50)
								 .cdate(LocalDateTime.now())
								 .productState(1)
								 .build();
		
		productRepository.save(product1);
		productId = product1.getProductId();
		
		Product product2 = Product.builder()
								  .productName("키보드")
								  .productPrice(new BigDecimal("10000"))
								  .stock(100)
								  .productState(1)
								  .cdate(LocalDateTime.now().minusDays(1))
								  .build();

		productRepository.save(product2);
		
		Order order = Order.builder()
						   .userId("hanghae")
						   .totalPrice(0)
						   .address("서울")
						   .orderState(1)
						   .cdate(LocalDateTime.now().minusDays(1))
						   .build();
		orderRepository.save(order);

		OrderProduct op1 = OrderProduct.builder()
									   .orderId(order.getOrderId())
									   .productId(product1.getProductId())
									   .orderQuantity(5)
									   .productPrice(product1.getProductPrice())
									   .build();

		OrderProduct op2 = OrderProduct.builder()
									   .orderId(order.getOrderId())
									   .productId(product2.getProductId())
									   .orderQuantity(3)
									   .productPrice(product2.getProductPrice())
									   .build();

		orderProductRepository.save(op1);
		orderProductRepository.save(op2);
	}

	@Test
	@DisplayName("상품 조회 성공 (상품이 존재할 경우)")
	void successGetProduct() {
		
		ProductCommand.Create command = new ProductCommand.Create(productId);

		ProductInfo.Create result = productService.getProduct(command);

		assertThat(result).isNotNull();
		assertThat(result.getProductId()).isEqualTo(productId);
		assertThat(result.getProductName()).isEqualTo("상품1");
		
	}

	@Test
	@DisplayName("상품 조회 실패 (상품이 없을 경우)")
	void failGetProduct_notFound() {
		
		ProductCommand.Create command = new ProductCommand.Create(99);

		assertThatThrownBy(() -> productService.getProduct(command)).isInstanceOf(CustomException.class)
																	.hasMessageContaining(ErrorEnum.NOT_FOUND_PRODUCT.getMessage());
	}
	
	@Test
	@DisplayName("인기상품 상위 조회")
	void successTopRankProduct() {
		
		ProductCommand.TopRank command = ProductCommand.TopRank.of(2);

		ProductInfo.TopRankProductInfo result = productService.getTopRankProduct(command);

		assertThat(result.getProducts().get(0).getProductName()).isEqualTo("상품1");
		assertThat(result.getProducts().get(0).getTotalQuantity()).isEqualTo(5L);
		assertThat(result.getProducts().get(1).getProductName()).isEqualTo("키보드");
		assertThat(result.getProducts().get(1).getTotalQuantity()).isEqualTo(3L);
	}
}
