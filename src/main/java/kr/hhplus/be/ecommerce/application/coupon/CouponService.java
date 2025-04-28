package kr.hhplus.be.ecommerce.application.coupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.domain.coupon.Coupon;
import kr.hhplus.be.ecommerce.domain.coupon.CouponCommand.CommandCoupon;
import kr.hhplus.be.ecommerce.domain.coupon.CouponRepository;
import kr.hhplus.be.ecommerce.domain.coupon.UserCoupon;
import kr.hhplus.be.ecommerce.domain.coupon.UserCouponRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

	@Autowired
	private final CouponRepository couponRepository;
	
	@Autowired
	private final UserCouponRepository userCouponRepository;
	
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
	
	public Coupon getCoupon(Integer couponId) {
		
		Coupon coupon = couponRepository.findById(couponId)
										.orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_COUPON));
		
		return coupon;
	}
	
	@Transactional
	public Boolean createCouponLock(CommandCoupon command) {
		
		Coupon coupon = couponRepository.findByIdForUpdate(command.getCouponId())
										.orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_COUPON));
		coupon.stockCheck();
		
		UserCoupon userCoupon = UserCoupon.builder()
										  .userId(command.getUserId())
										  .couponId(command.getCouponId())
										  .cdate(LocalDateTime.now())
										  .usedState(0)
										  .build();
		
		userCouponRepository.save(userCoupon);
		
		return true;
	}
	
	
}
