package com.spring.javagreenS_khv.vo;

import lombok.Data;

/*
 * 개인고객회원 대시보드, 통계 조회
 */
public @Data class CustomPersonSearchVO {
	private String loginId;
	private String loginPwd;
	private String encryptPwd;
	private int customId;
	private String customName;
	private String customKindCode;
	private String customKindName;
	private String customGrade;
	private String gradeName;
	private int point;
	private int visitCnt;
	private int todayCnt;
	private String loginDate;
	private String logoutDate;
	private String createDate;
	private String updateDate;
	private String updateUser;
	private String deleteDate;
	private String deleteUser;
}