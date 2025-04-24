package kr.hhplus.be.ecommerce.infrastructure.coupon;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.ecommerce.domain.coupon.Coupon;

public interface CouponJpaRepository extends JpaRepository<Coupon, Integer>{
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT c FROM Coupon c WHERE c.couponId = :couponId")
	Optional<Coupon> findByIdForUpdate(@Param("couponId") Integer couponId);
}
