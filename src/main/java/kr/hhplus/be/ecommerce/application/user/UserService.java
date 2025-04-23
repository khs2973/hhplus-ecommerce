package kr.hhplus.be.ecommerce.application.user;

import org.springframework.stereotype.Service;

import kr.hhplus.be.ecommerce.domain.user.User;
import kr.hhplus.be.ecommerce.domain.user.UserRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	public User getUser(String userId) {
		return userRepository.findByUserId(userId)
				 			 .orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_USER));
	}
	
}
