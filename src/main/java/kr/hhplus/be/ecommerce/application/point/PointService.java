package kr.hhplus.be.ecommerce.application.point;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.domain.point.PointEnum;
import kr.hhplus.be.ecommerce.domain.point.PointHistory;
import kr.hhplus.be.ecommerce.domain.point.PointHistoryRepository;
import kr.hhplus.be.ecommerce.domain.point.UserPoint;
import kr.hhplus.be.ecommerce.domain.point.UserPointRepository;
import kr.hhplus.be.ecommerce.infrastructure.user.UserRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import kr.hhplus.be.ecommerce.interfaces.point.PointRequest;
import kr.hhplus.be.ecommerce.interfaces.point.PointResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointService {

	private final UserRepository userRepository;

	private final UserPointRepository userPointRepository;

	private final PointHistoryRepository pointHistoryRepository;

	@Transactional
	public PointResponse chargeUserPoint(PointRequest pointRequest) {

		String userId = pointRequest.getUserId();
		
		BigDecimal chargeAmount = pointRequest.getUserPoint();

		userRepository.findByUserId(userId)
					  .orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_USER));

		UserPoint userPoint = userPointRepository.findByUserId(userId);

		BigDecimal totalPoint = userPoint.validateChargePoint(chargeAmount);

		PointHistory pointHistory = PointHistory.builder()
												.userId(userId)
												.beforePoint(userPoint.getUserPoint())
												.afterPoint(totalPoint)
												.pointType(PointEnum.USE.name())
												.cdate(LocalDateTime.now())
												.build();
		
		userPointRepository.save(userPoint);

		pointHistoryRepository.save(pointHistory);

		return PointResponse.chargeUserPoint(userId, totalPoint, totalPoint);

	}
	
	@Transactional
	public BigDecimal usePoint(String userId, BigDecimal amountToUse) {

		UserPoint userPoint = userPointRepository.findByUserId(userId);

		userPoint.validateUserPoint();

		userPoint.validateChargePoint(amountToUse);

		BigDecimal currentPoint = userPoint.getUserPoint();
		
		BigDecimal totalPoint = currentPoint.subtract(amountToUse);

		userPoint.comparePoint(totalPoint);
		
		userPoint.setUserPoint(totalPoint);
		
		PointHistory pointHistory = PointHistory.builder()
												.userId(userId)
												.beforePoint(userPoint.getUserPoint())
												.afterPoint(totalPoint)
												.pointType(PointEnum.USE.name())
												.cdate(LocalDateTime.now())
												.build();

		userPointRepository.save(userPoint);
		
		pointHistoryRepository.save(pointHistory);

		return totalPoint;
	}

}
