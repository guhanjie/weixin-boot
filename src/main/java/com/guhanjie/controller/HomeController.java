package com.guhanjie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.guhanjie.model.User;
import com.guhanjie.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController{
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	@RequestMapping(value="/user/{userId}", method=RequestMethod.GET)
	public String getUserById(@PathVariable("userId") Long id, Model model) {
		User user = userService.getUser(id);
		model.addAttribute("user", user);
		return "user";
	}
}
