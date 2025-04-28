package kr.hhplus.be.ecommerce.application.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.hhplus.be.ecommerce.domain.coupon.Coupon;
import kr.hhplus.be.ecommerce.domain.coupon.CouponCommand.CommandCoupon;
import kr.hhplus.be.ecommerce.domain.coupon.CouponRepository;
import kr.hhplus.be.ecommerce.domain.coupon.UserCouponRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest {

	@Mock
	private CouponRepository couponRepository;

	@Mock
	private UserCouponRepository userCouponRepository;

	@InjectMocks
	private CouponService couponService;

	@Test
	@DisplayName("쿠폰 발급 성공")
	void createCouponSuccess() {

		CommandCoupon command = new CommandCoupon("hanghae", 1);
		Coupon coupon = Mockito.mock(Coupon.class);

		when(couponRepository.findByIdForUpdate(1)).thenReturn(Optional.of(coupon));

		Boolean result = couponService.createCouponLock(command);

		assertThat(result).isTrue();

	}

	@Test
	@DisplayName("쿠폰이 존재하지 않을 경우 예외 발생")
	void createCouponFail() {

		CommandCoupon command = new CommandCoupon("hanghae", 999);
		when(couponRepository.findByIdForUpdate(999)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> couponService.createCouponLock(command)).isInstanceOf(CustomException.class)
				.hasMessageContaining(ErrorEnum.NOT_FOUND_COUPON.getMessage());
	}

	@Test
	@DisplayName("쿠폰 재고가 부족할 경우 예외 발생")
	void createCouponStockFail() {
		
		CommandCoupon command = new CommandCoupon("user123", 1);
		Coupon coupon = mock(Coupon.class);

		when(couponRepository.findByIdForUpdate(1)).thenReturn(Optional.of(coupon));
		doThrow(new CustomException(ErrorEnum.COUPON_STOCK_EMPTY)).when(coupon).stockCheck();

		assertThatThrownBy(() -> couponService.createCouponLock(command)).isInstanceOf(CustomException.class)
																	 .hasMessageContaining(ErrorEnum.COUPON_STOCK_EMPTY.getMessage());
	}

}
