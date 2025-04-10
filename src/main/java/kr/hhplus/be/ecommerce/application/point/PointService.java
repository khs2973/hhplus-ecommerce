package kr.hhplus.be.ecommerce.application.point;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.domain.point.PointHistory;
import kr.hhplus.be.ecommerce.domain.point.PointHistoryRepository;
import kr.hhplus.be.ecommerce.domain.point.UserPoint;
import kr.hhplus.be.ecommerce.domain.point.UserPointRepository;
import kr.hhplus.be.ecommerce.infrastructure.user.UserRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import kr.hhplus.be.ecommerce.interfaces.point.PointRequest;
import kr.hhplus.be.ecommerce.interfaces.point.PointResponse;

@Service
public class PointService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserPointRepository userPointRepository;

	@Autowired
	PointHistoryRepository pointHistoryRepository;

	@Transactional
	public PointResponse chargeUserPoint(PointRequest pointRequest) {

		String userId = pointRequest.getUserId();
		
		BigDecimal chargeAmount = pointRequest.getUserPoint();

		userRepository.findByUserId(userId)
					  .orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_USER));

		UserPoint userPoint = userPointRepository.findByUserId(userId);

		BigDecimal totalPoint = userPoint.validate(chargeAmount);

		pointHistoryRepository.save(PointHistory.builder()
												.userId(userId)
												.beforePoint(userPoint.getUserPoint())
												.afterPoint(totalPoint)
												.cdate(LocalDateTime.now())
												.build());

		userPointRepository.save(userPoint);

		return PointResponse.chargeUserPoint(userId, totalPoint, totalPoint);

	}
	
	@Transactional
	public BigDecimal usePoint(String userId, BigDecimal amountToUse) {

		UserPoint userPoint = userPointRepository.findByUserId(userId);

		if (userPoint == null) {
			throw new CustomException(ErrorEnum.NOT_FOUND_USER_POINT);
		}

		BigDecimal currentPoint = userPoint.getUserPoint();

		if (currentPoint.compareTo(amountToUse) < 0) {
			throw new CustomException(ErrorEnum.NOT_FOUND_USER_POINT);
		}

		BigDecimal totalPoint = currentPoint.subtract(amountToUse);
		userPoint.setUserPoint(totalPoint);

		userPointRepository.save(userPoint);

		return totalPoint;
	}

}
