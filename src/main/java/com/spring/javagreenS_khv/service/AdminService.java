package com.spring.javagreenS_khv.service;

import java.util.List;

import com.spring.javagreenS_khv.dto.CustomCompDeleteDTO;
import com.spring.javagreenS_khv.dto.CustomPersonDeleteDTO;
import com.spring.javagreenS_khv.vo.AdminLoginVO;

public interface AdminService {
	public AdminLoginVO searchAdminLogin(String loginId, String encryptPwd);

	public void updateLogout(String sLoginId);

	public List<CustomCompDeleteDTO> searchCustomCompDeleteList(String overFlg);

	public List<CustomPersonDeleteDTO> searchCustomPersonDeleteList(String overFlg);

	public void deleteCustomCompDelete(String customId);

	public void deleteCustomPersonDelete(String customId);

}