package com.spring.javagreenS_khv.service;

import com.spring.javagreenS_khv.vo.CustomPersonLoginVO;
import com.spring.javagreenS_khv.vo.CustomPersonVO;

public interface CustomPersonService {

	public CustomPersonLoginVO searchLogin(String id, String pwd);
	
	public void updateTodayCnt(String id, int customId);

	public void updateVisitCntAndTodayCnt(String id, int customId);

	public void updatePoint(String id, int customId);

	public void updateCustomPersonLoginUserDel(String id, int customId);

	public void updateLogout(String id, int customId);

	public boolean loginIdCheck(String id);

	public boolean emailCheck(String email);

	public boolean juminNoCheck(String juminNo);

	public int obtainCustomId(int customKindCode);

	public void insertCustomCompAndCustomCompLogin(CustomPersonVO personVo, CustomPersonLoginVO loginVo);
	
}