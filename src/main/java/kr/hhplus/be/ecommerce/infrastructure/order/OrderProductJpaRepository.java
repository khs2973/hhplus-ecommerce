package kr.hhplus.be.ecommerce.infrastructure.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.hhplus.be.ecommerce.domain.order.OrderProduct;

public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long>{
}
