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

		private InfoOrder(Integer orderId
						, String userId
						, BigDecimal finalPoint
						, BigDecimal remainingPoint) {
			
			this.orderId = orderId;
			this.userId = userId;
			this.finalPoint = finalPoint;
			this.remainingPoint = remainingPoint;
			
		}

		public static InfoOrder of(Integer orderId
								 , String userId
								 , BigDecimal finalPoint
								 , BigDecimal remainingPoint) {
			
			return new InfoOrder(orderId, userId, finalPoint, remainingPoint);
			
		}
		
	}
}
