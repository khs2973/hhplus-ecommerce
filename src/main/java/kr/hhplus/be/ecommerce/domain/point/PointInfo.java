package kr.hhplus.be.ecommerce.domain.point;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PointInfo {
	
	@Getter
	public static class Point {
		private final BigDecimal userPoint;
		
		private Point(BigDecimal userPoint) {
			this.userPoint = userPoint;
		}
		
		public static Point of(BigDecimal userPoint) {
			return new Point(userPoint);
		}
	}
}
