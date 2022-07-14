package com.spring.javagreenS_khv.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.spring.javagreenS_khv.dao.FlgSummaryDAO;
import com.spring.javagreenS_khv.dto.FlgSummaryDTO;

public class FlgSummaryServiceImpl implements FlgSummaryService {
	@Autowired
	public FlgSummaryDAO flgSummaryDao;
	
	public FlgSummaryDTO searchFlg(String flgCd, String menuCd, String subMenuCd) {
		return flgSummaryDao.searchFlg(flgCd, menuCd, subMenuCd);
	}
}