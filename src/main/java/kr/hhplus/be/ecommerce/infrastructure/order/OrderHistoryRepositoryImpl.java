package kr.hhplus.be.ecommerce.infrastructure.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.ecommerce.domain.order.OrderHistory;
import kr.hhplus.be.ecommerce.domain.order.OrderHistoryRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderHistoryRepositoryImpl implements OrderHistoryRepository{

	private final OrderHistoryJpaRepository orderHistoryJpaRepository;
	
	@Override
	public void save(OrderHistory orderHistory) {
		orderHistoryJpaRepository.save(orderHistory);
	}
	
	@Override
	public List<OrderHistory> findAll() {
		return orderHistoryJpaRepository.findAll();
	}
}
