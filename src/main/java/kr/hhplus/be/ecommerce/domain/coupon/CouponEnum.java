package kr.hhplus.be.ecommerce.domain.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponEnum {
	DELETE("삭제"),
	ACTIVE("사용 가능"),
	EXPIRED("만료"),
	PERCENT("퍼센트 할인"),
	FIXED("정액 할인");

	private final String desc;
	
	public String getCouponStateEnum() {
		return desc;
	}
}
