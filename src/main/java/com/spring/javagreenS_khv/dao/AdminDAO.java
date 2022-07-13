package com.spring.javagreenS_khv.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javagreenS_khv.dto.CustomCompDeleteDTO;
import com.spring.javagreenS_khv.dto.CustomCompLoginDTO;
import com.spring.javagreenS_khv.dto.CustomPersonDeleteDTO;
import com.spring.javagreenS_khv.dto.CustomPersonLoginDTO;
import com.spring.javagreenS_khv.vo.AdminLoginVO;

public interface AdminDAO {
	public AdminLoginVO searchAdminLogin(@Param("loginId") String loginId, @Param("encryptPwd") String encryptPwd);

	public void updateLogout(@Param("loginId") String loginId);

	public List<CustomCompDeleteDTO> searchCustomCompDeleteList(@Param("overFlg") String overFlg);

	public List<CustomPersonDeleteDTO> searchCustomPersonDeleteList(@Param("overFlg") String overFlg);

	public void deleteCustomCompDelete(@Param("customId") String customId);

	public void deleteCustomPersonDelete(@Param("customId") String customId);

	public List<CustomCompLoginDTO> searchRecentlyEntryCustomCompList();

	public List<CustomCompLoginDTO> searchRecentlyLoginCustomCompList();

	public List<CustomCompLoginDTO> searchPracDeleteCustomCompList();

	public List<CustomPersonLoginDTO> searchRecentlyEntryCustomPersonList();

	public List<CustomPersonLoginDTO> searchRecentlyLoginCustomPersonList();

	public List<CustomPersonLoginDTO> searchPracDeleteCustomPersonList();
}