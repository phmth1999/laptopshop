package com.phmth.laptopshop.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.FeedbackDto;
import com.phmth.laptopshop.service.IFeedbackService;

@RestController("adminFeedback")
@RequestMapping("/admin/feedback")
public class FeedbackController {
	
	private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

	@Autowired
	private IFeedbackService feedbackService;
	
	@GetMapping
	public ModelAndView showFeedbackPage(@RequestParam(name = "page", defaultValue = "1") int page) {
		
		int limit = 3;
		
		ModelAndView mav = new ModelAndView("/admin/feedback/index");
		
		try {
			Page<FeedbackDto> listPageFeedback = feedbackService.findAll(page, limit);
			if(listPageFeedback != null) {
				List<FeedbackDto> listFeedback = listPageFeedback.getContent();
				mav.addObject("feedback", listFeedback);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageFeedback.getTotalPages());
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
	@PostMapping
	public ModelAndView processFeedbackPage(@RequestParam(name = "page", defaultValue = "1") int page) {
		
		int limit = 3;
		
		ModelAndView mav = new ModelAndView("/admin/feedback/index");
		
		try {
			Page<FeedbackDto> listPageFeedback = feedbackService.findAll(page, limit);
			if(listPageFeedback != null) {
				List<FeedbackDto> listFeedback = listPageFeedback.getContent();
				mav.addObject("feedback", listFeedback);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageFeedback.getTotalPages());
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
}
