package com.phmth.laptopshop.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.FormSearchOrder;
import com.phmth.laptopshop.dto.ResponseMessage;
import com.phmth.laptopshop.entity.OrderEntity;
import com.phmth.laptopshop.enums.StateOrder;
import com.phmth.laptopshop.exception.OrderException;
import com.phmth.laptopshop.service.IOrderService;

@RestController("adminOrder")
@RequestMapping("/admin/order")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private IOrderService orderService;

	@GetMapping
	public ModelAndView historyPage(@RequestParam(name = "page", defaultValue = "1") int page) {

		ModelAndView mav = new ModelAndView("/admin/order/index");
		int limit = 3;
		Page<OrderEntity> listPageOrder = orderService.findAll(page, limit);
		List<OrderEntity> listOrder = listPageOrder.getContent();

		mav.addObject("formSearchOrder", new FormSearchOrder());

		mav.addObject("order", listOrder);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageOrder.getTotalPages());

		return mav;
	}

	@PostMapping
	public ModelAndView history(@ModelAttribute("formSearchOrder") FormSearchOrder formSearchOrder,
			@RequestParam(name = "page", defaultValue = "1") int page) {

		ModelAndView mav = new ModelAndView("/admin/order/index");
		int limit = 3;
		Page<OrderEntity> listPageOrder = orderService.findAll(page, limit, formSearchOrder);
		List<OrderEntity> listOrder = listPageOrder.getContent();

		mav.addObject("formSearchOrder", formSearchOrder);

		mav.addObject("order", listOrder);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageOrder.getTotalPages());

		return mav;
	}

	@PostMapping("/verify")
	public ResponseMessage deleteProduct(@RequestParam("id") long id, @RequestParam("status") StateOrder status) {

		String message = "";
		OrderEntity data = null;
		try {
			boolean verify = orderService.updateStateOrder(id, status);
			if (verify) {
				message = "Verify successfully!";
				data = orderService.findOne(id).get();
			} else {
				message = "Verify failed!";
			}
		} catch (OrderException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		}

		return new ResponseMessage(message, data);
	}

}
