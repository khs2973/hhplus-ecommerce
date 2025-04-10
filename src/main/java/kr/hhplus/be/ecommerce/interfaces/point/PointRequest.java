package kr.hhplus.be.ecommerce.interfaces.point;

import java.math.BigDecimal;

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
}
