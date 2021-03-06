/* 창고구분 T */
CREATE TABLE WAREHOUSE_PART (
    IDX				INT(8) UNIQUE AUTO_INCREMENT,
    WH_MNG_CD		INT(3) NOT NULL PRIMARY KEY, -- 001~999
    WH_CAPACITY_CD	VARCHAR(1) NOT NULL UNIQUE, -- 창고역량코드: S입고 F출고
    WH_CAPACITY_NM	VARCHAR(8) NOT NULL,
    WH_PART_CD		VARCHAR(2) NOT NULL UNIQUE, -- 창고구분코드: G1일반창고 A1농산물창고 I1아이스창고
    WH_PART_NM		VARCHAR(16) NOT NULL,
    CREATE_DATE		TIMESTAMP NULL,
    DELETE_DATE		TIMESTAMP NULL
);



/* 표준규격 T */
CREATE TABLE STD_UNIT (
    IDX				INT(8) UNIQUE AUTO_INCREMENT,
    STD_UNIT_CD		VARCHAR(3)	NOT NULL PRIMARY KEY, -- A01~Z99
    STD_UNIT_NM		VARCHAR(13)	NOT NULL, -- 캔1~N, 비닐1~N, 음료통용기1~N, 요거트용기1~N,우유용기1~N,양념장용기1~N,물용기1~N,종이팩1~N, 플라스틱팩1~N
    WIDTH			INT(8) NULL,
    HEIGHT			INT(8) NULL,
    BREADTH			INT(8) NULL,
    BULKY			INT(8) NULL,
    HEAVY			INT(8) NULL,
    UNIT			INT(8) NULL,
    CREATE_DATE		TIMESTAMP NULL,
    DELETE_DATE		TIMESTAMP NULL
);


/* 팔레트규격 T */
CREATE TABLE PALETTE_UNIT (
    IDX				INT(8) UNIQUE AUTO_INCREMENT,
    PALETTE_AGENCY	INT(8) NOT NULL, -- 팔레트 위탁업체
    PALETTE_CD		VARCHAR(16)	NOT NULL, -- 팔레트 표준규격 코드
    WIDTH			INT(4) NULL, 
    HEIGHT			INT(4) NULL,
    BREADTH			INT(3) NULL,
    STD_UNIT		VARCHAR(2) NULL, -- 단위 mm
    HEAVY			FLOAT(2,1) NULL,
    HEAVY_UNIT		VARCHAR(2) NULL, -- 단위 kg
    USES			VARCHAR(25) NULL,
    FEATURE			VARCHAR(52) NULL,
    CREATE_DATE		TIMESTAMP NULL,
    DELETE_DATE		TIMESTAMP NULL,
    PRIMARY KEY (PALETTE_AGENCY, PALETTE_CD),
    KEY FK_PALETTE_UNIT_PALETTE_AGENCY (PALETTE_AGENCY),
    CONSTRAINT FK_PALETTE_UNIT_PALETTE_AGENCY FOREIGN KEY (PALETTE_AGENCY) REFERENCES CUSTOM_COMP (CUSTOM_ID) ON DELETE CASCADE ON UPDATE CASCADE
);

/* 팔레트관리 T */
CREATE TABLE PALETTE_MNG (
    IDX				INT(8) UNIQUE AUTO_INCREMENT,
    PALETTE_AGENCY	INT(8) NOT NULL, -- 팔레트 위탁업체
    PALETTE_CD		VARCHAR(16)	NOT NULL, -- 팔레트 표준규격 코드
    BORROW_DATE		TIMESTAMP NULL, -- 빌린날짜
    RETURN_DATE		TIMESTAMP NULL, -- 반납날짜
    WH_MNG_CD		INT(3) NOT NULL, -- 창고관리코드
    S_DATE			TIMESTAMP NULL, -- 입고일
    F_DATE			TIMESTAMP NULL, -- 출고일
    PRIMARY KEY (PALETTE_AGENCY, PALETTE_CD),
    KEY FK_PALETTE_MNG_WH_MNG_CD (WH_MNG_CD),
    CONSTRAINT FK_PALETTE_MNG_WH_MNG_CD FOREIGN KEY (WH_MNG_CD) REFERENCES WAREHOUSE_PART (WH_MNG_CD) ON DELETE CASCADE ON UPDATE CASCADE
);

/* 차량관리 T */
CREATE TABLE VEHICLE_MNG (
    IDX				INT(8) UNIQUE AUTO_INCREMENT,
    VEHICLE_NO		VARCHAR(10) PRIMARY KEY, -- 999가9999
    VEHICLE_KIND_CD	VARCHAR(3) NOT NULL, -- T08 8톤트럭 T01 트럭 I01 냉동차 F01 지게차 000 기타
    VEHICLE_KIND_NM	VARCHAR(52) NULL, 
    WH_MNG_CD		INT(3) NOT NULL, -- 창고관리코드
    TRANS_CD		VARCHAR(4) NULL,
    TRANS_NM		VARCHAR(16) NULL,
    S_DATE			TIMESTAMP NULL, -- 입차일
    F_DATE			TIMESTAMP NULL, -- 출차일
    CREATE_DATE		TIMESTAMP NULL,
    DELETE_DATE		TIMESTAMP NULL,
    KEY FK_VEHICLE_MNG_WH_MNG_CD (WH_MNG_CD),
    CONSTRAINT FK_VEHICLE_MNG_WH_MNG_CD FOREIGN KEY (WH_MNG_CD) REFERENCES WAREHOUSE_PART (WH_MNG_CD) ON DELETE CASCADE ON UPDATE CASCADE
);


