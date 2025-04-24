package kr.hhplus.be.ecommerce.application.point;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
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

import kr.hhplus.be.ecommerce.domain.point.PointCommand;
import kr.hhplus.be.ecommerce.domain.point.UserPoint;
import kr.hhplus.be.ecommerce.domain.point.UserPointRepository;
import kr.hhplus.be.ecommerce.domain.user.User;
import kr.hhplus.be.ecommerce.domain.user.UserRepository;

@SpringBootTest
public class PointConcurrencyTest {

	@Autowired
	private UserPointRepository userPointRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PointService pointService;

	private String userId;
	
	@BeforeEach
	void setUp() {
		
		userId = "hanghae";
		
		userRepository.save(new User(userId, "서울", LocalDateTime.now()));
		userPointRepository.save(new UserPoint(userId, new BigDecimal("10000")));
	}

	@Test
	@DisplayName("여러번의 요청이 와도 하나만 처리가 되어야한다.")
	void concurrentPointUse() throws InterruptedException {
		int threadCount = 2;
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		List<Throwable> errors = Collections.synchronizedList(new ArrayList<>());

		Runnable task = () -> {
			try {
				pointService.usePoint(PointCommand.Use.of(userId, new BigDecimal("10000")));
			} catch (Throwable t) {
				errors.add(t);
			} finally {
				latch.countDown();
			}
		};

		executorService.submit(task);
		executorService.submit(task);

		latch.await();

		assertThat(errors).hasSize(1);
		assertThat(errors.get(0).getMessage()).contains("포인트가 부족합니다");
	}
}
