package com.eeduspace.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.eeduspace.management.comm.Constants;

@Controller("test")
@RequestMapping("/test")
@SessionAttributes(Constants.SESSION_ID)
public class TestController {
	
	@RequestMapping("/test")
	public String testController(){
		System.out.println("测试Controller层~~~");
		return "userupdateaccount";
		
	}
	
}
