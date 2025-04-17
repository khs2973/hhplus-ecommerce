package kr.hhplus.be.ecommerce.domain.order;

import java.util.List;

public interface OrderProductRepository {
	void save(OrderProduct orderProduct);

	List<OrderProduct> findAll();
}
