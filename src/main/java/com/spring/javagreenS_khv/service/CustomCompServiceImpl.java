package com.spring.javagreenS_khv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javagreenS_khv.dao.CustomCompDAO;
import com.spring.javagreenS_khv.vo.CustomCompLoginVO;
import com.spring.javagreenS_khv.vo.CustomCompVO;

@Service
public class CustomCompServiceImpl implements CustomCompService {

	@Autowired
	public CustomCompDAO customCompDao;
	
	@Override
	public CustomCompLoginVO searchLogin(String id, String pwd) {
		return customCompDao.searchLogin(id, pwd);
	}

	@Override
	public void updateTodayCnt(String id, int customId) {
		customCompDao.updateTodayCnt(id, customId);
	}

	@Override
	public void updateVisitCntAndTodayCnt(String id, int customId) {
		customCompDao.updateVisitCntAndTodayCnt(id, customId);
	}

	@Override
	public void updatePoint(String id, int customId) {
		customCompDao.updatePoint(id, customId);
	}

	@Override
	public void updateCustomCompLoginUserDel(String id, int customId) {
		customCompDao.updateCustomCompLoginUserDel(id, customId);
	}

	@Override
	public void updateLogout(String id, int customId) {
		customCompDao.updateLogout(id, customId);
	}

	@Override
	public boolean loginIdCheck(String id) {
		return customCompDao.loginIdCheck(id);
	}

	@Override
	public boolean companyNoCheck(String companyNo) {
		return customCompDao.companyNoCheck(companyNo);
	}

	@Override
	public boolean emailCheck(String email) {
		return customCompDao.emailCheck(email);
	}

	@Override
	public void insertCustomCompAndCustomCompLogin(CustomCompVO compVo, CustomCompLoginVO loginVo) {
		customCompDao.insertCustomCompAndCustomCompLogin(compVo, loginVo);		
	}

	@Override
	public int obtainCustomId(int customKindCode) {
		return customCompDao.obtainCustomId(customKindCode);
	}
}