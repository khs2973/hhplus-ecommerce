package kr.hhplus.be.ecommerce.domain.order;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository {
	void save(OrderProduct orderProduct);
}
