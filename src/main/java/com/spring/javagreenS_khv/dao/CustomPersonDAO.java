package com.spring.javagreenS_khv.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.javagreenS_khv.vo.CustomPersonLoginVO;
import com.spring.javagreenS_khv.vo.CustomPersonVO;

public interface CustomPersonDAO {

	public CustomPersonLoginVO searchLogin(@Param("id") String id, @Param("pwd") String pwd);

	public void updateTodayCnt(@Param("id") String id, @Param("customId") int customId);

	public void updateVisitCntAndTodayCnt(@Param("id") String id, @Param("customId") int customId);

	public void updatePoint(@Param("id") String id, @Param("customId") int customId);

	public void updateCustomPersonLoginUserDel(@Param("id") String id, @Param("customId") int customId);

	public void updateLogout(@Param("id") String id, @Param("customId") int customId);

	public boolean loginIdCheck(@Param("id") String id);

	public boolean emailCheck(@Param("email") String email);

	public boolean juminNoCheck(@Param("juminNo") String juminNo);

	public int obtainCustomId(@Param("customKindCode") int customKindCode);

	public void insertCustomCompAndCustomCompLogin(@Param("personVo") CustomPersonVO personVo, @Param("loginVo") CustomPersonLoginVO loginVo);
	
}