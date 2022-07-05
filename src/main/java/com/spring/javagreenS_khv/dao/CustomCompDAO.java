package com.spring.javagreenS_khv.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.javagreenS_khv.vo.CustomCompLoginVO;
import com.spring.javagreenS_khv.vo.CustomCompVO;

public interface CustomCompDAO {

	public CustomCompLoginVO searchLogin(@Param("id") String id, @Param("pwd") String pwd);

	public void updateTodayCnt(@Param("id") String id, @Param("customId") int customId);

	public void updateVisitCntAndTodayCnt(@Param("id") String id, @Param("customId") int customId);

	public void updatePoint(@Param("id") String id, @Param("customId") int customId);

	public void updateCustomCompLoginUserDel(@Param("id") String id, @Param("customId") int customId);

	public void updateLogout(@Param("id") String id, @Param("customId") int customId);

	public boolean loginIdCheck(@Param("id") String id);

	public boolean companyNoCheck(@Param("companyNo") String companyNo);

	public boolean emailCheck(@Param("email") String email);

	public void insertCustomCompAndCustomCompLogin(@Param("compVo") CustomCompVO compVo, @Param("loginVo") CustomCompLoginVO loginVo);

	public int obtainCustomId(@Param("customKindCode") int customKindCode);
	
}