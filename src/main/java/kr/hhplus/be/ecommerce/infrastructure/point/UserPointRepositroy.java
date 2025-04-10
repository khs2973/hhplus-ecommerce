package kr.hhplus.be.ecommerce.infrastructure.point;

import java.util.Optional;

import kr.hhplus.be.ecommerce.domain.point.UserPoint;


public interface UserPointRepositroy {

	Optional<UserPoint> findByUserId(String userId);
	
}
