package kr.hhplus.be.ecommerce.interfaces.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import kr.hhplus.be.ecommerce.application.coupon.CouponService;
import kr.hhplus.be.ecommerce.domain.coupon.CouponCommand.CommandCoupon;
import kr.hhplus.be.ecommerce.interfaces.common.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

	@Autowired
	private final CouponService couponService;
	
	@PostMapping("/createCoupon")
	public ApiResponse<?> createCoupon(@RequestBody CoupontRequest couponRequest) {
		CommandCoupon couponCommand = CommandCoupon.of(couponRequest.getUserId(), couponRequest.getCouponId());
		couponService.createCouponLock(couponCommand);
		return ApiResponse.success();
	}
}
