package kr.hhplus.be.ecommerce.application.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.domain.point.PointCommand;
import kr.hhplus.be.ecommerce.domain.point.PointInfo;
import kr.hhplus.be.ecommerce.domain.point.UserPoint;
import kr.hhplus.be.ecommerce.domain.point.UserPointRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;


@SpringBootTest
@Transactional
public class PointServiceIntegrationTest {

	@Autowired
	private PointService pointService;

	@Autowired
	private UserPointRepository userPointRepository;

	private final String userId = "hanghae";

	@BeforeEach
	void setUp() {

		UserPoint userPoint = new UserPoint(userId, new BigDecimal("5000"));
		userPointRepository.save(userPoint);

	}

	@Test
	@DisplayName("포인트 충전 성공")
	void successCharge() {
		BigDecimal chargePoint = new BigDecimal("5000");
		PointCommand.Charge chargeCommand = PointCommand.Charge.of(userId, chargePoint);

		PointInfo.Point result = pointService.chargeUserPoint(chargeCommand);

		assertThat(result.getUserPoint()).isEqualByComparingTo("10000");
	}
	
	@Test
	@DisplayName("최대 금액으로 충전시 실패")
	void failChargeMaxPoint() {
		BigDecimal chargePoint = new BigDecimal("99999");
		PointCommand.Charge chargeCommand = PointCommand.Charge.of(userId, chargePoint);
		
		assertThatThrownBy(() -> pointService.chargeUserPoint(chargeCommand)).isInstanceOf(CustomException.class)
																			 .hasMessage(ErrorEnum.CHARGE_POINT_MAX.getMessage());
	}

	@Test
	@DisplayName("최소 금액으로 충전시 실패")
	void failChargeMinPoint() {

		BigDecimal chargePoint = new BigDecimal("2000");
		PointCommand.Charge chargeCommand = PointCommand.Charge.of(userId, chargePoint);

		assertThatThrownBy(() -> pointService.chargeUserPoint(chargeCommand)).isInstanceOf(CustomException.class)
																			 .hasMessage(ErrorEnum.CHARGE_POINT_MIN.getMessage());

	}

	@Test
	@DisplayName("포인트 사용 성공")
	void successUsePoint() {

		BigDecimal usePoint = new BigDecimal("3000");
		PointCommand.Use useCommand = PointCommand.Use.of(userId, usePoint);

		boolean result = pointService.usePoint(useCommand);

		assertThat(result).isTrue();

		UserPoint updated = userPointRepository.findByUserId(userId);
		assertThat(updated.getUserPoint()).isEqualByComparingTo("2000");
		
	}
	
	@Test
	@DisplayName("포인트 부족으로 사용 실패")
	void failUsePoint() {
		
		BigDecimal usePoint = new BigDecimal("99999");
		PointCommand.Use useCommand = PointCommand.Use.of(userId, usePoint);
		
		assertThatThrownBy(() -> pointService.usePoint(useCommand)).isInstanceOf(CustomException.class)
																   .hasMessage(ErrorEnum.NOT_ENOUGH_POINT.getMessage());
		
	}
}
