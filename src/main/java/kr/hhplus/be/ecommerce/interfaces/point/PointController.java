package kr.hhplus.be.ecommerce.interfaces.point;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.hhplus.be.ecommerce.application.point.PointService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

	private final PointService pointService;

	@PostMapping("/charge")
	public PointResponse chargeUserPoint(@RequestBody PointRequest pointRequest) {
		return pointService.chargeUserPoint(pointRequest);
	}

}
