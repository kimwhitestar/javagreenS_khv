package com.spring.javagreenS_khv;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javagreenS_khv.service.CustomPersonService;
import com.spring.javagreenS_khv.vo.CustomPersonEntryUpdateFormVO;
import com.spring.javagreenS_khv.vo.CustomPersonLoginVO;
import com.spring.javagreenS_khv.vo.CustomPersonVO;

//개인고객회원관리Controller
@Controller
@RequestMapping("/customPerson")
public class CustomPersonController {
	@Autowired
	public CustomPersonService customPersonService;
	
	//로그인화면 이동
	@RequestMapping(value="/customPersonLogin", method=RequestMethod.GET)
	public String customPersonLoginGet(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String cLoginId = "";
		for (int i=0; i<cookies.length; i++) {
			if (cookies[i].getName().equals("cLoginId")) {
				cLoginId = cookies[i].getValue();
				request.setAttribute("loginId", cLoginId);
				break;
			}
		}
		return "/custom/person/customPersonLogin";
	}

	//로그인 
	@RequestMapping(value="/customPersonLogin", method=RequestMethod.POST)
	public String customPersonLoginPost(HttpSession session, HttpServletRequest request, HttpServletResponse response,
		@RequestParam("id") String id,
		@RequestParam("encryptPwd") String encryptPwd,
		@RequestParam(name="idSave", defaultValue="", required=false) String idSave,
		Model model) {//Model쓸때는 RedirectAttribute를 같이 쓸 수 없다
		
		// --------------------------------------------------
		// 로그인 성공시  내용
		// --------------------------------------------------
		// 1.오늘방문횟수, 전체방문횟수 1씩 증가 
		// 2.포인터 증가(1일 10회까지 방문시마다 100포인트씩 증가)
		// 3.주요자료 세션 저장 
		// 4.아이디 저장유무에 따라 쿠키 저장
		// --------------------------------------------------
		CustomPersonLoginVO loginVo = customPersonService.searchLogin(id, encryptPwd);
		if (null == loginVo) {
			return "custom/person/customPsersonLogin";
		}
		
		//로그인 아이디,비밀번호로 회원조회가 됬을 경우, HttpSession에 조회된 회원정보 설정
		session.setAttribute("sLoginId", loginVo.getId());
		session.setAttribute("sGradeCode", loginVo.getCustom_grade());//고객등급
		session.setAttribute("sGradeName", loginVo.getGrade_name());//고객등급명
		session.setAttribute("sCustomId", loginVo.getCustom_id());//고객ID -- SEQ로 바꾸자
		session.setAttribute("sCustomName", loginVo.getCustom_name());//고객명
		session.setAttribute("sLoginDate", loginVo.getLogin_date());//로그인날짜
		
		// --------------------------------------------------
		// DB 저장 : 오늘방문횟수, 전체방문횟수, 포인터 100씩 증가
		// --------------------------------------------------
		//최종방문일과 오늘날짜 비교해서 다른 경우, 오늘방문횟수(todayCnt)값을 0으로 초기화
    String todayYmdhms = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    String todayYmd = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		if (null == loginVo.getLogin_date() || ! loginVo.getLogin_date().substring(0, 10).equals(todayYmd)) {
			customPersonService.updateTodayCnt(loginVo.getId(), loginVo.getCustom_id());//DB저장(오늘방문횟수 '0', 로그인날짜 default now())
			loginVo.setToday_cnt(0);
			loginVo.setLogin_date(todayYmdhms);
		}
		//1.오늘방문횟수, 전체방문횟수 1씩 증가 
		customPersonService.updateVisitCntAndTodayCnt(loginVo.getId(), loginVo.getCustom_id());//DB 방문횟수 증가
		loginVo.setToday_cnt(loginVo.getToday_cnt() + 1);
		loginVo.setVisit_cnt(loginVo.getVisit_cnt() + 1);
		if (10 >= loginVo.getToday_cnt()) {
			//2.포인터 100씩 증가(방문시마다 100포인트씩 증가<DB저장>, 1일 10회 이하)
			customPersonService.updatePoint(loginVo.getId(), loginVo.getCustom_id());//DB 포인트 100포인트 증가
			loginVo.setPoint(loginVo.getPoint() + 100);
		}
		// --------------------------------------------------
		// 세션 저장(Mypage 회원전용방 출력용) : 오늘방문횟수, 전체방문횟수, 포인트
		// --------------------------------------------------
		session.setAttribute("sTodayVCnt", loginVo.getToday_cnt());
		session.setAttribute("sVCnt", loginVo.getVisit_cnt());
		session.setAttribute("sPoint", loginVo.getPoint());
		
		//idSave체크시 : 쿠키에 아이디(id)를 저장 checkbox checked 클릭 여부 - on/null
		//id저장
		if (idSave.equals("on")) {
			Cookie cookie = new Cookie("cLoginId", id);
			cookie.setMaxAge(60*60*24*7); //쿠키저장기간 : 7일(단위:초)
			response.addCookie(cookie);
		} else {
			Cookie[] cookies = request.getCookies();
			for (int i=0; i<cookies.length; i++) {
				if (cookies[i].getName().equals("cLoginId")) {
					cookies[i].setMaxAge(0); //쿠키저장기간 : 0일(단위:초) -> 삭제
					response.addCookie(cookies[i]);
					break;
				}
			}
		}
		
		return "redirect:/msgCustomPerson/LoginOk";
	}
	
