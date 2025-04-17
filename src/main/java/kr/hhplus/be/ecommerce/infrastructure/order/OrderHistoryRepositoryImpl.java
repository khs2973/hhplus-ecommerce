package kr.hhplus.be.ecommerce.infrastructure.order;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.ecommerce.domain.order.OrderHistory;
import kr.hhplus.be.ecommerce.domain.order.OrderHistoryRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderHistoryRepositoryImpl implements OrderHistoryRepository{

	private OrderHistoryJpaRepository orderHistoryJpaRepository;
	
	@Override
	public void save(OrderHistory orderHistory) {
		orderHistoryJpaRepository.save(orderHistory);
	}
}
