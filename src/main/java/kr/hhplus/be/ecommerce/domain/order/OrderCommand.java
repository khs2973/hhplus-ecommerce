package kr.hhplus.be.ecommerce.domain.order;

import java.util.List;

import kr.hhplus.be.ecommerce.application.order.OrderCriteria.CriteriaOrderProduct;
import lombok.Getter;

public class OrderCommand {
	
	@Getter
	public static class Create {
		
		private String userId;
		private Integer couponId;
		private List<CriteriaOrderProduct> criteriaOrderProduct;
		
		public Create(String userId, Integer couponId, List<CriteriaOrderProduct> criteriaOrderProduct) {
			this.userId = userId;
			this.couponId = couponId;
			this.criteriaOrderProduct = criteriaOrderProduct;
		}
		
		public static Create of(String userId, Integer couponId, List<CriteriaOrderProduct> criteriaOrderProduct) {
			return new Create(userId, couponId, criteriaOrderProduct);
		}
		
	}
}
