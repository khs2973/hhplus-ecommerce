package kr.hhplus.be.ecommerce.domain.point;

import org.springframework.stereotype.Repository;

@Repository
public interface UserPointRepository {

	UserPoint findByUserId(String userId);
	
	void save(UserPoint userPoint);
	
}
