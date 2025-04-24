package kr.hhplus.be.ecommerce.interfaces.coupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoupontRequest {
	
	String userId;
	Integer couponId;
	
}
