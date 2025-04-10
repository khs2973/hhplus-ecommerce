package kr.hhplus.be.ecommerce.interfaces.point;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointResponse {
	
	private String userId;
	private BigDecimal userPoint;
	private BigDecimal totalPoint;
	
	public static PointResponse chargeUserPoint(String userId, BigDecimal userPoint, BigDecimal totalPoint) {
		return PointResponse.builder()
							.userId(userId)
							.userPoint(userPoint)
							.totalPoint(totalPoint)
							.build();
	}
		
	public static PointResponse getUserPoint(String userId, BigDecimal usePoint) {
		return PointResponse.builder()
							.userId(userId)
							.totalPoint(usePoint)
							.build();
	}
	
}
