package kr.hhplus.be.ecommerce.infrastructure.order;

import kr.hhplus.be.ecommerce.domain.order.Order;
import kr.hhplus.be.ecommerce.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository{
	
	private final OrderJpaRepository orderJpaRepository;
	
	@Override
	public void save(Order order) {
		orderJpaRepository.save(order);
	}
	
}
