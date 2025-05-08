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
import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductCommand;
import kr.hhplus.be.ecommerce.domain.product.ProductInfo.InfoProduct;
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
	
	private Integer productId;
	
	@BeforeEach
	void setUp() {
		Product product = Product.builder()
								 .productName("상품1")
								 .productPrice(new BigDecimal("10000"))
								 .stock(50)
								 .cdate(LocalDateTime.now())
								 .productState(1)
								 .build();

		productRepository.save(product);
		productId = product.getProductId();
	}

	@Test
	@DisplayName("상품 조회 성공 (상품이 존재할 경우)")
	void successGetProduct() {
		
		ProductCommand.Create command = new ProductCommand.Create(productId);

		InfoProduct result = productService.getProduct(command);

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
}
