package kr.hhplus.be.ecommerce.infrastructure.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.ecommerce.domain.product.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Integer>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
 	@Query("select p from Product p where p.productId = :productId")
 	Optional<Product> findByProductIdForUpdate(@Param("productId") Integer productId);
 	
}
