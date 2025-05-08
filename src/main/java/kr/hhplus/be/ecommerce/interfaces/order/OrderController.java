package kr.hhplus.be.ecommerce.interfaces.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.hhplus.be.ecommerce.application.order.OrderFacade;
import kr.hhplus.be.ecommerce.domain.order.OrderCommand;
import kr.hhplus.be.ecommerce.domain.order.OrderInfo.InfoOrder;
import kr.hhplus.be.ecommerce.interfaces.common.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private final OrderFacade orderFacade;
	
	@PostMapping("/order")
	public ApiResponse<?> createOrder(@RequestBody OrderRequest orderRequest) {
		OrderCommand.Create orderCommand = OrderCommand.Create.of(orderRequest.getUserId()
																, orderRequest.getCouponId()
																, orderRequest.getCriteriaOrderProduct());
		InfoOrder infoOrder = orderFacade.createOrder(orderCommand);
		return ApiResponse.success(OrderResponse.from(infoOrder));
	}
	
}
