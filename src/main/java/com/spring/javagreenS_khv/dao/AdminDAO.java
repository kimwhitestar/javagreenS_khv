package com.spring.javagreenS_khv.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.javagreenS_khv.vo.AdminLoginVO;

public interface AdminDAO {
	public AdminLoginVO searchAdminLogin(@Param("loginId") String loginId, @Param("encryptPwd") String encryptPwd);

	public void updateLogout(@Param("loginId") String loginId);
}