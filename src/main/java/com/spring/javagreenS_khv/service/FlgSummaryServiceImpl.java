package com.spring.javagreenS_khv.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import com.spring.javagreenS_khv.dao.FlgSummaryDAO;
import com.spring.javagreenS_khv.dto.FlgSummaryDTO;
import com.spring.javagreenS_khv.vo.FlagVO;
import com.spring.javagreenS_khv.vo.FlgSummaryVO;

@Service
public class FlgSummaryServiceImpl implements FlgSummaryService {
	@Autowired
	public FlgSummaryDAO flgSummaryDao;
	
	@Override
	public FlgSummaryVO searchFlg(String flgCd, String menuCd, String subMenuCd) {
		FlgSummaryDTO flgSummaryDto = flgSummaryDao.searchFlg(flgCd, menuCd, subMenuCd);
		FlgSummaryVO flgSummaryVo = new FlgSummaryVO();
		String flgNm = flgSummaryDto.getFlg_nm();
		String[] flgNmRec = flgNm.split("|");
		String[] flgNmColumn = new String[flgNmRec.length];
		List<FlagVO> flagVos = new ArrayList<>();
		FlagVO flagVo = null;
		for (String flgNmRow : flgNmRec) {
			flgNmColumn = flgNmRow.split(":");
			flagVo = new FlagVO();
			flagVo.setFlgCd(flgNmColumn[0]);
			flagVo.setFlgNm(flgNmColumn[1]);
			flagVos.add(flagVo);
		}
		flgSummaryVo.setFlagVos(flagVos);
		flgSummaryVo.setDelFlag(flgSummaryDto.getDel_flag());
		return flgSummaryVo;
	}
}