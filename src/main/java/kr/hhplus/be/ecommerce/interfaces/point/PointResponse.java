package kr.hhplus.be.ecommerce.interfaces.point;

import java.math.BigDecimal;

import kr.hhplus.be.ecommerce.domain.point.PointInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointResponse {
	
	private BigDecimal userPoint;
	
	public static PointResponse from (PointInfo.Point point) {
		return new PointResponse(point.getUserPoint());
	}
	
}
