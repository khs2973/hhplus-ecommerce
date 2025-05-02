package kr.hhplus.be.ecommerce.domain.coupon;

import lombok.Getter;

public class CouponCommand {

	@Getter
	public static class Create {
		
		private String userId;
		private Integer couponId;
		
		public Create(String userId, Integer couponId) {
			this.userId = userId;
			this.couponId = couponId;
		}
		
		public static Create of(String userId, Integer couponId) {
			return new Create(userId, couponId);
		}
	}
	
}
