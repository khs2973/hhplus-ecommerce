package kr.hhplus.be.ecommerce.infrastructure.coupon;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hhplus.be.ecommerce.domain.coupon.Coupon;

public interface CouponJpaRepository extends JpaRepository<Coupon, Integer>{

}
