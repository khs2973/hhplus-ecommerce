package kr.hhplus.be.ecommerce.domain.point;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_point")
public class UserPoint {

	private static final BigDecimal MIN_POINT = new BigDecimal(5000);
	private static final BigDecimal MAX_POINT = new BigDecimal(20000);

	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "user_point")
	private BigDecimal userPoint;

	public UserPoint(String userId, BigDecimal userPoint) {
		this.userId = userId;
		this.userPoint = userPoint;
	}

	// 유저 validation
	public void validateUserPoint() {
		if(userId == null) {
			throw new CustomException(ErrorEnum.NOT_FOUND_USER);
		}
	}
	
	// 포인트 충전 (유효성 검사 포함)
	public PointHistory charge(BigDecimal chargePoint) {
		
		validateChargePoint(chargePoint);

		BigDecimal beforePoint = this.userPoint;
		this.userPoint = this.userPoint.add(chargePoint);
		BigDecimal afterPoint = this.userPoint;

		return PointHistory.builder()
						   .userId(this.userId)
						   .beforePoint(beforePoint)
						   .afterPoint(afterPoint)
						   .pointType(PointEnum.CHARGE.name())
						   .cdate(LocalDateTime.now())
						   .build();
	}

	// 포인트 사용 (유효성 검사 포함)
	public PointHistory use(BigDecimal amount) {
		
		validateEnoughPoint(amount);

		BigDecimal beforePoint = this.userPoint;
		this.userPoint = this.userPoint.subtract(amount);
		BigDecimal afterPoint = this.userPoint;

		return PointHistory.builder()
						   .userId(this.userId)
						   .beforePoint(beforePoint)
						   .afterPoint(afterPoint)
						   .pointType(PointEnum.USE.name())
						   .cdate(LocalDateTime.now())
						   .build();
	}

	// 최대, 최소 충전 포인트 validation
	public void validateChargePoint(BigDecimal amount) {
		
		if (amount.compareTo(MAX_POINT) > 0) {
			throw new CustomException(ErrorEnum.CHARGE_POINT_MAX);
		}
		if (amount.compareTo(MIN_POINT) < 0) {
			throw new CustomException(ErrorEnum.CHARGE_POINT_MIN);
		}
		
	}

	// 포인트가 부족할때 validation
	public void validateEnoughPoint(BigDecimal useAmount) {
		
		if (this.userPoint.compareTo(useAmount) < 0) {
			throw new CustomException(ErrorEnum.NOT_ENOUGH_POINT);
		}
		
	}

}
