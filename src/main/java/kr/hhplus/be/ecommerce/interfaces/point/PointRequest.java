package kr.hhplus.be.ecommerce.interfaces.point;

import java.math.BigDecimal;

import kr.hhplus.be.ecommerce.domain.point.PointCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointRequest {
	
	private String userId;
	private BigDecimal userPoint;
	
	public PointCommand.Charge toChargeCommand() {
		return PointCommand.Charge.of(userId, userPoint);
	}
	
	public PointCommand.Use toUseCommand() {
		return PointCommand.Use.of(userId, userPoint);
	}
	
}
