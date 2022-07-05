package com.spring.javagreenS_khv.service;

import com.spring.javagreenS_khv.vo.CustomCompLoginVO;
import com.spring.javagreenS_khv.vo.CustomCompVO;

public interface CustomCompService {

	public CustomCompLoginVO searchLogin(String id, String pwd);

	public void updateTodayCnt(String id, int customId);

	public void updateVisitCntAndTodayCnt(String id, int customId);

	public void updatePoint(String id, int customId);

	public void updateCustomCompLoginUserDel(String id, int customId);

	public void updateLogout(String id, int customId);

	public boolean loginIdCheck(String id);

	public boolean companyNoCheck(String companyNo);

	public boolean emailCheck(String email);

	public void insertCustomCompAndCustomCompLogin(CustomCompVO compVo, CustomCompLoginVO loginVo);

	public int obtainCustomId(int customKindCode);
	
}