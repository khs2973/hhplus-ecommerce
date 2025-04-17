package kr.hhplus.be.ecommerce.domain.product;

import java.util.Optional;

public interface ProductRepository {
	
	Optional<Product> findByProductId(Integer productId);
	
}
