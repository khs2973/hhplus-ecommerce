package kr.hhplus.be.ecommerce.domain.order;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderInfo {
	
	@Getter
	public static class InfoOrder {
		
		private final Integer orderId;
		private final String userId;
		private final BigDecimal finalPoint;
		private final BigDecimal remainingPoint;
		private final BigDecimal discount;
		private final BigDecimal usePoint;

		private InfoOrder(Integer orderId
						, String userId
						, BigDecimal finalPoint
						, BigDecimal remainingPoint
						, BigDecimal discount
						, BigDecimal usePoint) {
			
			this.orderId = orderId;
			this.userId = userId;
			this.finalPoint = finalPoint;
			this.remainingPoint = remainingPoint;
			this.discount = discount;
			this.usePoint = usePoint;
			
		}

		public static InfoOrder of(Integer orderId
								 , String userId
								 , BigDecimal finalPoint
								 , BigDecimal remainingPoint
								 , BigDecimal discount
								 , BigDecimal usedPoint) {
			
			return new InfoOrder(orderId, userId, finalPoint, remainingPoint, discount, usedPoint);
			
		}
		
	}
}
