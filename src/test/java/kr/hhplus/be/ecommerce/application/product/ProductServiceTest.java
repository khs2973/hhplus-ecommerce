package kr.hhplus.be.ecommerce.application.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductCommand;
import kr.hhplus.be.ecommerce.domain.product.ProductInfo;
import kr.hhplus.be.ecommerce.domain.product.ProductOrderRank;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductService productService;

	@Test
	@DisplayName("상품 조회시 결과가 없을 경우")
	void notFoundProduct() {

		Integer productId = 1;

		when(productRepository.findByProductId(productId)).thenReturn(Optional.empty());

	}

	@Test
	@DisplayName("상폼 조회 성공")
	void successGetProduct() {
		
		Integer productId = 1;
		ProductCommand.Create command = new ProductCommand.Create(productId);

		Product product = Product.builder()
								 .productId(productId)
								 .productName("상품1")
								 .productPrice(new BigDecimal("10000"))
								 .stock(50)
								 .build();

		when(productRepository.findByProductId(productId)).thenReturn(Optional.of(product));

		ProductInfo.Create result = productService.getProduct(command);

		assertThat(result).isNotNull();
		assertThat(result.getProductId()).isEqualTo(productId);
		assertThat(result.getProductName()).isEqualTo("상품1");
		assertThat(result.getProductPrice()).isEqualByComparingTo("10000");
		assertThat(result.getStock()).isEqualTo(50);
	}
	
	@Test
	@DisplayName("인기상품 조회")
	void successTopRankProduct() {
		
		int dateType = 2; 
		int expectedDays = 7;

		ProductCommand.TopRank topRankCommand = ProductCommand.TopRank.of(dateType);

		List<ProductOrderRank> mockResult = List.of(new ProductOrderRank(1, "마우스", 50),
													new ProductOrderRank(2, "키보드", 40));

		when(productRepository.findByCdateBetween(expectedDays)).thenReturn(mockResult);

		ProductInfo.TopRankProductInfo result = productService.getTopRankProduct(topRankCommand);

		assertThat(result.getProducts()).hasSize(2);
		assertThat(result.getProducts().get(0).getProductName()).isEqualTo("마우스");
		assertThat(result.getProducts().get(1).getTotalQuantity()).isEqualTo(40L);

		Mockito.verify(productRepository).findByCdateBetween(expectedDays);
	}

}
