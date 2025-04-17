package kr.hhplus.be.ecommerce.infrastructure.product;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hhplus.be.ecommerce.domain.product.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Integer>{

}
