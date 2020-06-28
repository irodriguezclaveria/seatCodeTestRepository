DROP TABLE IF EXISTS POSITION;
DROP TABLE IF EXISTS MOWER;
DROP TABLE IF EXISTS PLATEAU;
--MEJORAR LOS CAMPOS... REALMENTE SON CORRECTOS

CREATE TABLE PLATEAU(
	COD_PLATEAU VARCHAR(10) PRIMARY KEY,
	SIZE_X INT NOT NULL DEFAULT 3,
	SIZE_Y INT NOT NULL DEFAULT 3,
	ACTIVE BOOLEAN NOT NULL DEFAULT FALSE 
);

CREATE TABLE MOWER (
  ID LONG AUTO_INCREMENT PRIMARY KEY,
  STATUS VARCHAR(50) NOT NULL,
  COD_PLATEAU VARCHAR(10) NOT NULL,
  COD_MOWER VARCHAR(50) NOT NULL,
  LIVE_TIME INT NOT NULL,
  FOREIGN KEY(COD_PLATEAU) REFERENCES PLATEAU
);
--porque pongo otro ide
CREATE TABLE POSITION (
  ID LONG AUTO_INCREMENT  PRIMARY KEY,
  ID_MOWER LONG NOT NULL UNIQUE,
  POSITION_X INT NOT NULL DEFAULT 0,
  POSTION_Y INT NOT NULL DEFAULT 0,
  CARDINAL_POINT VARCHAR(10) DEFAULT 'NORTH',
  FOREIGN KEY(ID_MOWER) REFERENCES MOWER 
);



INSERT INTO PLATEAU (COD_PLATEAU,ACTIVE) VALUES
  ('PLATEAU_A',TRUE);
  
 
INSERT INTO MOWER (STATUS,COD_PLATEAU,COD_MOWER,LIVE_TIME) VALUES
  ('STOPPED','PLATEAU_A','MOWER_A',10),
  ('STOPPED','PLATEAU_A','MOWER_B',10),
  ('STOPPED','PLATEAU_A','MOWER_C',10);

