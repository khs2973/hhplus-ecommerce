package kr.hhplus.be.ecommerce.application.order;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.hhplus.be.ecommerce.application.order.OrderCriteria.CriteriaOrderProduct;
import kr.hhplus.be.ecommerce.domain.coupon.Coupon;
import kr.hhplus.be.ecommerce.domain.coupon.CouponEnum;
import kr.hhplus.be.ecommerce.domain.coupon.CouponRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderCommand.CommandOrder;
import kr.hhplus.be.ecommerce.domain.point.UserPoint;
import kr.hhplus.be.ecommerce.domain.point.UserPointRepository;
import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;
import kr.hhplus.be.ecommerce.domain.user.User;
import kr.hhplus.be.ecommerce.domain.user.UserRepository;

@SpringBootTest
public class OrderConcurrencyTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private UserPointRepository userPointRepository;
	
	@Autowired
	private OrderFacade orderFacade;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Test
	@DisplayName("비관적 락 - 동시에 주문 발생시 재고 부족")
	void concurrentOrderCoupon() throws Exception {
		
		String userId1 = "hanghae_basic";
		String userId2 = "hanghae_advanced";

		User user1 = new User(userId1, "서울", LocalDateTime.now());
		User user2 = new User(userId2, "부산", LocalDateTime.now());
		
		userRepository.save(user1);
		userRepository.save(user2);

		Product product = Product.builder()
								 .productName("락 페스티벌 상품")
								 .productPrice(new BigDecimal("10000"))
								 .stock(1)
								 .cdate(LocalDateTime.now())
								 .productState(1)
								 .build();
		
		productRepository.save(product);
		
		Integer productId = product.getProductId();

		Coupon coupon = Coupon.builder()
							  .couponName("10% 할인")
							  .couponStock(10)
							  .couponType(CouponEnum.PERCENT.getDesc())
							  .discount(10)
							  .couponState(1)
							  .startDate(LocalDateTime.now().minusDays(1))
							  .endDate(LocalDateTime.now().plusDays(3))
							  .build();
		
		couponRepository.save(coupon);
		
		Integer couponId = coupon.getCouponId();

		userPointRepository.save(new UserPoint(userId1, new BigDecimal("10000")));
		userPointRepository.save(new UserPoint(userId2, new BigDecimal("10000")));

		CountDownLatch latch = new CountDownLatch(2);
		List<Throwable> errors = Collections.synchronizedList(new ArrayList<>());

		Runnable task1 = () -> {
			try {
				CommandOrder command = CommandOrder.of("hanghae_basic", couponId,
						List.of(new CriteriaOrderProduct(productId, 1)));
				orderFacade.createOrder(command);
			} catch (Throwable t) {
				errors.add(t);
			} finally {
				latch.countDown();
			}
		};

		Runnable task2 = () -> {
			try {
				CommandOrder command = CommandOrder.of("hanghae_advanced", couponId,
						List.of(new CriteriaOrderProduct(productId, 1)));
				orderFacade.createOrder(command);
			} catch (Throwable t) {
				errors.add(t);
			} finally {
				latch.countDown();
			}
		};

		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(task1);
		executor.submit(task2);

		latch.await();

		// then
		assertThat(errors).hasSize(1);
		assertThat(errors.get(0).getMessage()).contains("재고 부족");
	}
}
