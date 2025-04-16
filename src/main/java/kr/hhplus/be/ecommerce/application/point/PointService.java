package kr.hhplus.be.ecommerce.application.point;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.domain.point.PointCommand;
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

	@Transactional
	public Point chargeUserPoint(PointCommand.Charge pointCommand) {

		UserPoint userPoint = userPointRepository.findByUserId(pointCommand.getUserId());

		userPoint.validateChargePoint(pointCommand.getUserPoint());
		
		PointHistory pointHistory = userPoint.charge(pointCommand.getUserPoint());

		userPointRepository.save(userPoint);
		pointHistoryRepository.save(pointHistory);

		return PointInfo.Point.of(userPoint.getUserPoint());

	}
	
	@Transactional
	public boolean usePoint(PointCommand.Use pointCommand) {

		UserPoint userPoint = userPointRepository.findByUserId(pointCommand.getUserId());

		PointHistory history = userPoint.use(pointCommand.getUserPoint());

		userPointRepository.save(userPoint);
		
		pointHistoryRepository.save(history);

		return true;
	}

}
