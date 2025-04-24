package kr.hhplus.be.ecommerce.domain.coupon;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_coupon")
public class UserCoupon {
	@Id
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "coupon_id")
	private Integer couponId;
	
	@Column(name = "cdate")
	private LocalDateTime cdate;
	
	@Column(name = "used_state")
	private Integer usedState;
}
