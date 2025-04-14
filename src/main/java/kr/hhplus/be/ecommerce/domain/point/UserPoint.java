package kr.hhplus.be.ecommerce.domain.point;

import java.math.BigDecimal;

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

	// 최소, 최대 금액 validation
	public BigDecimal validateChargePoint(BigDecimal userPoint) {
		BigDecimal totalPoint = this.userPoint.add(userPoint);

		if (totalPoint.compareTo(MAX_POINT) > 0) {
			throw new CustomException(ErrorEnum.CHARGE_POINT_MAX);
		}

		if (totalPoint.compareTo(MIN_POINT) < 0) {
			throw new CustomException(ErrorEnum.CHARGE_POINT_MIN);
		}

		this.userPoint = totalPoint;
		
		return totalPoint;
	}
	
	// 유저 validation
	public void validateUserPoint() {
		if(userId == null) {
			throw new CustomException(ErrorEnum.NOT_FOUND_USER);
		}
	}
	
	// 금액 비교
	public void comparePoint(BigDecimal amountToUse) {
		if(userPoint.compareTo(amountToUse) < 0) {
			throw new CustomException(ErrorEnum.NOT_FOUND_USER_POINT);
		}
	}

}
