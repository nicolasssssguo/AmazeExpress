package com.nicolasguo.express.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseAction {
	
	@RequestMapping("/{viewName}")
	public String viewResolver(@PathVariable String viewName){
		return viewName;
	}
	
	@RequestMapping("/{module}/{action}")
	public ModelAndView viewResolver(@PathVariable String module, @PathVariable String action){
		ModelAndView mav = new ModelAndView();
		mav.setViewName(module + "/" + action);
		mav.addObject("module", module);
		return mav;
	}
}
