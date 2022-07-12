package com.spring.javagreenS_khv.service;

import java.util.List;

import com.spring.javagreenS_khv.dto.CustomCompDeleteDTO;
import com.spring.javagreenS_khv.vo.AdminLoginVO;

public interface AdminService {
	public AdminLoginVO searchAdminLogin(String loginId, String encryptPwd);

	public void updateLogout(String sLoginId);

	public List<CustomCompDeleteDTO> searchCustomCompDeleteList();
}