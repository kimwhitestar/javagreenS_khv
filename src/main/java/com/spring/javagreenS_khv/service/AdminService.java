package com.spring.javagreenS_khv.service;

import com.spring.javagreenS_khv.vo.AdminLoginVO;

public interface AdminService {
	public AdminLoginVO searchAdminLogin(String loginId, String encryptPwd);

	public void updateLogout(String sLoginId);
}