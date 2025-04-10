package kr.hhplus.be.ecommerce.interfaces.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.hhplus.be.ecommerce.application.order.OrderFacade;
import kr.hhplus.be.ecommerce.interfaces.common.ApiResponse;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderFacade orderFacade;
	
	@PostMapping("/order")
	public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
		return orderFacade.createOrder(orderRequest);
	}
	
}
