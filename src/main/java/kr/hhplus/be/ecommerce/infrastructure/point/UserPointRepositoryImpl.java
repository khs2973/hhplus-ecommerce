package kr.hhplus.be.ecommerce.infrastructure.point;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.ecommerce.domain.point.UserPoint;
import kr.hhplus.be.ecommerce.domain.point.UserPointRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserPointRepositoryImpl implements UserPointRepository{
	
	private final UserPointJpaRepository userPointJpaRepository;
	
	@Override
	public UserPoint findByUserId(String userId){
		return userPointJpaRepository.findByUserId(userId);
	}
	
	@Override
	public void save(UserPoint userPoint){
		userPointJpaRepository.save(userPoint);
	}
	
}
