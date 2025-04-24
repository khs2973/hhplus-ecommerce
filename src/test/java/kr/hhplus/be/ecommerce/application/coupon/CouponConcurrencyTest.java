package kr.hhplus.be.ecommerce.application.coupon;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.hhplus.be.ecommerce.domain.coupon.Coupon;
import kr.hhplus.be.ecommerce.domain.coupon.CouponCommand.CommandCoupon;
import kr.hhplus.be.ecommerce.domain.coupon.CouponRepository;
import kr.hhplus.be.ecommerce.domain.user.User;
import kr.hhplus.be.ecommerce.domain.user.UserRepository;

@SpringBootTest
public class CouponConcurrencyTest {

	@Autowired
	private CouponService couponService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
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
							  .couponStock(1)
							  .couponState(1)
							  .startDate(LocalDateTime.now().minusDays(1))
							  .endDate(LocalDateTime.now().plusDays(3))
							  .build();

		couponRepository.save(coupon);
		couponId = coupon.getCouponId();
	}

	@Test
	@DisplayName("동시성 테스트 - 선착순 1명만 쿠폰 발급 성공")
	void concurrencyCoupon() throws InterruptedException {
		
		int threadCount = 5;
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		List<String> userIds = new ArrayList<>();
		for (int i = 0; i < threadCount; i++) {
			String uid = "user" + i;
			userRepository.save(new User(uid, "서울", LocalDateTime.now()));
			userIds.add(uid);
		}

		List<Throwable> errors = Collections.synchronizedList(new ArrayList<>());
		CommandCoupon commandCoupon = CommandCoupon.of(userId, couponId);
		for (String uid : userIds) {
			executorService.submit(() -> {
				try {
					couponService.createCoupon(commandCoupon);
				} catch (Throwable t) {
					errors.add(t);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Coupon updated = couponRepository.findById(couponId).orElseThrow();
		assertThat(updated.getCouponStock()).isEqualTo(0);
	}

}
