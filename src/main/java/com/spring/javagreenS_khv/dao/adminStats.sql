show tables;

-- 기업회원탈퇴목록
SELECT /* CUSTOM_KIND */
	DEL_PRAC.CUSTOM_ID CUSTOM_ID,
	DEL_PRAC.OVER_DAYS_USER_DEL OVER_DAYS_USER_DEL,
	IFNULL(DEL_OVER.OVER_FLG, 'PRAC') OVER_FLG, 
	DEL_PRAC.DELETE_DATE DELETE_DATE, 
	DEL_PRAC.DELETE_USER DELETE_USER 
FROM 
	(SELECT A.CUSTOM_ID, TIMESTAMPDIFF(DAY, A.DELETE_DATE, NOW()) AS OVER_DAYS_USER_DEL, A.DELETE_DATE, A.DELETE_USER     
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
ORDER BY OVER_FLG ASC, OVER_DAYS_USER_DEL DESC;





CREATE TABLE CUSTOM_COMP_HISTORY (
	SEQ				INT(15)		AUTO_INCREMENT UNIQUE KEY,
	CUSTOM_ID 		INT(8) 		NOT NULL PRIMARY KEY,
	CUSTOM_KIND_CD	INT(3) 		NOT NULL COMMENT '1:기업고객|2:개인고객|101:유통|102:대형마트|103:중형마트|104:소형마트|105:편의점|201:공장|202:택배|203:우체국|301:컨테이너위탁업체|302:트랙카위탁업체|303:표준위탁업체|501:항공운송|502:선박운송|503:기차운송|505:표준차량운송|506:고속버스운송|507:개별차량운송|601:농업공급자|602:수산업공급자|603:축산업공급자',
	CUSTOM_KIND_NM	VARCHAR(10) NOT NULL, 
	CUSTOM_GRADE	VARCHAR(1) 	NOT NULL COMMENT 'A:우수기업고객|B:정규기업고객|C:일반기업고객|O:임시기업고객|P:임시개인고객|Q:일반개인고객|R:정규개인고객|S:우수개인고객', 
	GRADE_NAME		VARCHAR(10) NOT NULL,
	DELETE_OVER_FLG VARCHAR(4)	NULL DEFAULT 'NONE' COMMENT 'NONE:해당안됨|OVER:30일경과|PRAC:30일미경과', 
	CREATE_DATE 	TIMESTAMP 	NULL DEFAULT CURRENT_TIMESTAMP,
	CREATE_USER		VARCHAR(10) DEFAULT NULL,
	UPDATE_DATE 	TIMESTAMP 	NULL DEFAULT CURRENT_TIMESTAMP,
	UPDATE_USER		VARCHAR(10) DEFAULT NULL,
	DELETE_DATE		TIMESTAMP 	NULL DEFAULT CURRENT_TIMESTAMP,
	DELETE_USER		VARCHAR(10) DEFAULT NULL
);
DESC CUSTOM_COMP_HISTORY;
INSERT INTO CUSTOM_COMP_HISTORY VALUES ( DEFAULT,  10000001, 1, "기업고객", "O", "임시기업고객", DEFAULT, "2022-03-10 12:00:00",   10000001,  NULL,  NULL,  NULL,  NULL);
INSERT INTO CUSTOM_COMP_HISTORY VALUES ( DEFAULT,  10000002, 1, "기업고객", "O", "임시기업고객", DEFAULT, "2022-03-25 12:25:00",   10000002,  NULL,  NULL,  NULL,  NULL);
INSERT INTO CUSTOM_COMP_HISTORY VALUES ( DEFAULT,  10000003, 1, "기업고객", "O", "임시기업고객", DEFAULT, "2022-07-06 17:14:39",   10000003,  NULL,  NULL,  NULL,  NULL);
INSERT INTO CUSTOM_COMP_HISTORY VALUES ( DEFAULT,  10100001, 101, "유통", "O", "임시기업고객", DEFAULT, "2022-05-01 10:00:00",   10100001,  NULL,  NULL,  NULL,  NULL);
INSERT INTO CUSTOM_COMP_HISTORY VALUES ( DEFAULT,  10300001, 103, "중형마트", "O", "임시기업고객", DEFAULT, "2022-05-20 10:31:00",   10300001,  NULL,  NULL,  NULL,  NULL);
INSERT INTO CUSTOM_COMP_HISTORY VALUES ( DEFAULT,  20200001, 202, "택배", "O", "임시기업고객", "PRAC", "2022-07-06 11:33:18",   20200001,  NULL,  NULL,  NULL,  NULL);
INSERT INTO CUSTOM_COMP_HISTORY VALUES ( DEFAULT,  30200001, 302, "트랙카위탁업체", "O", "임시기업고객", DEFAULT, "2022-06-11 13:50:49",   30200001,  NULL,  NULL,  NULL,  NULL);
INSERT INTO CUSTOM_COMP_HISTORY VALUES ( DEFAULT,  30300001, 303, "표준위탁업체", "O", "임시기업고객", DEFAULT, "2022-06-07 23:26:08",   30300001,  NULL,  NULL,  NULL,  NULL);
INSERT INTO CUSTOM_COMP_HISTORY VALUES ( DEFAULT,  30300002, 303, "표준위탁업체", "O", "임시기업고객", "PRAC", "2022-07-06 13:36:47",   30300002,  NULL,  NULL,  NULL,  NULL);
SELECT * FROM CUSTOM_COMP_LOGIN WHERE DELETE_DATE IS NOT NULL;
SELECT * FROM CUSTOM_COMP_HISTORY;
delete from CUSTOM_COMP_HISTORY;





-- DROP TABLE CUSTOM_PERSON_HISTORY;
CREATE TABLE CUSTOM_PERSON_HISTORY( 
	SEQ				INT(15)		AUTO_INCREMENT UNIQUE KEY, 
	CUSTOM_ID 		INT(8) 		NOT NULL PRIMARY KEY, 
	CUSTOM_KIND_CD	INT(3) 		NOT NULL COMMENT '1:기업고객|2:개인고객|101:유통|102:대형마트|103:중형마트|104:소형마트|105:편의점|201:공장|202:택배|203:우체국|301:컨테이너위탁업체|302:트랙카위탁업체|303:표준위탁업체|501:항공운송|502:선박운송|503:기차운송|505:표준차량운송|506:고속버스운송|507:개별차량운송|601:농업공급자|602:수산업공급자|603:축산업공급자', 
	CUSTOM_KIND_NM	VARCHAR(10) NOT NULL, 
	CUSTOM_GRADE	VARCHAR(1) 	NOT NULL  COMMENT 'A:우수기업고객|B:정규기업고객|C:일반기업고객|O:임시기업고객|P:임시개인고객|Q:일반개인고객|R:정규개인고객|S:우수개인고객', 
	GRADE_NAME		VARCHAR(10) NOT NULL, 
	GENDER			VARCHAR(1)	NULL COMMENT 'M:남자|W:여자', 
	DELETE_OVER_FLG VARCHAR(4)	NULL DEFAULT 'NONE' COMMENT 'NONE:해당안됨|OVER:30일경과|PRAC:30일미경과', 
	CREATE_DATE 	TIMESTAMP 	NULL DEFAULT CURRENT_TIMESTAMP, 
	CREATE_USER		VARCHAR(10) DEFAULT NULL, 
	UPDATE_DATE 	TIMESTAMP 	NULL DEFAULT CURRENT_TIMESTAMP, 
	UPDATE_USER		VARCHAR(10) DEFAULT NULL, 
	DELETE_DATE		TIMESTAMP 	NULL DEFAULT CURRENT_TIMESTAMP, 
	DELETE_USER		VARCHAR(10) DEFAULT NULL 
); 
DESC CUSTOM_PERSON_HISTORY;

INSERT INTO CUSTOM_PERSON_HISTORY VALUES ( DEFAULT, 10000001, 2, "개인고객", "P", "임시개인고객", "W", DEFAULT, "2022-06-07 23:42:28", 10000001, NULL, NULL, NULL, NULL );
INSERT INTO CUSTOM_PERSON_HISTORY VALUES ( DEFAULT, 10000002, 2, "개인고객", "P", "임시개인고객", "M", DEFAULT, "2022-07-06 12:42:40", 10000002, NULL, NULL, NULL, NULL ); 
INSERT INTO CUSTOM_PERSON_HISTORY VALUES ( DEFAULT, 10000003, 2, "개인고객",  "P", "임시개인고객", "M", DEFAULT, "2022-07-06 12:44:19", 10000003, NULL, NULL, NULL, NULL ); 
INSERT INTO CUSTOM_PERSON_HISTORY VALUES ( DEFAULT, 10000004, 2, "개인고객",  "P", "임시개인고객", "M", 'PRAC', "2022-07-06 12:53:00", 10000004, NULL, NULL, NULL, NULL ); 
INSERT INTO CUSTOM_PERSON_HISTORY VALUES ( DEFAULT, 10000005, 2, "개인고객",  "P", "임시개인고객", NULL, DEFAULT, "2022-07-06 17:40:57", 10000005, NULL, NULL, NULL, NULL ); 
INSERT INTO CUSTOM_PERSON_HISTORY VALUES ( DEFAULT, 60200001, 602, "수산업공급자",  "P", "임시개인고객", NULL, DEFAULT, "2022-02-18 10:00:00", 60200001, NULL, NULL, NULL, NULL ); 
INSERT INTO CUSTOM_PERSON_HISTORY VALUES ( DEFAULT, 60200002, 602, "수산업공급자",  "P", "임시개인고객", NULL, 'PRAC', "2022-02-18 10:00:00", 60200002, NULL, NULL, NULL, NULL ); 
INSERT INTO CUSTOM_PERSON_HISTORY VALUES ( DEFAULT, 60200003, 602, "수산업공급자",  "P", "임시개인고객", NULL, DEFAULT, "2022-05-25 11:00:00", 90000003, NULL, NULL, NULL, NULL ); 
INSERT INTO CUSTOM_PERSON_HISTORY VALUES ( DEFAULT, 60200004, 602, "수산업공급자",  "P", "임시개인고객", "W", DEFAULT, "2022-06-07 23:52:45", 60200003, NULL, NULL, NULL, NULL ); 
SELECT * FROM CUSTOM_PERSON_LOGIN WHERE DELETE_DATE IS NOT NULL;
SELECT * FROM CUSTOM_PERSON_HISTORY;

 -- 통계 작성중
 -- 기업고객 가입회원 (고객구분별 가입인원수 통계)
SELECT YEAR(CREATE_DATE) YEAR, MONTH(CREATE_DATE) MONTH, CUSTOM_KIND_NM, COUNT(CUSTOM_ID) CNT 
FROM CUSTOM_COMP_HISTORY A
WHERE DELETE_DATE IS NULL
GROUP BY YEAR, MONTH, CUSTOM_KIND_CD
ORDER BY YEAR, MONTH;

 -- 기업고객 가입회원 (고객등급별 가입인원수 통계)
SELECT YEAR(CREATE_DATE) YEAR, MONTH(CREATE_DATE) MONTH, CUSTOM_GRADE, COUNT(CUSTOM_ID) CNT 
FROM CUSTOM_COMP_HISTORY 
WHERE DELETE_DATE IS NULL
GROUP BY YEAR, MONTH, CUSTOM_GRADE
ORDER BY YEAR, MONTH;

-- 개인고객 가입회원 (고객구분별 가입인원수 통계)
SELECT YEAR(CREATE_DATE) YEAR, MONTH(CREATE_DATE) MONTH, CUSTOM_KIND_CD, GENDER, COUNT(CUSTOM_ID) CNT 
FROM CUSTOM_PERSON_HISTORY 
WHERE DELETE_DATE IS NULL
GROUP BY YEAR, MONTH, CUSTOM_KIND_CD
ORDER BY YEAR, MONTH;

-- 개인고객 가입회원 (고객등급별 가입인원수 통계)
SELECT YEAR(CREATE_DATE) YEAR, MONTH(CREATE_DATE) MONTH, CUSTOM_GRADE, GENDER, COUNT(CUSTOM_ID) CNT 
FROM CUSTOM_PERSON_HISTORY 
WHERE DELETE_DATE IS NULL
GROUP BY YEAR, MONTH, CUSTOM_GRADE
ORDER BY YEAR, MONTH;


 -- 기업고객 신규가입회원 - 최근 1개월 기준 (고객구분별 가입인원수 통계)
SELECT YEAR(CREATE_DATE) YEAR, MONTH(CREATE_DATE) MONTH, CUSTOM_KIND_CD, COUNT(CUSTOM_ID) CNT 
FROM CUSTOM_COMP_HISTORY 
WHERE DELETE_DATE IS NULL
AND DELETE_OVER_FLG = 'NONE' 
AND TIMESTAMPDIFF(DAY, CREATE_DATE, NOW()) <= 31
GROUP BY YEAR, MONTH, CUSTOM_KIND_CD
ORDER BY YEAR, MONTH;

 -- 기업고객 신규가입회원 - 최근 1개월 기준 (고객등급별 가입인원수 통계)
SELECT YEAR(CREATE_DATE) YEAR, MONTH(CREATE_DATE) MONTH, CUSTOM_GRADE, COUNT(CUSTOM_ID) CNT 
FROM CUSTOM_COMP_HISTORY 
WHERE DELETE_DATE IS NULL
AND DELETE_OVER_FLG = 'NONE' 
AND TIMESTAMPDIFF(DAY, CREATE_DATE, NOW()) <= 31
GROUP BY YEAR, MONTH, CUSTOM_GRADE
ORDER BY YEAR, MONTH;


 -- 개인고객 신규가입회원 - 최근 1개월 기준 (고객등급별 가입인원수 통계)
SELECT YEAR(CREATE_DATE) YEAR, MONTH(CREATE_DATE) MONTH, CUSTOM_KIND_CD, COUNT(CUSTOM_ID) CNT 
FROM CUSTOM_PERSON_HISTORY 
WHERE DELETE_DATE IS NULL
AND DELETE_OVER_FLG = 'NONE' 
AND TIMESTAMPDIFF(DAY, CREATE_DATE, NOW()) <= 31
GROUP BY YEAR, MONTH, CUSTOM_KIND_CD
ORDER BY YEAR, MONTH;
 
 -- 개인고객 신규가입회원 - 최근 1개월 기준 (고객등급별 가입인원수 통계)
SELECT YEAR(CREATE_DATE) YEAR, MONTH(CREATE_DATE) MONTH, CUSTOM_GRADE, COUNT(CUSTOM_ID) CNT 
FROM CUSTOM_PERSON_HISTORY 
WHERE DELETE_DATE IS NULL
AND DELETE_OVER_FLG = 'NONE' 
AND TIMESTAMPDIFF(DAY, CREATE_DATE, NOW()) <= 31
GROUP BY YEAR, MONTH, CUSTOM_GRADE
ORDER BY YEAR, MONTH;


 -- 기업고객 탈퇴회원
SELECT YEAR(CREATE_DATE) YEAR, MONTH(CREATE_DATE) MONTH, CUSTOM_KIND_CD, /*CUSTOM_GRADE, */COUNT(CUSTOM_ID) CNT 
FROM CUSTOM_COMP_HISTORY 
WHERE DELETE_OVER_FLG IN ('OVER', 'PRAC') 
  AND DELETE_DATE IS NULL
GROUP BY YEAR, MONTH, CUSTOM_KIND_CD 
ORDER BY YEAR, MONTH;

 -- 개인고객 탈퇴회원
SELECT YEAR(CREATE_DATE) YEAR, MONTH(CREATE_DATE) MONTH, CUSTOM_KIND_CD, /*CUSTOM_GRADE, GENDER,*/ COUNT(CUSTOM_ID) CNT  
FROM CUSTOM_PERSON_HISTORY 
WHERE DELETE_OVER_FLG IN ('OVER', 'PRAC')
  AND DELETE_DATE IS NULL
GROUP BY YEAR, MONTH, CUSTOM_KIND_CD 
ORDER BY YEAR, MONTH;


 -- 기업고객 삭제회원
SELECT YEAR(CREATE_DATE) YEAR, MONTH(CREATE_DATE) MONTH, CUSTOM_KIND_CD, /*CUSTOM_GRADE,*/ COUNT(CUSTOM_ID) CNT  
FROM CUSTOM_COMP_HISTORY 
WHERE DELETE_DATE IS NOT NULL
GROUP BY YEAR, MONTH, CUSTOM_KIND_CD  
ORDER BY YEAR, MONTH;

 -- 개인고객 삭제회원
SELECT YEAR(CREATE_DATE) YEAR, MONTH(CREATE_DATE) MONTH, CUSTOM_KIND_CD, /*CUSTOM_GRADE, GENDER,*/ COUNT(CUSTOM_ID) CNT  
FROM CUSTOM_PERSON_HISTORY 
WHERE DELETE_DATE IS NOT NULL 
GROUP BY YEAR, MONTH, CUSTOM_KIND_CD 
ORDER BY YEAR, MONTH;

