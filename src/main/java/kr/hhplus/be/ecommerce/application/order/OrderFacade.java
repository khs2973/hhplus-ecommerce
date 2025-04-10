package kr.hhplus.be.ecommerce.application.order;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.application.point.PointService;
import kr.hhplus.be.ecommerce.domain.order.Order;
import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.interfaces.common.ApiResponse;
import kr.hhplus.be.ecommerce.interfaces.order.OrderRequest;
import kr.hhplus.be.ecommerce.interfaces.order.OrderResponse;

@Service
public class OrderFacade {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	PointService pointService;

	@Transactional
	public ApiResponse<OrderResponse> createOrder(OrderRequest orderRequest) {
		
		Product product = orderService.validateProduct(orderRequest.getProductId());

		BigDecimal totalProductPrice = product.getProductPrice()
											  .multiply(BigDecimal.valueOf(orderRequest.getOrderQuantity()));

		BigDecimal totalPrice = totalProductPrice.subtract(orderRequest.getDiscountPrice())
												 .subtract(orderRequest.getUsedPoint());

		if (totalPrice.compareTo(BigDecimal.ZERO) < 0) {
			totalPrice = BigDecimal.ZERO;
		}

		BigDecimal remainingPoint = pointService.usePoint(orderRequest.getUserId(), totalPrice);

		Order order = orderService.createOrder(orderRequest, product);

		orderService.recordOrderHistory(order);

		OrderResponse response = new OrderResponse(order.getOrderId()
												, order.getUserId()
												, order.getTotalPrice()
												, remainingPoint
												, orderRequest.getDiscountPrice()
												, orderRequest.getUsedPoint());

		return ApiResponse.success(response);
	}
}
