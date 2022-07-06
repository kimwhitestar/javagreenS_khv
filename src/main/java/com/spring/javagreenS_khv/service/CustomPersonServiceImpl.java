package com.spring.javagreenS_khv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.javagreenS_khv.dao.CustomPersonDAO;
import com.spring.javagreenS_khv.vo.CustomPersonLoginVO;
import com.spring.javagreenS_khv.vo.CustomPersonVO;

@Service
public class CustomPersonServiceImpl implements CustomPersonService {

	@Autowired
	public CustomPersonDAO customPersonDao;

	@Override
	public CustomPersonLoginVO searchLogin(String id, String pwd) {
		return customPersonDao.searchLogin(id, pwd);
	}

	@Override
	public void updateTodayCnt(String id, int customId) {
		customPersonDao.updateTodayCnt(id, customId);
	}

	@Override
	public void updateVisitCntAndTodayCnt(String id, int customId) {
		customPersonDao.updateVisitCntAndTodayCnt(id, customId);
	}

	@Override
	public void updatePoint(String id, int customId) {
		customPersonDao.updatePoint(id, customId);
	}

	@Override
	public void updateCustomPersonLoginUserDel(String id, int customId) {
		customPersonDao.updateCustomPersonLoginUserDel(id, customId);
	}

	@Override
	public void updateLogout(String id, int customId) {
		customPersonDao.updateLogout(id, customId);
	}

	@Override
	public boolean loginIdCheck(String id) {
		return customPersonDao.loginIdCheck(id);
	}

	@Override
	public boolean emailCheck(String email) {
		return customPersonDao.emailCheck(email);
	}

	@Override
	public boolean juminNoCheck(String juminNo) {
		return customPersonDao.juminNoCheck(juminNo);
	}

	@Override
	public int obtainCustomId(int customKindCode) {
		return customPersonDao.obtainCustomId(customKindCode);
	}

	@Transactional
	@Override
	public void insertCustomPersonAndCustomPersonLogin(CustomPersonVO personVo, CustomPersonLoginVO loginVo) {
		customPersonDao.insertCustomPersonAndCustomPersonLogin(personVo, loginVo);
	}
}