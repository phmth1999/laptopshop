package com.phmth.laptopshop.controller.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.OrderDto;
import com.phmth.laptopshop.dto.reponse.MessageResponse;
import com.phmth.laptopshop.exception.OrderException;
import com.phmth.laptopshop.service.IOrderService;
import com.phmth.laptopshop.utils.IdLogged;

@RestController
@RequestMapping("/user/")
public class PurchaseHistoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(PurchaseHistoryController.class);
	
	@Autowired
	private IOrderService orderService;
	
	@GetMapping("purchase-history")
	public ModelAndView historyPage() {
		
		ModelAndView mav = new ModelAndView("/web/purchase-history/index");
		
		List<OrderDto> orderEntity = orderService.findByUser(IdLogged.getId());
		
		mav.addObject("order", orderEntity);
		
		return mav;
	}
	
	@PostMapping("purchase-history/delete")
	public MessageResponse deleteHistoryDetailPage(@RequestParam("id") long id) {
		
		String message = "";
		OrderDto data = null;
		try {
			boolean updateStatus = orderService.cancelOrder(id);
			if(updateStatus) {
				message = "Cancel order successfully!";
				data = orderService.findById(id).get();
			}else {
				message = "Cancel order failed!";
			}
		} catch (OrderException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		}
		
		return new MessageResponse(message, data);
	}
	
}
