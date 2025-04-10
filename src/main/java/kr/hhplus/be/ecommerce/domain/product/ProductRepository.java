package kr.hhplus.be.ecommerce.domain.product;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository {
	
	Optional<Product> findByProductIdAndState(Integer productId, Integer productState);
	
}
