package com.spring.javagreenS_khv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javagreenS_khv.dto.CustomCompDTO;
import com.spring.javagreenS_khv.dto.CustomCompLoginDTO;
import com.spring.javagreenS_khv.service.CustomCompService;
import com.spring.javagreenS_khv.vo.CustomCompEntryUpdateFormVO;
import com.spring.javagreenS_khv.vo.KakaoAddressVO;

//기업고객회원관리Controller
@Controller
@RequestMapping("/customComp")
public class CustomCompController {
	@Autowired
	public CustomCompService customCompService;
	
	//카카오맵 사용
	@RequestMapping(value="/kakaomap", method=RequestMethod.GET)
	public String kakaomapGet() {
		return "custom/comp/kakaomap/kakaomap";
	}
	
	//카카오맵 응용1 - Map 조회
	@RequestMapping(value="/kakaoEx1", method=RequestMethod.GET)
	public String kakaoEx1Get() {
		return "custom/comp/kakaomap/kakaoEx1";
	}
	
	//카카오맵 응용1 - 주소명으로 저장
	@ResponseBody
	@RequestMapping(value="/kakaoEx1", method=RequestMethod.POST)
	public String kakaoEx1Post(KakaoAddressVO vo) {
		KakaoAddressVO searchVo = customCompService.searchAddressName(vo.getAddress());
		if (null != searchVo) return "0";
		customCompService.insertAddressName(vo);
		return "1";
	}
	
	
	//카카오맵 응용2 - Map 조회
	@RequestMapping(value="/kakaoEx2", method=RequestMethod.GET)
	public String kakaoEx2Get(Model model, @RequestParam(name="address", defaultValue="db에 저장되어있는 주소명 한개", required=false) String address) {
		
		List<KakaoAddressVO> vos = customCompService.searchAddressNameList();
		KakaoAddressVO searchVo = customCompService.searchAddressName(address);
		
		model.addAttribute("vos", vos);
		model.addAttribute("vo", searchVo);
		model.addAttribute("address", address);
		
		return "custom/comp/kakaomap/kakaoEx2";
	}
	
	//카카오맵 응용2.2 - 지점명 DB에서 삭제
	@ResponseBody
	@RequestMapping(value="/kakaoEx2Delete", method=RequestMethod.GET)
	public String kakaoEx2DeleteGet(String address) {
		customCompService.kakaoEx2Delete(address);
		
		return "";
	}
	
	
	//로그인화면 이동
	@RequestMapping(value="/customCompLogin", method=RequestMethod.GET)
	public String customCompLoginGet(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String cLoginId = "";
		for (int i=0; i<cookies.length; i++) {
			if (cookies[i].getName().equals("cLoginId")) {
				cLoginId = cookies[i].getValue();
				request.setAttribute("loginId", cLoginId);
				break;
			}
		}
		return "custom/comp/customCompLogin";
	}
	
