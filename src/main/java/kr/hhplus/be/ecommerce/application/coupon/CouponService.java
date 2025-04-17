package kr.hhplus.be.ecommerce.application.coupon;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.hhplus.be.ecommerce.domain.coupon.Coupon;
import kr.hhplus.be.ecommerce.domain.coupon.CouponRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

	@Autowired
	private final CouponRepository couponRepository;

	public BigDecimal applyCoupon(Integer couponId, BigDecimal totalPrice) {
		
		if (couponId == null) {
			return BigDecimal.ZERO;
		}

		Coupon coupon = couponRepository.findById(couponId)
										.orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_COUPON));

		BigDecimal discount = coupon.calculateDiscount(totalPrice);
		
		coupon.markAsUsed();
		
		couponRepository.save(coupon);

		return discount;
	}
}
