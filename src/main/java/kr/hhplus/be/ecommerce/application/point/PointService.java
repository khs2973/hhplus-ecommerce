package kr.hhplus.be.ecommerce.application.point;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.domain.point.PointCommand;
import kr.hhplus.be.ecommerce.domain.point.PointEnum;
import kr.hhplus.be.ecommerce.domain.point.PointHistory;
import kr.hhplus.be.ecommerce.domain.point.PointHistoryRepository;
import kr.hhplus.be.ecommerce.domain.point.PointInfo;
import kr.hhplus.be.ecommerce.domain.point.PointInfo.Point;
import kr.hhplus.be.ecommerce.domain.point.UserPoint;
import kr.hhplus.be.ecommerce.domain.point.UserPointRepository;
import kr.hhplus.be.ecommerce.infrastructure.user.UserRepository;
import kr.hhplus.be.ecommerce.interfaces.common.ApiResponse;
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