	//로그인
	@RequestMapping(value="/customCompLogin", method=RequestMethod.POST)
	public String customCompLoginPost(HttpSession session, HttpServletRequest request, HttpServletResponse response,
		@RequestParam("loginId") String loginId,
		@RequestParam("encryptPwd") String encryptPwd,
		@RequestParam(name="idSave", defaultValue="", required=false) String idSave,
		Model model) {//Model쓸때는 RedirectAttribute를 같이 쓸 수 없다
		
		// --------------------------------------------------
		// 로그인 성공시 처리 내용
		// --------------------------------------------------
		// 1.오늘방문횟수, 전체방문횟수 1씩 증가 
		// 2.포인터 증가(1일 10회까지 방문시마다 100포인트씩 증가)
		// 3.주요자료 세션 저장 
		// 4.아이디 저장유무에 따라 쿠키 저장
		// --------------------------------------------------
		CustomCompLoginDTO loginDto = customCompService.searchLogin(loginId, encryptPwd);
		if (null == loginDto) {
			return "custom/comp/customCompLogin";
		}
		
		//로그인 아이디,비밀번호로 회원조회가 됬을 경우, HttpSession에 조회된 회원정보 설정
		session.setAttribute("sLoginId", loginDto.getLogin_id());
		session.setAttribute("sGradeCode", loginDto.getCustom_grade());//고객등급
		session.setAttribute("sGradeName", loginDto.getGrade_name());//고객등급명
		session.setAttribute("sCustomId", loginDto.getCustom_id());//고객ID -- SEQ로 바꾸자
		session.setAttribute("sCustomName", loginDto.getCustom_name());//고객명
		session.setAttribute("sLoginDate", loginDto.getLogin_date());//로그인날짜
		
		// --------------------------------------------------
		// DB 저장 : 오늘방문횟수, 전체방문횟수, 포인터 100씩 증가
		// --------------------------------------------------
		//최종방문일과 오늘날짜 비교해서 다른 경우, 오늘방문횟수(todayCnt)값을 0으로 초기화
    String todayYmdhms = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    String todayYmd = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		if (null == loginDto.getLogin_date() || ! loginDto.getLogin_date().substring(0, 10).equals(todayYmd)) {
			customCompService.updateTodayCnt(loginDto.getLogin_id(), loginDto.getCustom_id());//DB저장(오늘방문횟수 '0', 로그인날짜 default now())
			loginDto.setToday_cnt(0);
			loginDto.setLogin_date(todayYmdhms);
		}
		//1.오늘방문횟수, 전체방문횟수 1씩 증가 
		customCompService.updateVisitCntAndTodayCnt(loginDto.getLogin_id(), loginDto.getCustom_id());//DB 방문횟수 증가
		loginDto.setToday_cnt(loginDto.getToday_cnt() + 1);
		loginDto.setVisit_cnt(loginDto.getVisit_cnt() + 1);
		if (10 >= loginDto.getToday_cnt()) {
			//2.포인터 100씩 증가(방문시마다 100포인트씩 증가<DB저장>, 1일 10회 이하)
			customCompService.updatePoint(loginDto.getLogin_id(), loginDto.getCustom_id());//DB 포인트 100포인트 증가
			loginDto.setPoint(loginDto.getPoint() + 100);
		}
		// --------------------------------------------------
		// 세션 저장(Mypage 회원전용방 출력용) : 오늘방문횟수, 전체방문횟수, 포인트
		// --------------------------------------------------
		session.setAttribute("sTodayVCnt", loginDto.getToday_cnt());
		session.setAttribute("sVCnt", loginDto.getVisit_cnt());
		session.setAttribute("sPoint", loginDto.getPoint());
		
		//idSave체크시 : 쿠키에 아이디(id)를 저장 checkbox checked 클릭 여부 - on/null
		//id저장
		if (idSave.equals("on")) {
			Cookie cookie = new Cookie("cLoginId", loginId);
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
		
		return "redirect:/msgCustomComp/LoginOk";
	}
	
	//회원전용방
	@RequestMapping(value="/customCompMain", method=RequestMethod.GET)
	public String customCompMainGet(HttpSession session, Model model) {
		String sLoginId = (String)session.getAttribute("sLoginId");
		String sGradeCode = (String)session.getAttribute("sGradeCode");
		
		if ((null == sLoginId || 0 == sLoginId.trim().length()) 
				&& (null == sGradeCode || 0 == sGradeCode.trim().length())) {
			//비회원 화면
			return "redirect:/msgCustomComp/LoginNo";
		}
		
		return "custom/comp/customCompMain";
	}
	
	//회원탈퇴(기업고객로그인테이블) - 30일 회원정보유지, 회원로그인정보 임시삭제(delete_date=탈퇴날짜(회원탈퇴))
	@RequestMapping(value="/customCompDeletePract", method=RequestMethod.GET)
	public String customCompDeletePractGet(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sLoginId = (String) session.getAttribute("sLoginId");
		int sCustomId = (int) session.getAttribute("sCustomId");
		
		//회원탈퇴 - 1달간은 회원정보유지, deleteDate와 logoutDate를 now()로 수정
		customCompService.updateCustomCompLoginUserDel(sLoginId, sCustomId);
		
//		if (1 == res) {
			return "redirect:/msgCustomComp/DeletePractOk";
//		} else {
//			return "redirect:/msgCustomComp/DeletePractNo";
//		}
	}

	//로그아웃
	@RequestMapping(value="/customCompLogout", method=RequestMethod.GET)
	public String customCompLogoutGet(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sLoginId = (String)session.getAttribute("sLoginId");
		int sCustomId = (int) session.getAttribute("sCustomId");
		
		customCompService.updateLogout(sLoginId, sCustomId);//DB 로그아웃정보 수정
//		if (1 == res) {
			session.invalidate();//세션삭제
			return "redirect:/msgCustomComp/LogoutOk";
//		} else {
//			return "redirect:/msgCustomComp/LogoutNo";
//		}
	}

	//회원가입화면 이동
	@RequestMapping(value="/customCompEntry", method=RequestMethod.GET)
	public String customCompEntryGet() {
//		//기업고객고분코드 목록조회 
//		CustomKindDAO dao = new CustomKindDAO();
//		List<CustomKindVO> customKind = dao.searchCustomCompCustomKindList();
//		request.setAttribute("customKind", customKind);
		return "custom/comp/customCompEntry";
	}

	//회원가입
	@RequestMapping(value="/customCompEntry", method=RequestMethod.POST)
	public String customCompEntryPost(Model model, @Validated CustomCompEntryUpdateFormVO entryVo, BindingResult bindRes) {
		
		if (bindRes.hasErrors()) {
			HashMap errMsgMap = new HashMap();
			initErrMsgList(errMsgMap);

			int i=0;
			List<ObjectError> errors = bindRes.getAllErrors();
			for (ObjectError fe : errors) {
				i++;
				System.out.println("code : " + fe.getCode());
				System.out.println("defaultMessage : " + fe.getDefaultMessage());
			}			
			System.out.println(" ObjectErrors : " + i);

			i=0;
			List<FieldError> fieldErrors = bindRes.getFieldErrors();
			for (FieldError fe : fieldErrors) {
				i++;
				System.out.println("fielderrors : " + bindRes.hasFieldErrors(fe.getField()));
				System.out.println("field : " + fe.getField());
				System.out.println("Message : " + fe.getDefaultMessage());
//				errMsgMap.put(fe.getField(), fe.getDefaultMessage());
			}
			System.out.println(" FieldErrors : " + i);
			
			model.addAttribute("errMsgMap", errMsgMap);
			return "redirect:/customComp/customCompEntry";
		}
		
		CustomCompDTO compDto = new CustomCompDTO(); 
		CustomCompLoginDTO loginDto = new CustomCompLoginDTO();
		
		//기업고객아이디 발급
		//CUSTOM_ID 구성 : 3자리(100~999) 'CUSTOM_KIND_CD' + 5자리 '순차발행' (00001~99999))
		//CUSTOM_KIND_CD '1', '2'의 경우는 '100'으로 설정 
		int customKindCode = entryVo.getCustomKindCode();
		int customId = customCompService.obtainCustomId(customKindCode);

		//기업고객 회원정보 VO 설정
		compDto.setCustom_id(customId);
		compDto.setCustom_nm(entryVo.getCustomName());
		compDto.setCustom_nm_short(entryVo.getCustomNameShort());
		compDto.setCustom_kind_cd(customKindCode);
		compDto.setEstbl_date(entryVo.getEstblDate());
		compDto.setCompany_no(entryVo.getCompanyNo());
		compDto.setOffice(entryVo.getOffice());
		compDto.setTxt_office(entryVo.getTxtOffice());
		compDto.setTel_no(entryVo.getTelNo());
		compDto.setHp_no(entryVo.getHpNo());
		compDto.setEmail(entryVo.getEmail());
		compDto.setPost_code(entryVo.getPostcode());
		compDto.setRoad_addr(entryVo.getRoadAddress());
		compDto.setExtra_addr(entryVo.getExtraAddress());
		compDto.setDetail_addr(entryVo.getDetailAddress());
		compDto.setMemo(entryVo.getMemo());
		compDto.setCustom_img_file_name(entryVo.getPhoto());
		
		//기업고객 로그인 VO 설정
		loginDto.setCustom_id(customId);
		loginDto.setLogin_id(entryVo.getLoginId());
		loginDto.setEncrypt_pwd(entryVo.getEncryptPwd());
		
		//기업고객 회원정보 DB 등록, 기업고객 로그인 DB 등록 - mybatis transaction 포함
		customCompService.insertCustomCompAndCustomCompLogin(compDto, loginDto);
		
//		if (1 == resLogin && 1 == resComp) {
				return "redirect:/msgCustomComp/EntryOk";
//		} else {
//				return "redirect:/msgCustomComp/EntryNo";
//		}		
	}
	
	private void initErrMsgList(HashMap errMsgMap) {
		errMsgMap.put("customName", "");
		errMsgMap.put("customNameShort", "");
		errMsgMap.put("companyNo", "");
		errMsgMap.put("txtOffice", "");
		errMsgMap.put("tel2", "");
		errMsgMap.put("tel3", "");
		errMsgMap.put("hp2", "");
		errMsgMap.put("hp3", "");
		errMsgMap.put("email1", "");
		errMsgMap.put("txtEmail2", "");
		errMsgMap.put("detailAddress", "");
		errMsgMap.put("memo", "");
		errMsgMap.put("customImgFileName", "");
	}

	//error style class 적용
/*  	function regexStyleCheck() {
		let message = '';
 		
 		//회원아이디 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		if ( '' == message ) {
			$("#loginId").addClass("is-valid");
			$("#loginIdInvalid").addClass("is-valid");
 			$("#loginIdInvalid").text('');
		} else {
			$("#loginId").addClass("is-invalid");
			$("#loginIdInvalid").addClass("is-invalid");
			$("#loginIdInvalid").text(message);
		}		
		//비밀번호 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		if ( '' == message ) {
			$("#loginPwd").addClass("is-invalid");
			$("#loginPwdInvalid").addClass("is-invalid");
			$("#loginPwdInvalid").text(message);
 			$("#loginPwd").focus();
		} else {
			$("#loginPwd").addClass("is-valid");
			$("#loginPwdInvalid").addClass("is-valid");
 			$("#loginPwdInvalid").text('');
		}
		//기업명 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		if ( '' == message ) {
			$("#customName").addClass("is-invalid");
			$("#customNameInvalid").addClass("is-invalid");
			$("#customNameInvalid").text(message);
			$("#customName").focus();
		} else {
			$("#customName").addClass("is-valid");
			$("#customNameInvalid").addClass("is-valid");
			$("#customNameInvalid").text('');
		}
		//기업명(단축명칭) 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		if ( '' == message ) {
			$("#customNameShort").addClass("is-invalid");
			$("#customNameShortInvalid").addClass("is-invalid");
			$("#customNameShortInvalid").text(message);
			$("#customNameShort").focus();
		} else {
			$("#customNameShort").addClass("is-valid");
			$("#customNameShortInvalid").addClass("is-valid");
			$("#customNameShortInvalid").text('');
		}
		//사업자등록번호 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		if ( '' == message ) {
			$("#companyNo").addClass("is-invalid");
			$("#companyNoInvalid").addClass("is-invalid");
			$("#companyNoInvalid").text(message);
			$("#companyNo").focus();
		} else {
			$("#companyNo").addClass("is-valid");
			$("#companyNoInvalid").addClass("is-valid");
			$("#companyNoInvalid").text('');
		}
		//사무실명 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		if ( '' == message ) {
			$("#txtOffice").addClass("is-invalid");
			$("#txtOfficeInvalid").addClass("is-invalid");
			$("#txtOfficeInvalid").text(message);
			$("#txtOffice").focus();
		} else {
			$("#txtOffice").addClass("is-valid");
			$("#txtOfficeInvalid").addClass("is-valid");
			$("#txtOfficeInvalid").text('');
		}
		//우편번호 공란 체크
 		message = '${(String)messageMap.get("id........................................")}';
		if ( '' == $('#addressGroup input[name="postcode"]').val()
			&& '' != $('#addressGroup input[name="detailAddress"]').val() ) {
			$('#addressGroup input[name="detailAddress"]').addClass("is-invalid");
			$("#detailAddressInvalid").addClass("is-invalid");
			$("#detailAddressInvalid").text("주소는 우편번호찾기로 검색 후 입력하세요");
			$('#addressGroup input[name="detailAddress"]').focus();
		} else {
			$('#addressGroup input[name="detailAddress"]').addClass("is-valid");
			$("#detailAddressInvalid").addClass("is-valid");
			$("#detailAddressInvalid").text('');
		}
		//상세주소 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
 		if ( '' != $('#addressGroup input[name="postcode"]').val() 
 			&& '' != $('#addressGroup input[name="detailAddress"]').val() ) {
			if ( ! $('#addressGroup input[name="detailAddress"]').val().(message) ) {
				$('#addressGroup input[name="detailAddress"]').addClass("is-invalid");
				$("#detailAddressInvalid").addClass("is-invalid");
				$("#detailAddressInvalid").text(message);
				$('#addressGroup input[name="detailAddress"]').focus();
			} else {
				$('#addressGroup input[name="detailAddress"]').addClass("is-valid");
				$("#detailAddressInvalid").addClass("is-valid");
				$("#detailAddressInvalid").text('');
			}
		}
		//이메일 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		if ( '' == message ) {
			$("#email1").addClass("is-invalid");
			$("#email1Invalid").addClass("is-invalid");
			$("#email1Invalid").text(message);
			$("#email1").focus();
		} else {
			$("#email1").addClass("is-valid");
			$("#email1Invalid").addClass("is-valid");
			$("#email1Invalid").text('');
		}
		//이메일 도메인 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		let options = entryForm.email2.options;
	  	if(options[0].selected == true) {//도메인 셀렉트박스에서 '-직접입력-' 선택의 경우
	  		if (entryForm.txtEmail2.value == '' ) {//도메인 직접입력 텍스트박스가 공란의 경우
					$("#txtEmail2").addClass("is-invalid");
					$("#txtEmail2Invalid").addClass("is-invalid");
					$("#txtEmail2Invalid").text(message);
					$("#txtEmail2").focus();
	  		} else {//도메인 직접입력 텍스트박스에 입력한 경우
					if ( ! $("#txtEmail2").val().(message) ) {//도메인값 REGEX 유효성 체크
						$("#txtEmail2").addClass("is-invalid");
						$("#txtEmail2Invalid").addClass("is-invalid");
						$("#txtEmail2Invalid").text(message);
						$("#txtEmail2").focus();
					} else {
						$("#txtEmail2").addClass("is-valid");
						$("#txtEmail2Invalid").addClass("is-valid");
						$("#txtEmail2Invalid").text('');
					}
	  		}
	  	}	else {//도메인 셀렉트박스에서 선택한 경우
				$("#txtEmail2").addClass("is-valid");
				$("#txtEmail2Invalid").addClass("is-valid");
				$("#txtEmail2Invalid").text('');
	  	}
		//전화번호 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		if ('' != $("#tel3").val()) {
			if ( ! $("#tel3").val().(message) ) {
				$("#tel3").addClass("is-invalid");
				$("#tel3Invalid").addClass("is-invalid");
				$("#tel3Invalid").text(message);
				$("#tel3").focus();
			} else {
 				$("#tel3").addClass("is-valid");
				$("#tel3Invalid").addClass("is-valid");
				$("#tel3Invalid").text('');
			}
		} 
		//전화번호 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		if ('' != $("#tel2").val()) {
			if ( ! $("#tel2").val().(message) ) {
				$("#tel2").addClass("is-invalid");
				$("#tel2Invalid").addClass("is-invalid");
				$("#tel2Invalid").text(message);
				$("#tel2").focus();
			} else {
				$("#tel2").addClass("is-valid");
				$("#tel2Invalid").addClass("is-valid");
				$("#tel2Invalid").text('');
			}
		}
		//휴대폰번호 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		if ('' != $("#hp3").val()) {
			if ( ! $("#hp3").val().(message) ) {
				$("#hp3").addClass("is-invalid");
				$("#hp3Invalid").addClass("is-invalid");
				$("#hp3Invalid").text(message);
				$("#hp3").focus();
			} else {
 				$("#hp3").addClass("is-valid");
				$("#hp3Invalid").addClass("is-valid");
				$("#hp3Invalid").text('');
			}
		} 
		//휴대폰번호 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		if ('' != $("#hp2").val()) {
			if ( ! $("#hp2").val().(message) ) {
				$("#hp2").addClass("is-invalid");
				$("#hp2Invalid").addClass("is-invalid");
				$("#hp2Invalid").text(message);
				$("#hp2").focus();
			} else {
				$("#hp2").addClass("is-valid");
				$("#hp2Invalid").addClass("is-valid");
				$("#hp2Invalid").text('');
			}
		}		
		//메모 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		if ( '' == message ) {
			$("#memo").addClass("is-invalid");
			$("#memoInvalid").addClass("is-invalid");
			$("#memoInvalid").text(message);
			$("#memo").focus();
		} else {
			$("#memo").addClass("is-valid");
			$("#memoInvalid").addClass("is-valid");
			$("#memoInvalid").text('');
		}
 		//회원사진명 정규식 에러 style class 적용
 		message = '${(String)messageMap.get("id........................................")}';
		if ( '' == message ) {
			$("#customImgFileName").addClass("is-invalid");
			$("#customImgFileNameInvalid").addClass("is-invalid");
			$("#customImgFileNameInvalid").text(message);
			$("#customImgFileName").focus();
		} else {
			$("#customImgFileName").addClass("is-valid");
			$("#customImgFileNameInvalid").addClass("is-valid");
			$("#customImgFileNameInvalid").text('');
		}
	} */

	//로그인ID중복체크화면 이동
	@RequestMapping(value="/customCompLoginIdCheck", method=RequestMethod.GET)
	public String customCompLoginIdCheckGet() {
		return "custom/comp/customCompLoginIdCheck";
	}
	
	//로그인ID중복체크
	@RequestMapping(value="/customCompLoginIdCheck", method=RequestMethod.POST)
	public String customCompLoginIdCheckPost(
		@RequestParam(name="loginId", defaultValue="", required=true) String loginId,
		Model model) {
		
		model.addAttribute("loginId", loginId);
		//isExist = true 아이디 중복
		if (customCompService.loginIdCheck(loginId)) {
			model.addAttribute("existLoginIdYN", "Y");
		} else {
			model.addAttribute("existLoginIdYN", "N");
		}
		return "custom/comp/customCompLoginIdCheck";
	}
	
	//사업자등록번호중복체크화면 이동
	@RequestMapping(value="/customCompCompanyNoCheck", method=RequestMethod.GET)
	public String customCompCompanyNoCheckGet() {
		return "custom/comp/customCompCompanyNoCheck";
	}
	
	//사업자등록번호중복체크
	@RequestMapping(value="/customCompCompanyNoCheck", method=RequestMethod.POST)
	public String customCompCompanyNoCheckPost(
		@RequestParam(name="companyNo", defaultValue="", required=true) String companyNo,
		Model model) {
		
		model.addAttribute("companyNo", companyNo);
		//isExist = true 아이디 중복
		if (customCompService.companyNoCheck(companyNo)) {
			model.addAttribute("existCompanyNoYN", "Y");
		} else {
			model.addAttribute("existCompanyNoYN", "N");
		}
		return "custom/comp/customCompCompanyNoCheck";
	}
	
	//이메일중복체크화면 이동
	@RequestMapping(value="/customCompEmailCheck", method=RequestMethod.GET)
	public String customCompEmailCheckGet() {
		return "custom/comp/customCompEmailCheck";
	}
	
	//이메일중복체크
	@RequestMapping(value="/customCompEmailCheck", method=RequestMethod.POST)
	public String customCompEmailCheckPost(
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
		if (customCompService.emailCheck(email)) {
			model.addAttribute("existEmailYN", "Y");
		} else {
			model.addAttribute("existEmailYN", "N");
		}
		return "custom/comp/customCompEmailCheck";
	}
	
	//회원정보수정화면 이동
	@RequestMapping(value="/customCompUpdate", method=RequestMethod.GET)
	public String customCompUpdateGet() {
		return "custom/comp/customCompUpdate";
	}
	
	//회원정보수정
	@RequestMapping(value="/customCompUpdate", method=RequestMethod.POST)
	public String customCompUpdatePost() {
		return "custom/comp/customCompUpdate";
	}
}