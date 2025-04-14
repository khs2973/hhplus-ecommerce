package kr.hhplus.be.ecommerce.application.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
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

import kr.hhplus.be.ecommerce.domain.point.PointHistory;
import kr.hhplus.be.ecommerce.domain.point.PointHistoryRepository;
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

		String userId = "hanghae";

		BigDecimal currentPoint = new BigDecimal(5000);
		BigDecimal chargePoint = new BigDecimal(7000);

		BigDecimal totalPoint = currentPoint.add(chargePoint);

		PointRequest pointRequest = new PointRequest(userId, chargePoint);

		when(userRepository.findByUserId(userId)).thenReturn(Optional.of(new User()));
		when(userPointRepository.findByUserId(userId)).thenReturn(new UserPoint(userId, currentPoint));

		PointResponse response = pointService.chargeUserPoint(pointRequest);

		assertThat(response.getTotalPoint()).isEqualByComparingTo(totalPoint);
		assertThat(response.getUserPoint()).isEqualByComparingTo(totalPoint);
		assertThat(response.getUserId()).isEqualTo(userId);

		verify(userPointRepository).save(any(UserPoint.class));
		verify(pointHistoryRepository).save(any(PointHistory.class));

	}

	@Test
	@DisplayName("유효하지 않은 사용자")
	void userNotFound() {

		String userId = "notUser";
		PointRequest pointRequest = new PointRequest(userId, new BigDecimal(5000));

		when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> pointService.chargeUserPoint(pointRequest))
											 .isInstanceOf(IllegalArgumentException.class).hasMessage("유효하지 않은 사용자입니다.");
	}

	@Test
	@DisplayName("최소 금액으로 충전시 실패")
	void failMinChargePoint() {

		String userId = "hanghae";
		BigDecimal chargePoint = new BigDecimal(2000);

		PointRequest pointRequest = new PointRequest(userId, chargePoint);

		UserPoint userPoint = mock(UserPoint.class);

		when(userRepository.findByUserId(userId)).thenReturn(Optional.of(mock(User.class)));
		when(userPointRepository.findByUserId(userId)).thenReturn(userPoint);
		when(userPoint.validateChargePoint(chargePoint)).thenThrow(new CustomException(ErrorEnum.CHARGE_POINT_MIN));

		assertThatThrownBy(() -> pointService.chargeUserPoint(pointRequest))
											 .isInstanceOf(CustomException.class)
											 .hasMessageContaining(ErrorEnum.CHARGE_POINT_MIN.getMessage());
	}
	
	@Test
	@DisplayName("포인트 사용 성공")
	void successUserPoint() {
		
		String userId = "hanghae";
		BigDecimal currentPoint = new BigDecimal(5000);
		BigDecimal amountToUse = new BigDecimal(2000);

		UserPoint userPoint = new UserPoint();
		userPoint.setUserPoint(currentPoint);

		when(userPointRepository.findByUserId(userId)).thenReturn(userPoint);

		BigDecimal remainingPoint = pointService.usePoint(userId, amountToUse);

		assertThat(remainingPoint).isEqualByComparingTo(new BigDecimal(3000));
		
	}
	
	@Test
	@DisplayName("포인트 사용 실패 - 포인트 부족")
	void failUserPoint() {
		
		String userId = "hanghae";
		BigDecimal currentPoint = new BigDecimal(500);
		BigDecimal amountToUse = new BigDecimal(1000);

		UserPoint userPoint = new UserPoint();
		userPoint.setUserPoint(currentPoint);

		when(userPointRepository.findByUserId(userId)).thenReturn(userPoint);

		assertThatThrownBy(() -> pointService.usePoint(userId, amountToUse))
											 .isInstanceOf(CustomException.class)
											 .hasMessageContaining(ErrorEnum.NOT_FOUND_USER_POINT.getMessage());
	}
	
	

}
