package com.spring.javagreenS_khv.vo;

import lombok.Data;

public @Data class CustomPersonEntryUpdateFormVO {
	//개인고객회원정보
	private int seq;
	private int customId;
	private String customName;
	private int customKindGroupCode;
	private String kindGroupName;
	private String customKindCode;
	private String kindName;
	private char customGrade;
	private String gradeName;
	private String job;
	private String txtJob;
	private String hobby;
	private String checkedHobbies;
	private String birthDate;
	private String juminNo;
	private char gender;
	private String telNo;
	private String hpNo;
	private String email;
	private String postcode;
	private String roadAddress;
	private String extraAddress;
	private String detailAddress;
	private String memo;
	private String createDate;
	private String createUser;
	private String updateDate;
	private String updateUser;
	private String deleteDate;
	private String deleteUser;
	private int overDaysUserDel; //회원삭제신청 경과일(30일 경과시 회원탈퇴처리)
	//개인고객회원로그인정보
	private String loginId;
	private String loginPwd;
	private String encryptPwd;
	private int point;
	private int visitCnt;
	private int todayCnt;
}