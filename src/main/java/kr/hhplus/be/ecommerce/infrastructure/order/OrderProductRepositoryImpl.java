package kr.hhplus.be.ecommerce.infrastructure.order;

import kr.hhplus.be.ecommerce.domain.order.OrderProductRepository;
import kr.hhplus.be.ecommerce.domain.point.PointHistoryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import kr.hhplus.be.ecommerce.domain.order.OrderProduct;

@Component
public class OrderProductRepositoryImpl implements OrderProductRepository{
	
	@Override
	public void save(OrderProduct orderProduct) {

	}
	
}
