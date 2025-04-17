package kr.hhplus.be.ecommerce.infrastructure.order;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hhplus.be.ecommerce.domain.order.OrderHistory;

public interface OrderHistoryJpaRepository extends JpaRepository<OrderHistory, Integer>{

}
