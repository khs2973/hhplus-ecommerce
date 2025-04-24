package kr.hhplus.be.ecommerce.domain.order;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository {
	void save(OrderProduct orderProduct);

	List<OrderProduct> findAll();
}