	//회원전용방
	@RequestMapping(value="/customPersonMain", method=RequestMethod.GET)
	public String customPersonMainGet(HttpSession session, CustomPersonLoginVO loginVo, Model model) {
		String sLoginId = (String)session.getAttribute("sLoginId");
		String sGradeCode = (String)session.getAttribute("sGradeCode");
		
		if ((null == sLoginId || 0 == sLoginId.trim().length()) 
			&& (null == sGradeCode || 0 == sGradeCode.trim().length())) {
			//비회원 화면
			return "redirect:/msgCustomPerson/LoginNo";
		}
		
		return "custom/person/customPersonMain";
	}

	//회원탈퇴(기업고객로그인테이블) - 30일 회원정보유지, 회원로그인정보 임시삭제(delete_date=탈퇴날짜(회원탈퇴))
	@RequestMapping(value="/customPersonDeletePract", method=RequestMethod.GET)
	public String customPersonDeletePractGet(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sLoginId = (String) session.getAttribute("sLoginId");
		int sCustomId = (int) session.getAttribute("sCustomId");
		
		//회원탈퇴 - 1달간은 회원정보유지, deleteDate와 logoutDate를 now()로 수정
		customPersonService.updateCustomPersonLoginUserDel(sLoginId, sCustomId);
		
//		if (1 == res) {
			return "redirect:/msgcustomPerson/DeletePractOk";
//		} else {
//			return "redirect:/msgCustomPerson/DeletePractNo";
//		}
	}

	//로그아웃
	@RequestMapping(value="/customPersonLogout", method=RequestMethod.GET)
	public String customPersonLogoutGet(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sLoginId = (String)session.getAttribute("sLoginId");
		int sCustomId = (int) session.getAttribute("sCustomId");
		
		customPersonService.updateLogout(sLoginId, sCustomId);//DB 로그아웃정보 수정
//		if (1 == res) {
			session.invalidate();//세션삭제
			return "redirect:/msgCustomPerson/LogoutOk";
//		} else {
//			return "redirect:/msgCustomPerson/LogoutNo";
//		}
	}

	//회원가입화면 이동
	@RequestMapping(value="/customPersonEntry", method=RequestMethod.GET)
	public String customPersonEntryGet() {
		return "custom/person/customPersonEntry";
	}
	
