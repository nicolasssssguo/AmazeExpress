package com.nicolasguo.express.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nicolasguo.express.condition.impl.UserCondition;
import com.nicolasguo.express.entity.Result;
import com.nicolasguo.express.entity.User;
import com.nicolasguo.express.service.UserService;

@Controller
@RequestMapping("/user")
public class UserAction {

	@Resource(name = "userService")
	private UserService<User, String> userService;

	@RequestMapping(value = "/login.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public @ResponseBody Result<String> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
		UserCondition condition = new UserCondition();
		condition.setUsername(username);
		condition.setPassword(password);
		List<User> result = userService.findUserByCondition(condition);
		if (result.size() > 0) {
			session.setAttribute("LOGIN_USER", result.get(0));
			return new Result<String>(HttpStatus.OK.value(), null);
		}
		return new Result<String>(HttpStatus.BAD_REQUEST.value(), "用户名或密码错误");
	}

	@RequestMapping("/logout.do")
	public String login(HttpSession session) {
		session.invalidate();
		return "redirect:/login.jsp";
	}
}
