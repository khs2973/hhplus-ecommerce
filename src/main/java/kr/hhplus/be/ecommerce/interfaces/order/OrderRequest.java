package kr.hhplus.be.ecommerce.interfaces.order;

import java.util.List;

import kr.hhplus.be.ecommerce.application.order.OrderCriteria.CriteriaOrderProduct;
import kr.hhplus.be.ecommerce.domain.order.OrderCommand.CommandOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
	
	private String userId;
	private Integer couponId;
	private List<CriteriaOrderProduct> criteriaOrderProduct;
	
	public CommandOrder toCommandOrder() {
		return CommandOrder.of(userId, couponId, criteriaOrderProduct);
	}
}