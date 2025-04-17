package kr.hhplus.be.ecommerce.domain.order;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository {
	
	void save(OrderHistory orderHistory);
	
	List<OrderHistory> findAll();
}
