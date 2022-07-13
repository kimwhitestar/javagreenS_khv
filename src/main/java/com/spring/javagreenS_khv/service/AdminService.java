package com.spring.javagreenS_khv.service;

import java.util.List;

import com.spring.javagreenS_khv.dto.CustomCompDeleteDTO;
import com.spring.javagreenS_khv.dto.CustomCompLoginDTO;
import com.spring.javagreenS_khv.dto.CustomPersonDeleteDTO;
import com.spring.javagreenS_khv.dto.CustomPersonLoginDTO;
import com.spring.javagreenS_khv.vo.AdminLoginVO;

public interface AdminService {
	public AdminLoginVO searchAdminLogin(String loginId, String encryptPwd);

	public void updateLogout(String sLoginId);

	public List<CustomCompDeleteDTO> searchCustomCompDeleteList(String overFlg);

	public List<CustomPersonDeleteDTO> searchCustomPersonDeleteList(String overFlg);

	public void deleteCustomCompDelete(String customId);

	public void deleteCustomPersonDelete(String customId);

	public List<CustomCompLoginDTO> searchRecentlyEntryCustomCompList();

	public List<CustomCompLoginDTO> searchRecentlyLoginCustomCompList();

	public List<CustomCompLoginDTO> searchPracDeleteCustomCompList();

	public List<CustomPersonLoginDTO> searchRecentlyEntryCustomPersonList();

	public List<CustomPersonLoginDTO> searchRecentlyLoginCustomPersonList();

	public List<CustomPersonLoginDTO> searchPracDeleteCustomPersonList();

}