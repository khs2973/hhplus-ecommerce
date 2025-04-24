package kr.hhplus.be.ecommerce.domain.coupon;

import lombok.Getter;

public class CouponCommand {

	@Getter
	public static class CommandCoupon {
		
		private String userId;
		private Integer couponId;
		
		public CommandCoupon(String userId, Integer couponId) {
			this.userId = userId;
			this.couponId = couponId;
		}
		
		public static CommandCoupon of(String userId, Integer couponId) {
			return new CommandCoupon(userId, couponId);
		}
	}
	
}
