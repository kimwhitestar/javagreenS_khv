package com.spring.javagreenS_khv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.javagreenS_khv.dao.CustomCompDAO;
import com.spring.javagreenS_khv.dto.CustomCompDTO;
import com.spring.javagreenS_khv.dto.CustomCompLoginDTO;
import com.spring.javagreenS_khv.vo.KakaoAddressVO;

@Service
public class CustomCompServiceImpl implements CustomCompService {
	
	@Autowired
	public CustomCompDAO customCompDao;
	
	@Override
	public KakaoAddressVO searchAddressName(String address) {
		return customCompDao.searchAddressName(address);
	}

	@Override
	public void insertAddressName(KakaoAddressVO vo) {
		customCompDao.insertAddressName(vo);
	}

	@Override
	public List<KakaoAddressVO> searchAddressNameList() {
		return customCompDao.searchAddressNameList();
	}

	@Override
	public void kakaoEx2Delete(String address) {
		customCompDao.kakaoEx2Delete(address);
	}

	@Override
	public CustomCompLoginDTO searchLogin(String loginId, String encryptPwd) {
		return customCompDao.searchLogin(loginId, encryptPwd);
	}

	@Override
	public void updateTodayCnt(String loginId, int customId) {
		customCompDao.updateTodayCnt(loginId, customId);
	}

	@Override
	public void updateVisitCntAndTodayCnt(String loginId, int customId) {
		customCompDao.updateVisitCntAndTodayCnt(loginId, customId);
	}

	@Override
	public void updatePoint(String loginId, int customId) {
		customCompDao.updatePoint(loginId, customId);
	}

	@Transactional
	@Override
	public void updateCustomCompLoginUserDel(String loginId, int customId) {
		customCompDao.updateCustomCompLoginUserDel(loginId, customId);
	}

	@Override
	public void updateLogout(String loginId, int customId) {
		customCompDao.updateLogout(loginId, customId);
	}

	@Override
	public boolean loginIdCheck(String loginId) {
		return customCompDao.loginIdCheck(loginId);
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
	public int obtainCustomId(int customKindCode) {
		return customCompDao.obtainCustomId(customKindCode);
	}

	@Transactional
	@Override
	public void insertCustomCompAndCustomCompLogin(CustomCompDTO compDto, CustomCompLoginDTO loginDto) {
		customCompDao.insertCustomCompAndCustomCompLogin(compDto, loginDto);
	}

	@Override
	public CustomCompDTO searchCustomComp(int customId) {
		return customCompDao.searchCustomComp(customId);
	}

	@Transactional
	@Override
	public void updateCustomComp(CustomCompDTO compDto) {
		customCompDao.updateCustomComp(compDto);
	}

}