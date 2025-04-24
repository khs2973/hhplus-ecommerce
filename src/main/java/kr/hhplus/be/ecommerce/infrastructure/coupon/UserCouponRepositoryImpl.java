package kr.hhplus.be.ecommerce.infrastructure.coupon;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.ecommerce.domain.coupon.UserCoupon;
import kr.hhplus.be.ecommerce.domain.coupon.UserCouponRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserCouponRepositoryImpl implements UserCouponRepository{
	
	private final UserCouponJpaRepository userCouponJpaRepository;
	
	@Override
	public void save(UserCoupon userCoupon) {
		userCouponJpaRepository.save(userCoupon);
	}
	
}
