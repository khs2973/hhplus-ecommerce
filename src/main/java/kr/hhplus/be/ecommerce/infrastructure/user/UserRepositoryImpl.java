package kr.hhplus.be.ecommerce.infrastructure.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.hhplus.be.ecommerce.domain.user.User;
import kr.hhplus.be.ecommerce.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{
	
	private final UserJpaRepository userJpaRepository;
	
	@Override
	public Optional<User> findByUserId(String userId) {
		return userJpaRepository.findById(userId);
	}
}
