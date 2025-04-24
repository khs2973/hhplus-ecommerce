package kr.hhplus.be.ecommerce.domain.product;

import java.util.Optional;

public interface ProductRepository {
	
	Optional<Product> findByProductId(Integer productId);

	void save(Product product);
	
	Optional<Product> findByProductIdForUpdate(Integer productId);
	
}
