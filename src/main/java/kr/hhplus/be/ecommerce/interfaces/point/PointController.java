package kr.hhplus.be.ecommerce.interfaces.point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.hhplus.be.ecommerce.application.point.PointService;
import kr.hhplus.be.ecommerce.domain.point.PointCommand.Charge;
import kr.hhplus.be.ecommerce.domain.point.PointCommand.Use;
import kr.hhplus.be.ecommerce.domain.point.PointInfo;
import kr.hhplus.be.ecommerce.interfaces.common.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

	@Autowired
	PointService pointService;

	@PostMapping("/charge")
	public ApiResponse<?> chargeUserPoint(@RequestBody PointRequest pointRequest) {
		
		Charge pointCharge = pointRequest.toChargeCommand();
		
		PointInfo.Point pointInfo = pointService.chargeUserPoint(pointCharge);
		
		return ApiResponse.success(PointResponse.from(pointInfo));
	}
	
	@PostMapping("/use")
	public ApiResponse<?> usePoint(@RequestBody PointRequest pointRequest) {
		
		Use pointUse = pointRequest.toUseCommand();
		
		pointService.usePoint(pointUse);
		
		return ApiResponse.success();
		
	}

}