/* 입고 T */
CREATE TABLE STOCK_WH (
    IDX					INT(8) UNIQUE AUTO_INCREMENT,
    SUPPLIER_NO			INT(8) NOT NULL, 
    PRODUCT_NO			INT(8) NOT NULL,
    PRODUCT_NM			VARCHAR(15) NOT NULL,
    RACK_PART_CD		VARCHAR(5) NULL, -- P0001~P9999
    PART_POSITION_NO	VARCHAR(13) NULL, -- RACK_AA_99999
    STOCK_WH_CD			VARCHAR(2) NULL, -- 입고창고코드: G1일반창고 A1농산물창고 I1아이스창고
    STOCK_QTY			INT(8) NULL,
    INVENTORY_QTY		INT(8) NULL,
    STD_UNIT_CD			VARCHAR(3) NOT NULL, -- A01~Z99
    S_PALETTE_AGENCY	INT(8) NOT NULL,
    S_PALETTE_QTY		INT(8) NOT NULL,
    S_BOX_QTY			INT(8) NULL,
    WH_TRANS_CD			VARCHAR(3) NULL,	-- F01, A01, P01
    WH_TRANS_MEMO		VARCHAR(250) NULL,	-- 'F01:지게차|A01:렉자동시스템|P01:인력################'
    S_STRANS_CD			VARCHAR(4) NULL,	-- A001~Z999
    S_VEHICLE_NO		VARCHAR(10) NULL, -- 999가9999
    S_VEHICLE_QTY		INT(8) NULL,
    S_DATE				TIMESTAMP NULL,
    FORWARDING_WH_CD	VARCHAR(2) NULL, -- 출고창고코드: G1일반창고 A1농산물창고 I1아이스창고
    F_PALETTE_AGENCY	INT(8) NULL,
    F_PALETTE_QTY		INT(8) NULL,
    F_BOX_QTY			INT(8) NULL,
    F_TRANS_CD			VARCHAR(4) NULL, -- A001~Z999
    F_VEHICLE_NO		VARCHAR(10) NULL, -- 999가9999
    F_VEHICLE_QTY		INT(8) NULL,
    F_DATE				TIMESTAMP NULL,
    PRIMARY KEY (SUPPLIER_NO, PRODUCT_NO),
    KEY FK_STOCK_WH_SUPPLIER_NO (SUPPLIER_NO),
    CONSTRAINT FK_STOCK_WH_SUPPLIER_NO FOREIGN KEY (SUPPLIER_NO) REFERENCES CUSTOM_COMP (CUSTOM_ID) ON DELETE CASCADE ON UPDATE CASCADE,
    KEY FK_STOCK_WH_STD_UNIT_CD (STD_UNIT_CD),
    CONSTRAINT FK_STOCK_WH_STD_UNIT_CD FOREIGN KEY (STD_UNIT_CD) REFERENCES STD_UNIT (STD_UNIT_CD) ON DELETE CASCADE ON UPDATE CASCADE,
    KEY FK_STOCK_WH_S_PALETTE_AGENCY (S_PALETTE_AGENCY),
    CONSTRAINT FK_STOCK_WH_S_PALETTE_AGENCY FOREIGN KEY (S_PALETTE_AGENCY) REFERENCES PALETTE_UNIT (PALETTE_AGENCY) ON DELETE CASCADE ON UPDATE CASCADE,
    KEY FK_STOCK_WH_F_PALETTE_AGENCY (F_PALETTE_AGENCY),
    CONSTRAINT FK_STOCK_WH_F_PALETTE_AGENCY FOREIGN KEY (F_PALETTE_AGENCY) REFERENCES PALETTE_UNIT (PALETTE_AGENCY) ON DELETE CASCADE ON UPDATE CASCADE,    
    KEY FK_STOCK_WH_S_VEHICLE_NO (S_VEHICLE_NO),
    CONSTRAINT FK_STOCK_WH_S_VEHICLE_NO FOREIGN KEY (S_VEHICLE_NO) REFERENCES VEHICLE_MNG (VEHICLE_NO) ON DELETE CASCADE ON UPDATE CASCADE,
    KEY FK_STOCK_WH_F_VEHICLE_NO (F_VEHICLE_NO),
    CONSTRAINT FK_STOCK_WH_F_VEHICLE_NO FOREIGN KEY (F_VEHICLE_NO) REFERENCES VEHICLE_MNG (VEHICLE_NO) ON DELETE CASCADE ON UPDATE CASCADE
);