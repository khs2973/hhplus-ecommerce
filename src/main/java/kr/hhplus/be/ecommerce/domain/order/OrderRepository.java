package kr.hhplus.be.ecommerce.domain.order;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {
	
	void save(Order order);
	
}
