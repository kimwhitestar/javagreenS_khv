package com.spring.javagreenS_khv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.javagreenS_khv.dao.AdminDAO;
import com.spring.javagreenS_khv.dto.CustomCompDeleteDTO;
import com.spring.javagreenS_khv.dto.CustomCompLoginDTO;
import com.spring.javagreenS_khv.dto.CustomPersonDeleteDTO;
import com.spring.javagreenS_khv.dto.CustomPersonLoginDTO;
import com.spring.javagreenS_khv.vo.AdminLoginVO;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	public AdminDAO adminDao;

	@Override
	public AdminLoginVO searchAdminLogin(String loginId, String encryptPwd) {
		return adminDao.searchAdminLogin(loginId, encryptPwd);
	}

	@Override
	public void updateLogout(String loginId) {
		adminDao.updateLogout(loginId);
	}

	@Override
	public List<CustomCompDeleteDTO> searchCustomCompDeleteList(String overFlg) {
		return adminDao.searchCustomCompDeleteList(overFlg);
	}

	@Override
	public List<CustomPersonDeleteDTO> searchCustomPersonDeleteList(String overFlg) {
		return adminDao.searchCustomPersonDeleteList(overFlg);
	}

	@Transactional
	@Override
	public void deleteCustomCompDelete(String customId) {
		adminDao.deleteCustomCompDelete(customId);
	}

	@Transactional
	@Override
	public void deleteCustomPersonDelete(String customId) {
		adminDao.deleteCustomPersonDelete(customId);
	}

	@Override
	public List<CustomCompLoginDTO> searchRecentlyEntryCustomCompList() {
		return adminDao.searchRecentlyEntryCustomCompList();
	}

	@Override
	public List<CustomCompLoginDTO> searchRecentlyLoginCustomCompList() {
		return adminDao.searchRecentlyLoginCustomCompList();
	}

	@Override
	public List<CustomCompLoginDTO> searchPracDeleteCustomCompList() {
		return adminDao.searchPracDeleteCustomCompList();
	}

	@Override
	public List<CustomPersonLoginDTO> searchRecentlyEntryCustomPersonList() {
		return adminDao.searchRecentlyEntryCustomPersonList();
	}

	@Override
	public List<CustomPersonLoginDTO> searchRecentlyLoginCustomPersonList() {
		return adminDao.searchRecentlyLoginCustomPersonList();
	}

	@Override
	public List<CustomPersonLoginDTO> searchPracDeleteCustomPersonList() {
		return adminDao.searchPracDeleteCustomPersonList();
	}
}