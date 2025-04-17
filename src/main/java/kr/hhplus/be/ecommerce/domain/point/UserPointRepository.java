package kr.hhplus.be.ecommerce.domain.point;

public interface UserPointRepository {

	UserPoint findByUserId(String userId);
	
	void save(UserPoint userPoint);
	
}
