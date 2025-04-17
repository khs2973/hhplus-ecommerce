package kr.hhplus.be.ecommerce.domain.coupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DynamicUpdate
@NoArgsConstructor
@Table(name = "coupon")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "couponId")
	private Integer couponId;

	@Column(name = "coupon_type")
	private Integer couponType;

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

	@Column(name = "coupont_state")
	private Integer couponState;

	// 할인 금액 반환
	public BigDecimal calculateDiscount(BigDecimal totalPrice) {
		validateUsable();
		BigDecimal discountAmount = BigDecimal.valueOf(discount);
		return discountAmount.min(totalPrice);
	}

	// 사용 가능 검증
	public void validateUsable() {

		if (this.couponState == 1) {
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
