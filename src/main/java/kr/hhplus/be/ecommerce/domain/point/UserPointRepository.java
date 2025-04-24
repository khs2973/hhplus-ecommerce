package kr.hhplus.be.ecommerce.domain.point;

import java.util.Optional;

public interface UserPointRepository {

	UserPoint findByUserId(String userId);
	
	void save(UserPoint userPoint);
	
	Optional<UserPoint> findByUserIdForUpdate(String userId);
	
}
