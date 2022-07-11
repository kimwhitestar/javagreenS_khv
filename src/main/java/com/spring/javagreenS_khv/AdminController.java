package com.spring.javagreenS_khv;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javagreenS_khv.dto.CustomCompDTO;
import com.spring.javagreenS_khv.service.AdminService;
import com.spring.javagreenS_khv.vo.AdminLoginVO;
import com.spring.javagreenS_khv.vo.CustomCompEntryUpdateFormVO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	public AdminService adminService;
	
	//로그인화면 이동
	@RequestMapping(value="/adminLogin", method=RequestMethod.GET)
	public String adminLoginGet(HttpServletRequest request) {
		return "admin/adminLogin";
	}
	
	//로그인
	@RequestMapping(value="/adminLogin", method=RequestMethod.POST)
	public String adminLoginPost(HttpSession session, AdminLoginVO adminLoginVo, Model model) {
		AdminLoginVO vo = adminService.searchAdminLogin(adminLoginVo.getLoginId(), adminLoginVo.getEncryptPwd());
		if (null == vo) {
			return "redirect:/msgAdmin/LoginNo";
		}
		model.addAttribute("vo", vo);
		
		//로그인 아이디,비밀번호로 회원조회가 됬을 경우, HttpSession에 조회된 회원정보 설정
		session.setAttribute("sLoginId", vo.getLoginId());
		session.setAttribute("sLevel", vo.getLevel());
		session.setAttribute("sLevelName", vo.getLevelName());
		
		return "redirect:/msgAdmin/LoginOk";
	}
	
	//로그아웃
	@RequestMapping(value="/adminLogout", method=RequestMethod.GET)
	public String adminLogoutGet(HttpSession session) {
		String sLoginId = (String)session.getAttribute("sLoginId");
		adminService.updateLogout(sLoginId);//DB 로그아웃정보 수정
//		if (1 == res) {
			session.invalidate();//세션삭제
			return "redirect:/msgAdmin/LogoutOk";
//		} else {
//			return "redirect:/msgAdmin/LogoutNo";
//		}
	}
	
	//기업회원탈퇴목록화면 이동
	@RequestMapping(value="/customCompDeleteList", method=RequestMethod.GET)
	public String customCompDeleteListGet(Model model) {
		//한 페이징에 표시할 레코드 검색
		List<CustomCompDTO> dtos = adminService.searchCustomCompDeleteList(paging.getStartIndexNo(), paging.getPageSize());
		
		if (null == dtos) return "redirect:/msgAdmin/";//화면으로 이동
		
		List<CustomCompEntryUpdateFormVO> vos = new ArrayList<>();
		CustomCompEntryUpdateFormVO customCompVo = null;
		for (CustomCompDTO compDto : dtos) {
			//Form출력 설정
			customCompVo = new CustomCompEntryUpdateFormVO();
			customCompVo.setCustomName(compDto.getCustom_nm());
			customCompVo.setCompanyNo(compDto.getCompany_no());
			customCompVo.setCustomKindCode(String.valueOf(compDto.getCustom_kind_cd()));
			customCompVo.setOverDaysUserDel(compDto.getOver_days_user_del());
			vos.add(customCompVo);
		}
		
		model.addAttribute("vos", vos);
		return "admin/customCompDeleteList";
	}

	//기업회원탈퇴
	@RequestMapping(value="/customCompDeleteList", method=RequestMethod.POST)
	public String customCompDeleteListPost() {
		return "";
	}

	//개인회원탈퇴목록화면 이동
	@RequestMapping(value="/customPersonDeleteList", method=RequestMethod.GET)
	public String customPersonDeleteListGet() {
		return "";
	}

	//개인회원탈퇴
	@RequestMapping(value="/customPersonDeleteList", method=RequestMethod.POST)
	public String customPersonDeleteListPost() {
		return "";
	}
}