package com.spring.javagreenS_khv;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageCustomPersonController {

	@RequestMapping(value="/msgCustomPerson/{msgFlag}", method=RequestMethod.GET)
	public String msgGet(@PathVariable String msgFlag, Model m, HttpSession session) {
		System.out.println("msgFlag : " + msgFlag);
		
		String sCustomName = (String)session.getAttribute("sCustomName");
		
    if (msgFlag.equals("LoginNo")) { 
    	m.addAttribute("msg", "로그인 실패"); 			
    	m.addAttribute("url", "customPerson/customPersonLogin");
  	} else if (msgFlag.equals("LoginOk")) { 
    	m.addAttribute("msg", sCustomName + "님 로그인됬습니다");
    	m.addAttribute("url", "customPerson/customPersonMain");
  	} else if (msgFlag.equals("PwdNo")) { 
    	m.addAttribute("msg", sCustomName + "님, 잘못된 비밀번호입니다. 비밀번호를 다시 입력하세요.");
    	m.addAttribute("url", "customPerson/customPersonUpdate");
//    } else if (msgFlag.equals("IdFindoutNo")) { 
//    	m.addAttribute("msg", "아이디 찾기 실패"); 
//    	m.addAttribute("url", "/customPerson/customCompIdFindout");
//    } else if (msgFlag.equals("IdFindoutOk")) { 
//    	m.addAttribute("msg", "회원님의 아이디를 찾았습니다"); 
//    	m.addAttribute("url", "/customPerson/memberLogin");
//    } else if (msgFlag.equals("PwdChangeNo")) { 
//    	m.addAttribute("msg", "비밀번호 수정 실패"); 
//    	m.addAttribute("url", "/customPerson/memberLogin");
//    } else if (msgFlag.equals("PwdChangeOk")) { 
//    	m.addAttribute("msg", "회원님의 비밀번호를 수정했습니다"); 
//    	m.addAttribute("url", "/customPerson/memberLogin");
  	} else if (msgFlag.equals("LogoutOk")) { 
    	m.addAttribute("msg", sCustomName + "님 로그아웃됬습니다"); 
    	m.addAttribute("url", "customPerson/customPersonLogin");
    } else if (msgFlag.equals("LogoutNo")) { 
    	m.addAttribute("msg", "로그아웃 실패");
    	m.addAttribute("url", "customPerson/customPersonMain");
    } else if (msgFlag.equals("EntryOk")) { 
    	m.addAttribute("msg", "회원으로 가입됬습니다."); 
    	m.addAttribute("url", "customPerson/customPersonLogin");
    } else if (msgFlag.equals("EntryNo")) { 
    	m.addAttribute("msg", "회원 가입 실패"); 
    	m.addAttribute("url", "customPerson/customPersonEntry");
    } else if (msgFlag.equals("UpdateOk")) { 
    	m.addAttribute("msg", "회원정보가 수정됬습니다."); 
    	m.addAttribute("url", "customPerson/customPersonMain");
    } else if (msgFlag.equals("UpdateNo")) { 
    	m.addAttribute("msg", "회원정보 수정 실패"); 
    	m.addAttribute("url", "customPerson/customPersonUpdate");
//    } else if (msgFlag.equals("LevelUpdateOk")) { 
//    	m.addAttribute("msg", "회원레벨이 수정됬습니다"); 
//    	m.addAttribute("url", "/customPerson/memberLogin");
//    } else if (msgFlag.equals("LevelUpdateNo")) { 
//    	m.addAttribute("msg", "회원레벨 수정 실패"); 
//    	m.addAttribute("url", "/customPerson/memberLogin");
    } else if (msgFlag.equals("DeletePractOk")) { 
    	m.addAttribute("msg", sCustomName + "님 회원에서 탈퇴됬습니다"); 
    	m.addAttribute("url", "customPerson/customPersonLogout");
    } else if (msgFlag.equals("DeletePractNo")) {
    	m.addAttribute("msg", "회원 탈퇴 실패"); 
    	m.addAttribute("url", "customPerson/customPersonMain");
    } else if (msgFlag.equals("DeleteOk")) { 
    	m.addAttribute("msg", "회원DB에서 회원을 삭제했습니다"); 
    	m.addAttribute("url", "customPerson/customPersonLogin");
    } else if (msgFlag.equals("DeleteNo")) { 
    	m.addAttribute("msg", "회원DB에서 회원 삭제 실패"); 
    	m.addAttribute("url", "customPerson/customPersonMain");
    }		

		return "msg/message";
	}	
	
	
//	@RequestMapping(value="/msgLogout/{msgFlag}/{name}", method=RequestMethod.GET)
//	public String msgLogoutGet(@PathVariable String msgFlag, @PathVariable String name, Model m) {
//		System.out.println("msgFlag : " + msgFlag);
//		System.out.println("name : " + name);
//		
//		if (msgFlag.equals("memberLogout")) {
//			m.addAttribute("msg", name + "님 로그아웃됬습니다");
//			m.addAttribute("url", "/customPerson/memberLogin");
//		}
//		
//		return "include/message");
//	}
}