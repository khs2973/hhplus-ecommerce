package kr.hhplus.be.ecommerce.infrastructure.point;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.ecommerce.domain.point.PointHistory;
import kr.hhplus.be.ecommerce.domain.point.PointHistoryRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepository{

	private final PointHistoryJpaRepository pointHistoryJpaRepository;
	
	@Override
	public void save(PointHistory pointHistory) {
		pointHistoryJpaRepository.save(pointHistory);
	}
	
}
