package com.spring.javagreenS_khv.vo;

import lombok.Data;

public @Data class CustomPersonVO {
	private int seq;
	private int custom_id;
	private String custom_nm;
	private int custom_kind_group_code;
	private String kind_group_name;
	private int custom_kind_cd;
	private String kind_name;
	private String custom_grade;
	private String grade_name;
	private String job;
	private String txt_job;
	private String hobby;
	private String birth_date;
	private String jumin_no;
	private String gender;
	private String tel_no;
	private String hp_no;
	private String email;
	private String post_code;
	private String road_addr;
	private String extra_addr;
	private String detail_addr;
	private String memo;
	private String create_date;
	private String create_user;
	private String update_date;
	private String update_user;
	private String delete_date;
	private String delete_user;
	private int over_days_user_del; //회원삭제신청 경과일(30일 경과시 회원탈퇴처리)
}