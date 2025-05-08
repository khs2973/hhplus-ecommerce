package kr.hhplus.be.ecommerce.domain.order;

public interface OrderRepository{
	void save(Order orderProduct);
	
	void saveAndFlush(Order orderProduct);
}
