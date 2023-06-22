package com.phmth.laptopshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.entity.FeedbackEntity;
import com.phmth.laptopshop.service.IFeedbackService;

@RestController("adminFeedback")
@RequestMapping("/admin/feedback")
public class FeedbackController {

	@Autowired
	private IFeedbackService feedbackService;
	
	@GetMapping
	public ModelAndView showFeedbackPage(@RequestParam(name = "page", defaultValue = "1") int page) {
		
		ModelAndView mav = new ModelAndView("/admin/feedback/index");
		
		int limit = 3;
		Page<FeedbackEntity> listPageFeedback = feedbackService.findAll(page, limit);
		List<FeedbackEntity> listFeedback = listPageFeedback.getContent();
		
		mav.addObject("feedback", listFeedback);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageFeedback.getTotalPages());
		
		return mav;
	}
	@PostMapping
	public ModelAndView processFeedbackPage(@RequestParam(name = "page", defaultValue = "1") int page) {
		
		ModelAndView mav = new ModelAndView("/admin/feedback/index");
		
		int limit = 3;
		Page<FeedbackEntity> listPageFeedback = feedbackService.findAll(page, limit);
		List<FeedbackEntity> listFeedback = listPageFeedback.getContent();
		
		mav.addObject("feedback", listFeedback);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageFeedback.getTotalPages());
		
		return mav;
	}
}
