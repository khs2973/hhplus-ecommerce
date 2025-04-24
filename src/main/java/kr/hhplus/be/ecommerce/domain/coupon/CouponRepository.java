package kr.hhplus.be.ecommerce.domain.coupon;

import java.util.Optional;

public interface CouponRepository {
	
	Optional<Coupon> findById(Integer couponId);
	void save(Coupon coupon);
	Optional<Coupon> findByIdForUpdate(Integer couponId);
}
