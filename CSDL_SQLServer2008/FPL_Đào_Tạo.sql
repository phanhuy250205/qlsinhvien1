 USE Master					
	GO					
	IF  EXISTS (SELECT * FROM SYSdatabases  WHERE Name = N'FPL_�T')					
	Drop Database FPL_�T				
	Go					
	Create database FPL_�T					
	on					
	( Name = 'FPL_�T_dat', FILENamE='C:\CSDL\FPL_�T.mdf',					
	size= 5MB,					
	Maxsize=10MB,					
	filegrowth=1MB)					
	LOG ON					
	( Name = 'FPL_�T_log', FILENamE='C:\CSDL\FPL_�T.ldf',					
	size= 1MB,					
	Maxsize=5MB,					
	filegrowth=1MB) 					
	GO					
	--------------------------------------------					
	USE FPL_�T				
	GO
	-------STUDENTS-----------------------------
	IF  EXISTS (SELECT * FROM SYSOBJECTS  WHERE Name = N'STUDENTS')					
	Drop table STUDENTS					
	Go					
	Create table STUDENTS(
		MASV NVARCHAR(50) NOT NULL,
		HOTEN NVARCHAR(50) NULL,
		EMAIL NVARCHAR(50) NULL,
		SODT NVARCHAR(15) NULL,
		GIOITINH BIT NULL,
		DIACHI NVARCHAR(50) NULL,
		HINH NVARCHAR(MAX) NULL,
		CONSTRAINT PK_STUDENTS PRIMARY KEY (MASV)	
	)
	-------GRADE--------------------------------
	IF  EXISTS (SELECT * FROM SYSOBJECTS  WHERE Name = N'GRADE')					
	Drop table GRADE					
	Go					
	Create table GRADE(
		ID INT IDENTITY NOT NULL,
		MASV NVARCHAR(50) NOT NULL,
		TIENGANH FLOAT NULL,
		TINHOC FLOAT NULL,
		GDTC FLOAT NULL,
		CONSTRAINT PK_GRADE PRIMARY KEY (ID),
		CONSTRAINT FK_GRADE_STUDENTS FOREIGN KEY(MASV) REFERENCES STUDENTS
	)
	--------USERS-------------------------------
	IF  EXISTS (SELECT * FROM SYSOBJECTS  WHERE Name = N'USERS')					
	Drop table USERS					
	Go					
	Create table USERS(
		USERNAME NVARCHAR(50) NOT NULL,
		PASSWORDD NVARCHAR(50) NULL,
		ROLE NVARCHAR(50) NULL,
		CONSTRAINT PK_USERS PRIMARY KEY(USERNAME)
	)
