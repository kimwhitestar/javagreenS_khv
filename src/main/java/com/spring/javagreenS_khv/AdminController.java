package com.spring.javagreenS_khv;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javagreenS_khv.dto.CustomCompDeleteDTO;
import com.spring.javagreenS_khv.dto.CustomCompLoginDTO;
import com.spring.javagreenS_khv.dto.CustomPersonDeleteDTO;
import com.spring.javagreenS_khv.dto.CustomPersonLoginDTO;
import com.spring.javagreenS_khv.service.AdminService;
import com.spring.javagreenS_khv.service.FlgSummaryService;
import com.spring.javagreenS_khv.vo.AdminLoginVO;
import com.spring.javagreenS_khv.vo.CustomCompDeleteFormVO;
import com.spring.javagreenS_khv.vo.CustomCompSearchVO;
import com.spring.javagreenS_khv.vo.CustomPersonDeleteFormVO;
import com.spring.javagreenS_khv.vo.CustomPersonSearchVO;
import com.spring.javagreenS_khv.vo.FlgSummaryVO;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	public AdminService adminService;
	
	@Autowired
	public FlgSummaryService flgSummaryService;
	
//	@Autowired
//	public BCryptPasswordEncoder bCrypt;
	
	//로그인화면 이동
	@RequestMapping(value="/adminLogin", method=RequestMethod.GET)
	public String adminLoginGet(HttpServletRequest request) {
		logger.info("[" + new Object(){}.getClass().getEnclosingMethod().getName() + "]"); //현재 실행중인 메소드명
		return "admin/adminLogin";
	}
	
	//로그인
	@RequestMapping(value="/adminLogin", method=RequestMethod.POST)
	public String adminLoginPost(HttpSession session, AdminLoginVO adminLoginVo, Model model) {
		logger.info("[" + new Object(){}.getClass().getEnclosingMethod().getName() + "]"); //현재 실행중인 메소드명
		
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
	public String adminLogoutGet(HttpSession session, Model model) {
		logger.info("[" + new Object(){}.getClass().getEnclosingMethod().getName() + "]"); //현재 실행중인 메소드명

		String sLoginId = (String)session.getAttribute("sLoginId");
		String sLevelName = (String)session.getAttribute("sLevelName");
		
		adminService.updateLogout(sLoginId);//DB 로그아웃정보 수정
		
//		if (1 == res) {
			session.invalidate();//세션삭제
			model.addAttribute("levelName", sLevelName);
			return "redirect:/msgAdmin/LogoutOk";

//		} else {
//			return "redirect:/msgAdmin/LogoutNo";
//		}
			
	}

	//기업고객회원탈퇴목록화면 - 전체목록
	@RequestMapping(value="/customCompDeletePracList", method=RequestMethod.GET)
	public String customCompDeletePracListGet(
		@RequestParam(name="overFlg", defaultValue="NONE", required=false) String overFlg,
		Model model) {
		logger.info("[" + new Object(){}.getClass().getEnclosingMethod().getName() + "]"); //현재 실행중인 메소드명

		return customCompDeletePracListPost(overFlg, model);
	}

	//기업고객회원탈퇴목록화면 - 조건목록
	@RequestMapping(value="/customCompDeletePracList", method=RequestMethod.POST)
	public String customCompDeletePracListPost(
		@RequestParam(name="overFlg", defaultValue="NONE", required=false) String overFlg,
		Model model) {
		logger.info("[" + new Object(){}.getClass().getEnclosingMethod().getName() + "]"); //현재 실행중인 메소드명

		/*회원탈퇴후 30일 경과여부 flag 획득 (NONE:해당안됨|OVER:30일경과|PRAC:30일미경과)*/
		FlgSummaryVO flgSummaryVo = flgSummaryService.searchFlg("DELETE_OVER_FLG", "000", "180");
		model.addAttribute("delOverFlgVo", flgSummaryVo);
		
		//한 페이징에 표시할 레코드 검색
		List<CustomCompDeleteDTO> dtos = adminService.searchCustomCompDeleteList(overFlg);
		if (null == dtos || 0 == dtos.size()) {
			model.addAttribute("vos", null);
			model.addAttribute("overFlg", overFlg);
			return "admin/customCompDeletePracList";
		}
		
		List<CustomCompDeleteFormVO> vos = new ArrayList<>();
		CustomCompDeleteFormVO compDelVo = null;
		for (CustomCompDeleteDTO compDelDto : dtos) {
			//Form출력 설정
			compDelVo = new CustomCompDeleteFormVO();
			compDelVo.setLoginId(compDelDto.getLogin_id());
			compDelVo.setCustomId(compDelDto.getCustom_id());
			compDelVo.setCustomName(compDelDto.getCustom_nm());
			compDelVo.setCompanyNo(compDelDto.getCompany_no());
			compDelVo.setOverDaysUserDel(compDelDto.getOver_days_user_del());
			compDelVo.setOverFlg(compDelDto.getOver_flg());
			compDelVo.setDeleteDate(compDelDto.getDelete_date());
			compDelVo.setDeleteUser(compDelDto.getDelete_user());
			vos.add(compDelVo);
		}
		model.addAttribute("vos", vos);
		model.addAttribute("overFlg", overFlg);
		
		return "admin/customCompDeletePracList";
	}
	
	//개인고객회원탈퇴목록화면 이동
	@RequestMapping(value="/customPersonDeletePracList", method=RequestMethod.GET)
	public String customPersonDeletePracListGet(
		@RequestParam(name="overFlg", defaultValue="NONE", required=false) String overFlg,
		Model model) {
		logger.info("[" + new Object(){}.getClass().getEnclosingMethod().getName() + "]"); //현재 실행중인 메소드명

		return customPersonDeletePracListPost(overFlg, model);
	}
	
	//개인고객회원탈퇴목록화면 - 조건목록
	@RequestMapping(value="/customPersonDeletePracList", method=RequestMethod.POST)
	public String customPersonDeletePracListPost(
		@RequestParam(name="overFlg", defaultValue="NONE", required=false) String overFlg,
		Model model) {
		logger.info("[" + new Object(){}.getClass().getEnclosingMethod().getName() + "]"); //현재 실행중인 메소드명
		
		/*회원탈퇴후 30일 경과여부 flag 획득 (NONE:해당안됨|OVER:30일경과|PRAC:30일미경과)*/
		FlgSummaryVO flgSummaryVo = flgSummaryService.searchFlg("DELETE_OVER_FLG", "000", "180");
		model.addAttribute("delOverFlgVo", flgSummaryVo);
		
		//한 페이징에 표시할 레코드 검색
		List<CustomPersonDeleteDTO> dtos = adminService.searchCustomPersonDeleteList(overFlg);
		if (null == dtos || 0 == dtos.size()) {
			model.addAttribute("vos", null);
			model.addAttribute("overFlg", overFlg);
			return "admin/customPersonDeletePracList";
		}
		
		List<CustomPersonDeleteFormVO> vos = new ArrayList<>();
		CustomPersonDeleteFormVO personDelVo = null;
		for (CustomPersonDeleteDTO personDelDto : dtos) {
			//Form출력 설정
			personDelVo = new CustomPersonDeleteFormVO();
			personDelVo.setLoginId(personDelDto.getLogin_id());
			personDelVo.setCustomId(personDelDto.getCustom_id());
			personDelVo.setCustomName(personDelDto.getCustom_nm());
			personDelVo.setBirthDate(personDelDto.getBirth_date());
			personDelVo.setOverDaysUserDel(personDelDto.getOver_days_user_del());
			personDelVo.setOverFlg(personDelDto.getOver_flg());
			personDelVo.setDeleteDate(personDelDto.getDelete_date());
			personDelVo.setDeleteUser(personDelDto.getDelete_user());
			vos.add(personDelVo);
		}
		model.addAttribute("vos", vos);
		model.addAttribute("overFlg", overFlg);
		return "admin/customPersonDeletePracList";
	}
	
	
	//기업고객 탈퇴회원신청자 회원삭제(탈퇴신청 1개월차)
	@RequestMapping(value="/customCompDeletePrac", method=RequestMethod.POST)
	public String customCompDeletePracPost(
		@RequestParam(name="delCustomId", required=false) String[] delCustomId,
		@RequestParam(name="onceDelCustomId", defaultValue="", required=false) String onceDelCustomId,
		@RequestParam(name="overFlg", defaultValue="NONE", required=false) String overFlg,
		Model model) {
		logger.info("[" + new Object(){}.getClass().getEnclosingMethod().getName() + "]"); //현재 실행중인 메소드명
		
		if (!onceDelCustomId.isEmpty()) {
			adminService.deleteCustomCompDelete(onceDelCustomId);
			model.addAttribute("overFlg", overFlg);
			return "redirect:/msgAdmin/CompDeleteOk";
		}
		for (String customId : delCustomId) {
			adminService.deleteCustomCompDelete(customId);
		}
		model.addAttribute("overFlg", overFlg);
		return "redirect:/msgAdmin/CompDeleteOk";
	}

	//개인고객 탈퇴회원신청자 회원삭제(탈퇴신청 1개월차)
	@RequestMapping(value="/customPersonDeletePrac", method=RequestMethod.POST)
	public String customPersonDeletePracPost(
		@RequestParam(name="delCustomId", required=false) String[] delCustomId,
		@RequestParam(name="onceDelCustomId", defaultValue="", required=false) String onceDelCustomId,
		@RequestParam(name="overFlg", defaultValue="NONE", required=false) String overFlg,
		Model model) {
		
		logger.info("[" + new Object(){}.getClass().getEnclosingMethod().getName() + "]"); //현재 실행중인 메소드명
		
		if (!onceDelCustomId.isEmpty()) {
			adminService.deleteCustomPersonDelete(onceDelCustomId);
			model.addAttribute("overFlg", overFlg);
			return "redirect:/msgAdmin/PersonDeleteOk";
		}
		
		for (String customId : delCustomId) {
			adminService.deleteCustomPersonDelete(customId);
		}
		model.addAttribute("overFlg", overFlg);
		return "redirect:/msgAdmin/PersonDeleteOk";
	}

	//기업고객회원통계화면 이동
	@RequestMapping(value="/customCompStats", method=RequestMethod.GET)
	public String customCompStatsGet(Model model) {
		logger.info("[" + new Object(){}.getClass().getEnclosingMethod().getName() + "]"); //현재 실행중인 메소드명

		//기간별(년월), 고객구분코드별 신규가입회원 통계
		
		
		//기간별(년월), 고객구분코드별 탈퇴신청회원 통계
		
		//기간별(년월), 고객구분코드별 탈퇴회원 통계
		
		//model.addAttribute("", );
		return "admin/customCompStats";
	}
	
	//개인고객회원통계화면 이동
	@RequestMapping(value="/customPersonStats", method=RequestMethod.GET)
	public String customPersonStatsGet(Model model) {
		logger.info("[" + new Object(){}.getClass().getEnclosingMethod().getName() + "]"); //현재 실행중인 메소드명

		//기간별(년월), 고객구분코드별 신규가입회원 통계
		
		//기간별(년월), 고객구분코드별, 성별 탈퇴신청회원 통계
		
		//기간별(년월), 고객구분코드별, 성별 탈퇴회원 통계
		
		//model.addAttribute("", );
		return "admin/customPersonStats";
	}
	
	//관리자 메인화면 (차후 기업고객,개인고객서비스검색으로 수정함)
	@RequestMapping(value="/adminIndex", method=RequestMethod.GET)
	public String adminIndexGet(Model model) {
		logger.info("[" + new Object(){}.getClass().getEnclosingMethod().getName() + "]"); //현재 실행중인 메소드명

		/* 기업고객 대시보드 목록 조회 */
		CustomCompSearchVO compVo = null;
		//신규회원가입한 목록 - 가입일 1개월차
		List<CustomCompLoginDTO> compRecentlyEntryDtoList = adminService.searchRecentlyEntryCustomCompList();
		List<CustomCompSearchVO> compRecentlyEntryVoList = new ArrayList<>(); 
		//최근접속회원목록
		List<CustomCompLoginDTO> compRecentlyLoginDtoList = adminService.searchRecentlyLoginCustomCompList();
		List<CustomCompSearchVO> compRecentlyLoginVoList = new ArrayList<>(); 
		//탈퇴회원목록 - 회원삭제대상자(임시탈퇴유지기간 30일을 경과한 회원)
		List<CustomCompLoginDTO> compPracDeleteDtoList = adminService.searchPracDeleteCustomCompList();
		List<CustomCompSearchVO> compPracDeleteVoList = new ArrayList<>(); 

		/* 개인고객 대시보드 목록 조회 */
		CustomPersonSearchVO personVo = null;
		//신규회원가입한 목록 - 가입일 1개월차
		List<CustomPersonLoginDTO> personRecentlyEntryDtoList = adminService.searchRecentlyEntryCustomPersonList();
		List<CustomPersonSearchVO> personRecentlyEntryVoList = new ArrayList<>(); 
		//최근접속회원목록
		List<CustomPersonLoginDTO> personRecentlyLoginDtoList = adminService.searchRecentlyLoginCustomPersonList();
		List<CustomPersonSearchVO> personRecentlyLoginVoList = new ArrayList<>(); 
		//탈퇴회원목록 - 회원삭제대상자(임시탈퇴유지기간 30일을 경과한 회원)
		List<CustomPersonLoginDTO> personPracDeleteDtoList = adminService.searchPracDeleteCustomPersonList();
		List<CustomPersonSearchVO> personPracDeleteVoList = new ArrayList<>(); 
		
		for (CustomCompLoginDTO compDto : compRecentlyEntryDtoList) {
			compVo = new CustomCompSearchVO();
			compVo.setLoginId(compDto.getLogin_id());
			compVo.setCustomNameShort(compDto.getCustom_nm_short());
			compVo.setCustomKindName(compDto.getCustom_kind_nm());
			compVo.setCreateDate(compDto.getCreate_date());
			compRecentlyEntryVoList.add(compVo);
		}
		for (CustomCompLoginDTO compDto : compRecentlyLoginDtoList) {
			compVo = new CustomCompSearchVO();
			compVo.setLoginId(compDto.getLogin_id());
			compVo.setCustomNameShort(compDto.getCustom_nm_short());
			compVo.setCustomKindName(compDto.getCustom_kind_nm());
			compVo.setLoginDate(compDto.getLogin_date());
			compRecentlyLoginVoList.add(compVo);
		}
		for (CustomCompLoginDTO compDto : compPracDeleteDtoList) {
			compVo = new CustomCompSearchVO();
			compVo.setLoginId(compDto.getLogin_id());
			compVo.setCustomNameShort(compDto.getCustom_nm_short());
			compVo.setCustomKindName(compDto.getCustom_kind_nm());
			compVo.setDeleteDate(compDto.getDelete_date());
			compPracDeleteVoList.add(compVo);
		}
		for (CustomPersonLoginDTO personDto : personRecentlyEntryDtoList) {
			personVo = new CustomPersonSearchVO();
			personVo.setLoginId(personDto.getLogin_id());
			personVo.setCustomName(personDto.getCustom_nm());
			personVo.setCustomKindName(personDto.getCustom_kind_nm());
			personVo.setCreateDate(personDto.getCreate_date());
			personRecentlyEntryVoList.add(personVo);
		}
		for (CustomPersonLoginDTO personDto : personRecentlyLoginDtoList) {
			personVo = new CustomPersonSearchVO();
			personVo.setLoginId(personDto.getLogin_id());
			personVo.setCustomName(personDto.getCustom_nm());
			personVo.setCustomKindName(personDto.getCustom_kind_nm());
			personVo.setLoginDate(personDto.getLogin_date());
			personRecentlyLoginVoList.add(personVo);
		}
		for (CustomPersonLoginDTO personDto : personPracDeleteDtoList) {
			personVo = new CustomPersonSearchVO();
			personVo.setLoginId(personDto.getLogin_id());
			personVo.setCustomName(personDto.getCustom_nm());
			personVo.setCustomKindName(personDto.getCustom_kind_nm());
			personVo.setDeleteDate(personDto.getDelete_date());
			personPracDeleteVoList.add(personVo);
		}
		model.addAttribute("compRecentlyEntryVoList", compRecentlyEntryVoList);
		model.addAttribute("compRecentlyLoginVoList", compRecentlyLoginVoList);
		model.addAttribute("compPracDeleteVoList", compPracDeleteVoList);
		model.addAttribute("personRecentlyEntryVoList", personRecentlyEntryVoList);
		model.addAttribute("personRecentlyLoginVoList", personRecentlyLoginVoList);
		model.addAttribute("personPracDeleteVoList", personPracDeleteVoList);
		
		return "admin/adminIndex";
	}
}