	//회원가입화면 이동
	@RequestMapping(value="/customPersonEntry", method=RequestMethod.POST)
	public String customPersonEntryPost(CustomPersonEntryUpdateFormVO entryVo) {

		CustomPersonLoginVO loginVo = new CustomPersonLoginVO();
		CustomPersonVO personVo = new CustomPersonVO();

		//개인고객아이디 발급
		//CUSTOM_ID 구성 : 3자리(100~999) 'CUSTOM_KIND_CD' + 5자리 '순차발행' (00001~99999))
		//CUSTOM_KIND_CD '1', '2'의 경우는 '100'으로 설정 
		int customKindCode = entryVo.getCustomKindCode();
		int customId = customPersonService.obtainCustomId(customKindCode);

		//개인고객 회원정보 VO 설정
		personVo.setCustom_id(customId);
		personVo.setCustom_nm(entryVo.getCustomName());
		personVo.setCustom_kind_cd(customKindCode);
		personVo.setJob(entryVo.getJob());
		personVo.setTxt_job(entryVo.getTxtJob());
		personVo.setPost_code(entryVo.getPostcode());
		personVo.setRoad_addr(entryVo.getRoadAddress());
		personVo.setExtra_addr(entryVo.getExtraAddress());
		personVo.setDetail_addr(entryVo.getDetailAddress());
		personVo.setEmail(entryVo.getEmail());
		personVo.setJumin_no(entryVo.getJuminNo());
		//personVo.setGender(entryVo.getGender());
		personVo.setBirth_date(entryVo.getBirthDate());
		personVo.setTel_no(entryVo.getTelNo());
		personVo.setHp_no(entryVo.getHpNo());
		personVo.setHobby(entryVo.getCheckedHobbies());
		personVo.setMemo(entryVo.getMemo());
		
		//개인고객 로그인 VO 설정
		loginVo.setId(entryVo.getId());
		loginVo.setEncrypt_pwd(entryVo.getEncryptPwd());
		loginVo.setCustom_id(customId);
		
		//개인고객 회원정보 DB 등록, 개인고객 로그인 DB 등록
		customPersonService.insertCustomCompAndCustomCompLogin(personVo, loginVo);
		
//	if (1 == resLogin && 1 == resComp) {
			return "redirect:/msgCustomComp/EntryOk";
//	} else {
//			return "redirect:/msgCustomComp/EntryNo";
//	}		
	}
	
	//로그인ID중복체크화면 이동
	@RequestMapping(value="/customPersonLoginIdCheck", method=RequestMethod.GET)
	public String customPersonLoginIdCheckGet() {
		return "custom/person/customPersonLoginIdCheck";
	}
	
	//로그인ID중복체크
	@RequestMapping(value="/customPersonLoginIdCheck", method=RequestMethod.POST)
	public String customPersonLoginIdCheckPost(
			@RequestParam(name="id", defaultValue="", required=true) String id,
			Model model) {
		model.addAttribute("id", id);
		//isExist = true 아이디 중복
		if (customPersonService.loginIdCheck(id)) {
			model.addAttribute("existIdYN", "Y");
		} else {
			model.addAttribute("existIdYN", "N");
		}
		return "custom/person/customPersonLoginIdCheck";
	}
	
	//주민등록번호중복체크화면 이동
	@RequestMapping(value="/customPersonJuminNoCheck", method=RequestMethod.GET)
	public String customPersonJuminNoCheckGet() {
		return "custom/person/customPersonJuminNoCheck";
	}
	
	//주민등록번호중복체크
	@RequestMapping(value="/customPersonJuminNoCheck", method=RequestMethod.POST)
	public String customPersonJuminNoCheckPost(
		@RequestParam(name="juminNo", defaultValue="", required=true) String juminNo,
		Model model) {
		
		model.addAttribute("juminNo", juminNo);
		model.addAttribute("existJuminNoYN", null);
		//isExist = true 아이디 중복
		if (customPersonService.juminNoCheck(juminNo)) {
			model.addAttribute("existJuminNoYN", "Y");
		} else {
			model.addAttribute("existJuminNoYN", "N");
		}
		return "custom/person/customPersonJuminNoCheck";
	}
	
	//이메일중복체크화면 이동
	@RequestMapping(value="/customPersonEmailCheck", method=RequestMethod.GET)
	public String customPersonEmailCheckGet() {
		return "custom/person/customPersonEmailCheck";
	}
	
	//이메일중복체크
	@RequestMapping(value="/customPersonEmailCheck", method=RequestMethod.POST)
	public String customPersonEmailCheckPost(
		@RequestParam(name="email", defaultValue="", required=true) String email,
		@RequestParam(name="email1", defaultValue="", required=true) String email1,
		@RequestParam(name="email2", defaultValue="", required=true) String email2,
		@RequestParam(name="txtEmail2", defaultValue="", required=true) String txtEmail2,
		Model model) {
		
		model.addAttribute("email", email);
		model.addAttribute("email1", email1);
		model.addAttribute("email2", email2);
		model.addAttribute("txtEmail2", txtEmail2);
			//이메일 중복 / 존재 체크
			if (customPersonService.emailCheck(email)) {
				model.addAttribute("existEmailYN", "Y");
			} else {
				model.addAttribute("existEmailYN", "N");
			}
			return "custom/person/customPersonEmailCheck";
	}
	
	//회원정보수정화면 이동
	@RequestMapping(value="/customPersonUpdate", method=RequestMethod.GET)
	public String customPersonUpdateGet() {
		return "custom/person/customPersonUpdate";
	}
	
}