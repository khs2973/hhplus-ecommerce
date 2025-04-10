package kr.hhplus.be.ecommerce.domain.point;

import org.springframework.stereotype.Repository;

@Repository
public interface PointHistoryRepository {
	
	void save(PointHistory pointHistory);
	
}
