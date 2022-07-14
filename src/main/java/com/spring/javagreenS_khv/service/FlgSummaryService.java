package com.spring.javagreenS_khv.service;

import com.spring.javagreenS_khv.dto.FlgSummaryDTO;

public interface FlgSummaryService {
	public FlgSummaryDTO searchFlg(String flgCd, String menuCd, String subMenuCd);
}