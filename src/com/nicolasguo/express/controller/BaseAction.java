package com.nicolasguo.express.controller;

import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.nicolasguo.express.condition.impl.ExpressCondition;
import com.nicolasguo.express.entity.Express;
import com.nicolasguo.express.entity.Page;
import com.nicolasguo.express.service.ExpressService;

@Controller
public class BaseAction {
	
	@Resource(name = "expressService")
	private ExpressService<Express, String> expressService;
	
	@RequestMapping("/index")
	public ModelAndView index(
			@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo, HttpSession session) {
		Page<Express> pageEntity = new Page<Express>();
		pageEntity.setPageNo(pageNo);
		/*Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);*/
		ExpressCondition condition = new ExpressCondition();
		condition.setStartDate(new Date());
		condition.setEndDate(new Date());
		session.setAttribute("expressCondition", condition);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		mav.addObject("expressPage", expressService.findExpressByCondition(condition, pageEntity));
		return mav;
	}
}
