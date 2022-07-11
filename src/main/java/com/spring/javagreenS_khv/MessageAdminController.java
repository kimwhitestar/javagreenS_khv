package com.spring.javagreenS_khv;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageAdminController {
	
	@RequestMapping(value="/msgAdmin/{msgFlag}", method=RequestMethod.GET)
	public String msgGet(@PathVariable String msgFlag, Model m, HttpSession session) {
		System.out.println("msgFlag : " + msgFlag);
		
		String sLevelName = (String)session.getAttribute("sLevelName");
		
    if (msgFlag.equals("LoginNo")) { 
    	m.addAttribute("msg", "로그인 실패"); 			
    	m.addAttribute("url", "admin/adminLogin");
  	} else if (msgFlag.equals("LoginOk")) { 
    	m.addAttribute("msg", sLevelName + "님 로그인됬습니다");
    	m.addAttribute("url", "admin/adminIndex");
  	} else if (msgFlag.equals("LogoutOk")) { 
    	m.addAttribute("msg", sLevelName + "님 로그아웃됬습니다"); 
    	m.addAttribute("url", "admin/adminLogin");
  	}
    
		return "msg/message";
	}
}