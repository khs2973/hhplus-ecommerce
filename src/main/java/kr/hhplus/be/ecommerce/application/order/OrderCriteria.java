package kr.hhplus.be.ecommerce.application.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class OrderCriteria {

	@Getter
	@AllArgsConstructor
	public static class CriteriaOrderProduct {
		private Integer productId;
		private Integer quantity;
	}
}
