package kr.hhplus.be.ecommerce.application.coupon;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.domain.coupon.Coupon;
import kr.hhplus.be.ecommerce.domain.coupon.CouponCommand.CommandCoupon;
import kr.hhplus.be.ecommerce.domain.coupon.CouponRepository;
import kr.hhplus.be.ecommerce.domain.user.User;
import kr.hhplus.be.ecommerce.domain.user.UserRepository;

@SpringBootTest
@Transactional
public class CouponIntegrationTest {

	@Autowired
	private CouponService couponService;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private UserRepository userRepository;

	private String userId;
	private Integer couponId;

	@BeforeEach
	void setUp() {
		User user = new User("hanghae", "서울", LocalDateTime.now());
		
		userRepository.save(user);
		userId = user.getUserId();

		Coupon coupon = Coupon.builder()
							  .couponName("10% 할인 쿠폰")
							  .discount(10)
							  .couponType("PERCENT")
							  .couponStock(10)
							  .couponState(1)
							  .startDate(LocalDateTime.now().minusDays(1))
							  .endDate(LocalDateTime.now().plusDays(3))
							  .build();

		couponRepository.save(coupon);
		couponId = coupon.getCouponId();
	}
	
	@Test
	@DisplayName("쿠폰 발급 성공")
	void createCouponSuccess() {

		CommandCoupon commandCoupon = new CommandCoupon(userId, couponId);
		
		boolean result = couponService.createCouponLock(commandCoupon);

		assertThat(result).isTrue();
	}
	

}
