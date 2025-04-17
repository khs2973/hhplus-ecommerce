package kr.hhplus.be.ecommerce.infrastructure.order;

import kr.hhplus.be.ecommerce.domain.order.OrderProductRepository;
import kr.hhplus.be.ecommerce.domain.point.PointHistoryRepository;
import kr.hhplus.be.ecommerce.infrastructure.point.UserPointJpaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import kr.hhplus.be.ecommerce.domain.order.OrderProduct;

@Repository
@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepository{
	
	private OrderProductJpaRepository orderProductJpaRepository;
	
	@Override
	public void save(OrderProduct orderProduct) {
		orderProductJpaRepository.save(orderProduct);
	}
	
	@Override
	public List<OrderProduct> findAll() {
		return orderProductJpaRepository.findAll();
	}
	
}
