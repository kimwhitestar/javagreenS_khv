package com.spring.javagreenS_khv.vo;

import lombok.Data;

public @Data class CustomCompEntryUpdateFormVO {
	//기업고객회원정보
	private int seq;
	private int customId;
	private String customName;
	private String customNameShort;
	private int customKindGroupCode;
	private String kindGroupName;
	private int customKindCode;
	private String customKindName;
	private char customGrade;
	private String gradeName;
	private String estblDate;
	private String companyNo;
	private String office;
	private String txtOffice;
	private String telNo;
	private String hpNo;
	private String email;
	private String postcode;
	private String roadAddress;
	private String extraAddress;
	private String detailAddress;
	private String memo;
	private String customImgFileName;
	private String photo;
	private int overDaysUserDel; //회원탈퇴신청 후 30일 경과시 회원삭제처리
	//기업고객회원로그인정보
	private String id;
	private String pwd;
	private String encryptPwd;
	private int point;
	private int visitCnt;
	private int todayCnt;
}