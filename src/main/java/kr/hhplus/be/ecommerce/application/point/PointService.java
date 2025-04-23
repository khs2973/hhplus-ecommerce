package kr.hhplus.be.ecommerce.application.point;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.domain.point.PointCommand;
import kr.hhplus.be.ecommerce.domain.point.PointCommand.Use;
import kr.hhplus.be.ecommerce.domain.point.PointHistory;
import kr.hhplus.be.ecommerce.domain.point.PointHistoryRepository;
import kr.hhplus.be.ecommerce.domain.point.PointInfo;
import kr.hhplus.be.ecommerce.domain.point.PointInfo.Point;
import kr.hhplus.be.ecommerce.domain.point.UserPoint;
import kr.hhplus.be.ecommerce.domain.point.UserPointRepository;
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
	
	public boolean usePoint(PointCommand.Use pointCommand) {

		String userId = pointCommand.getUserId();
		BigDecimal amount = pointCommand.getUserPoint();
		
		UserPoint userPoint = userPointRepository.findByUserId(userId);

		PointHistory history = userPoint.use(amount);

		userPointRepository.save(userPoint);
		
		pointHistoryRepository.save(history);

		return true;
	}
	
	public UserPoint searchUserPoint(String userId) {
		return userPointRepository.findByUserId(userId);
	}

}
