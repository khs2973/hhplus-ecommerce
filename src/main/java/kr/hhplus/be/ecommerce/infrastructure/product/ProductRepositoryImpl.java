package kr.hhplus.be.ecommerce.infrastructure.product;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository{
	
	private final ProductJpaRepository productJpaRepository;
	
	@Override
	public Optional<Product> findByProductId(Integer productId) {
		return productJpaRepository.findById(productId);
	}
	
	@Override
	public void save(Product product) {
		productJpaRepository.save(product);
	}
	
	@Override
 	public Optional<Product> findByProductIdForUpdate(Integer productId) {
 		return productJpaRepository.findByProductIdForUpdate(productId);
 	}
	
}
