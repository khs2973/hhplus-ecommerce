package kr.hhplus.be.ecommerce.domain.coupon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coupon")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "couponId")
	private Integer couponId;

	@Column(name = "coupon_type")
	private String couponType;

	@Column(name = "coupon_name")
	private String couponName;

	@Column(name = "coupon_stock")
	private Integer couponStock;

	@Column(name = "discount")
	private Integer discount;

	@Column(name = "start_date")
	private LocalDateTime startDate;

	@Column(name = "end_date")
	private LocalDateTime endDate;

	@Column(name = "coupon_state")
	private Integer couponState;

	// 할인 금액 반환
	public BigDecimal calculateDiscount(BigDecimal totalPrice) {
		
		validateUsable();
		
		if(this.couponType.equals(CouponEnum.PERCENT.getDesc())) {
			BigDecimal discountRate = BigDecimal.valueOf(this.discount)
												.divide(BigDecimal.valueOf(100));
			return totalPrice.multiply(BigDecimal.ONE.subtract(discountRate));
		} else {
			return BigDecimal.valueOf(discount);
		}
		
	}

	// 사용 가능 검증
	public void validateUsable() {

		if (this.couponState == 2) {
			throw new CustomException(ErrorEnum.ALREADY_USED_COUPON);
		}

		LocalDateTime now = LocalDateTime.now();
		if (now.isBefore(this.startDate) || now.isAfter(this.endDate)) {
			throw new CustomException(ErrorEnum.EXPIRED_COUPON);
		}
	}

	// 쿠폰 사용처리
	public void markAsUsed() {
		this.couponState = 1;
		this.couponStock -= 1;
	}

}
