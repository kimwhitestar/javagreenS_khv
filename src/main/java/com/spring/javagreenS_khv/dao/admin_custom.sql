DESC CUSTOM_COMP_LOGIN;
DESC CUSTOM_COMP;
-- 기업회원탈퇴목록
SELECT DEL_PRAC.LOGIN_ID, 
	DEL_PRAC.CUSTOM_ID CUSTOM_ID,
	DEL_PRAC.CUSTOM_NM CUSTOM_NM, 
	DEL_PRAC.COMPANY_NO COMPANY_NO, 
	DEL_PRAC.OVER_DAYS_USER_DEL OVER_DAYS_USER_DEL,
	IFNULL(DEL_OVER.OVER_FLG, 'PRAC') OVER_FLG, 
	DEL_PRAC.DELETE_DATE DELETE_DATE, 
	DEL_PRAC.DELETE_USER DELETE_USER 
FROM 
	(SELECT A.LOGIN_ID, A.CUSTOM_ID, B.CUSTOM_NM, B.COMPANY_NO, TIMESTAMPDIFF(DAY, A.DELETE_DATE, NOW()) AS OVER_DAYS_USER_DEL, A.DELETE_DATE, A.DELETE_USER   
	FROM CUSTOM_COMP_LOGIN A, CUSTOM_COMP B
	WHERE A.DELETE_DATE IS NOT NULL -- 탈퇴회원(임시탈퇴자, 로그인삭제일) 
	AND A.CUSTOM_ID = B.CUSTOM_ID
	) AS DEL_PRAC 
LEFT OUTER JOIN 
	(SELECT CUSTOM_ID, 'OVER' AS OVER_FLG 
	FROM CUSTOM_COMP_LOGIN 
	WHERE TIMESTAMPDIFF(DAY, DELETE_DATE, NOW()) >= 30 
	) AS DEL_OVER 
ON DEL_PRAC.CUSTOM_ID = DEL_OVER.CUSTOM_ID 
ORDER BY OVER_FLG ASC, OVER_DAYS_USER_DEL DESC



		
DESC CUSTOM_PERSON_LOGIN;
DESC CUSTOM_PERSON;
		
		SELECT DEL_PRAC.LOGIN_ID, 
			DEL_PRAC.CUSTOM_ID CUSTOM_ID,
			DEL_PRAC.CUSTOM_NM CUSTOM_NM, 
			DEL_PRAC.BIRTH_DATE BIRTH_DATE, 
			DEL_PRAC.OVER_DAYS_USER_DEL OVER_DAYS_USER_DEL,
			IFNULL(DEL_OVER.OVER_FLG, 'PRAC') OVER_FLG, 
			DEL_PRAC.DELETE_DATE DELETE_DATE, 
			DEL_PRAC.DELETE_USER DELETE_USER 
		FROM 
			(SELECT A.LOGIN_ID, A.CUSTOM_ID, B.CUSTOM_NM, B.BIRTH_DATE, TIMESTAMPDIFF(DAY, A.DELETE_DATE, NOW()) AS OVER_DAYS_USER_DEL, A.DELETE_DATE, A.DELETE_USER   
			FROM CUSTOM_PERSON_LOGIN A, CUSTOM_PERSON B
			WHERE A.DELETE_DATE IS NOT NULL -- 탈퇴회원(임시탈퇴자, 로그인삭제일) 
			AND A.CUSTOM_ID = B.CUSTOM_ID
			) AS DEL_PRAC 
		LEFT OUTER JOIN 
			(SELECT CUSTOM_ID, 'OVER' AS OVER_FLG 
			FROM CUSTOM_PERSON_LOGIN 
			WHERE TIMESTAMPDIFF(DAY, DELETE_DATE, NOW()) >= 30 
			) AS DEL_OVER 
		ON DEL_PRAC.CUSTOM_ID = DEL_OVER.CUSTOM_ID 
		ORDER BY OVER_FLG ASC, OVER_DAYS_USER_DEL DESC
