package kr.hhplus.be.ecommerce.application.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.hhplus.be.ecommerce.domain.point.PointCommand;
import kr.hhplus.be.ecommerce.domain.point.PointHistory;
import kr.hhplus.be.ecommerce.domain.point.PointHistoryRepository;
import kr.hhplus.be.ecommerce.domain.point.PointInfo;
import kr.hhplus.be.ecommerce.domain.point.UserPoint;
import kr.hhplus.be.ecommerce.domain.point.UserPointRepository;
import kr.hhplus.be.ecommerce.domain.user.User;
import kr.hhplus.be.ecommerce.infrastructure.user.UserRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import kr.hhplus.be.ecommerce.interfaces.point.PointRequest;
import kr.hhplus.be.ecommerce.interfaces.point.PointResponse;

@ExtendWith(MockitoExtension.class)
public class PointServiceTest {

	@InjectMocks
	private PointService pointService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserPointRepository userPointRepository;

	@Mock
	private PointHistoryRepository pointHistoryRepository;

	@Test
	@DisplayName("포인트 충전 성공")
	void successCharge() {

		// given
		String userId = "hanghae";

		BigDecimal currentPoint = new BigDecimal("5000");
		BigDecimal chargePoint = new BigDecimal("7000");
		BigDecimal totalPoint = currentPoint.add(chargePoint);

		UserPoint userPoint = new UserPoint(userId, currentPoint);
		PointRequest pointRequest = new PointRequest(userId, chargePoint);
		PointCommand.Charge pointCommand = pointRequest.toChargeCommand();

		when(userPointRepository.findByUserId(userId)).thenReturn(userPoint);

		// when
		PointInfo.Point resultPoint = pointService.chargeUserPoint(pointCommand);

		// then
		assertThat(resultPoint.getUserPoint()).isEqualByComparingTo(totalPoint);

		verify(userPointRepository).save(userPoint);
		verify(pointHistoryRepository).save(any(PointHistory.class));

	}

	@Test
	@DisplayName("최소 금액으로 충전시 실패")
	void failMinChargePoint() {

		String userId = "hanghae";
		BigDecimal chargePoint = new BigDecimal("2000");

		PointRequest pointRequest = new PointRequest(userId, chargePoint);
		PointCommand.Charge chargeCommand = pointRequest.toChargeCommand();

		UserPoint userPoint = mock(UserPoint.class);
		
		when(userPointRepository.findByUserId(userId)).thenReturn(userPoint);
		doThrow(new CustomException(ErrorEnum.CHARGE_POINT_MIN)).when(userPoint)
																.validateChargePoint(chargePoint);

		assertThatThrownBy(() -> pointService.chargeUserPoint(chargeCommand))
											 .isInstanceOf(CustomException.class)
											 .hasMessageContaining(ErrorEnum.CHARGE_POINT_MIN.getMessage());
	}

	@Test
	@DisplayName("포인트 사용 성공")
	void successUserPoint() {

		String userId = "hanghae";
		BigDecimal currentPoint = new BigDecimal("5000");

		UserPoint userPoint = new UserPoint(userId, currentPoint);
		PointRequest pointRequest = new PointRequest(userId, currentPoint);
		PointCommand.Use useCommand = pointRequest.toUseCommand();

		when(userPointRepository.findByUserId(userId)).thenReturn(userPoint);

		boolean result = pointService.usePoint(useCommand);

		assertThat(result).isTrue();

		verify(userPointRepository).save(userPoint);
		verify(pointHistoryRepository).save(any(PointHistory.class));

	}

	@Test
	@DisplayName("포인트 사용 실패 - 포인트 부족")
	void failUserPoint() {

		String userId = "hanghae";
		BigDecimal currentPoint = new BigDecimal("500");
		BigDecimal amountToUse = new BigDecimal("1000");

		UserPoint userPoint = new UserPoint(userId, currentPoint);
		PointCommand.Use useCommand = PointCommand.Use.of(userId, amountToUse);

		when(userPointRepository.findByUserId(userId)).thenReturn(userPoint);

		assertThatThrownBy(() -> pointService.usePoint(useCommand))
											 .isInstanceOf(CustomException.class)
											 .hasMessageContaining(ErrorEnum.NOT_ENOUGH_POINT.getMessage());
	}

}
