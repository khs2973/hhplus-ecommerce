package kr.hhplus.be.ecommerce.domain.point;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PointCommand {

	@Getter
	public static class Charge {
		private final String userId;
		private final BigDecimal userPoint;
		
		private Charge(String userId, BigDecimal userPoint) {
			this.userId = userId;
			this.userPoint = userPoint;
		}

		public static Charge of(String userId, BigDecimal userPoint) {
			return new Charge(userId, userPoint);
		}
	}
	
	@Getter
	public static class Use {
		
		private final String userId;
		private final BigDecimal userPoint;
		
		private Use(String userId, BigDecimal userPoint) {
			this.userId = userId;
			this.userPoint = userPoint;
		}

		public static Use of(String userId, BigDecimal userPoint) {
			return new Use(userId, userPoint);
		}
	}

	
}
