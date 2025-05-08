package kr.hhplus.be.ecommerce.application.point;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.domain.point.PointCommand;
import kr.hhplus.be.ecommerce.domain.point.PointHistory;
import kr.hhplus.be.ecommerce.domain.point.PointHistoryRepository;
import kr.hhplus.be.ecommerce.domain.point.PointInfo;
import kr.hhplus.be.ecommerce.domain.point.PointInfo.Point;
import kr.hhplus.be.ecommerce.domain.point.UserPoint;
import kr.hhplus.be.ecommerce.domain.point.UserPointRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointService {

	private final UserPointRepository userPointRepository;

	private final PointHistoryRepository pointHistoryRepository;

	public Point chargeUserPoint(PointCommand.Charge pointCommand) {

		String userId = pointCommand.getUserId();
		BigDecimal amount = pointCommand.getUserPoint();
		
		UserPoint userPoint = userPointRepository.findByUserId(userId);

		userPoint.validateChargePoint(amount);
		
		PointHistory pointHistory = userPoint.charge(amount);

		userPointRepository.save(userPoint);
		pointHistoryRepository.save(pointHistory);

		return PointInfo.Point.of(userPoint.getUserPoint());

	}
	
	@Transactional
	public boolean usePoint(PointCommand.Use pointCommand) {

		String userId = pointCommand.getUserId();
		BigDecimal amount = pointCommand.getUserPoint();
		
		UserPoint userPoint = userPointRepository.findByUserIdForUpdate(userId)
												 .orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_USER));

		PointHistory history = userPoint.use(amount);

		userPointRepository.save(userPoint);
		
		pointHistoryRepository.save(history);

		return true;
	}
	
	public UserPoint searchUserPoint(String userId) {
		return userPointRepository.findByUserId(userId);
	}

}
