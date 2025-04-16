package kr.hhplus.be.ecommerce.application.product;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import kr.hhplus.be.ecommerce.domain.product.ProductRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceTest {

	@Mock
	ProductRepository productRepository;
	
	@Test
	@DisplayName("상품 조회시 결과가 없을 경우")
	void notFoundProduct() {
		
		Integer productId = 1;
		
		when(productRepository.findByProductIdAndState(productId, 1)).thenReturn(Optional.empty());
		
	}
	
}
