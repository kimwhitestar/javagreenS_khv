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
		String flgNm = flgSummaryDto.getFlg_nm();

		int startIdx = 0, endIdx = 0, cnt = 1;
		for (int i=0; i<flgNm.length(); i++) {
			endIdx = flgNm.indexOf("|", startIdx);//NONE:해당안됨|OVER:30일경과|PRAC:30일미경과
			if (-1 == endIdx) break;
			startIdx = endIdx;
			cnt ++;
		}
		startIdx = 0; 
		endIdx = 0;
		String[] flgNmRec = new String[cnt];//[NONE:해당안됨][OVER:30일경과][PRAC:30일미경과
		for (int i=0; i<flgNm.length(); i++) {
			endIdx = flgNm.indexOf("|", startIdx);//NONE:해당안됨|OVER:30일경과|PRAC:30일미경과
			if (1 > endIdx) break;//-1,0은 안잘림
			flgNmRec[i] = flgNm.substring(startIdx, endIdx);
			startIdx = endIdx;
		}
		List<FlagVO> flagVos = new ArrayList<>();
		FlagVO flagVo = null;
		startIdx = 0; 
		endIdx = 0;
		for (String flgNmRow : flgNmRec) {
			endIdx = flgNmRow.indexOf(":");//NONE:해당안됨|OVER:30일경과|PRAC:30일미경과
			if (1 > endIdx || endIdx == flgNmRow.length()-1) break;
			flagVo = new FlagVO();
			flagVo.setFlgCd(flgNmRow.substring(startIdx, endIdx));//key
			startIdx = endIdx;
			endIdx = flgNmRow.length();
			flagVo.setFlgNm(flgNmRow.substring(startIdx, endIdx));//value
			flagVos.add(flagVo);
		}
		FlgSummaryVO flgSummaryVo = new FlgSummaryVO();
		flgSummaryVo.setFlagVos(flagVos);
		flgSummaryVo.setDelFlag(flgSummaryDto.getDel_flag());
		return flgSummaryVo;
	}
}