package kr.hhplus.be.ecommerce.domain.order;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository {
	
	void save(OrderHistory orderHistory);
	
}
