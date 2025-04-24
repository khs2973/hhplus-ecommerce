package kr.hhplus.be.ecommerce.infrastructure.coupon;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hhplus.be.ecommerce.domain.coupon.UserCoupon;

public interface UserCouponJpaRepository extends JpaRepository<UserCoupon, String>{

}
