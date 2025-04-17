package kr.hhplus.be.ecommerce.infrastructure.coupon;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.ecommerce.domain.coupon.Coupon;
import kr.hhplus.be.ecommerce.domain.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository{

	private final CouponJpaRepository couponJpaRepository;
	
	@Override
	public Optional<Coupon> findById(Integer couponId) {
		return couponJpaRepository.findById(couponId);
	}
	
	@Override
	public void save(Coupon coupon) {
		couponJpaRepository.save(coupon);
	}
	
}
