DROP DATABASE IF NOT EXISTS SejongAcademy;
CREATE DATABASE IF NOT EXISTS SejongAcademy;
SHOW DATABASES;
USE SejongAcademy;

DROP TABLE IF EXISTS Student;
CREATE TABLE IF NOT EXISTS Student(
   NIC VARCHAR(15),
   Name VARCHAR(45)NOT NULL DEFAULT 'Unknown',
   Gender VARCHAR(6),
   DOB VARCHAR(10),
   Age CHAR(5),
   PassportNo VARCHAR (15),
   Address TEXT,
   ContactNo CHAR(15),
   Email VARCHAR(45),
   CONSTRAINT PRIMARY KEY (NIC)
);
SHOW TABLES ;
DESCRIBE Student;

#---------------------------------------------

DROP TABLE IF EXISTS Registration;
CREATE TABLE IF NOT EXISTS Registration(
   NIC VARCHAR(15),
   RId VARCHAR(45),
   Name VARCHAR (45),
   RegistrationDate DATE,
   RegistrationFee DOUBLE DEFAULT 0.0,
   CONSTRAINT PRIMARY KEY (RId),
   CONSTRAINT FOREIGN KEY (NIC) REFERENCES Student(NIC) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE Registration;

#-----------------------------------------------------

DROP TABLE IF EXISTS Attendence ;
CREATE TABLE IF NOT EXISTS Attendence(
   RId VARCHAR(15),
   AId VARCHAR(10),
   Name VARCHAR (30),
   Date DATE,
   Status VARCHAR(10),
   CONSTRAINT PRIMARY KEY (AId),
   CONSTRAINT FOREIGN KEY (RId) REFERENCES Registration(RId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE Attendence ;

#-----------------------------------------------------

DROP TABLE IF EXISTS Class;
CREATE TABLE IF NOT EXISTS Class(
   CId VARCHAR(10),
   Name VARCHAR(50),
   Shedule VARCHAR(50),
   CONSTRAINT PRIMARY KEY (CId)
);
SHOW TABLES ;
DESCRIBE Class;

#-----------------------------------------------------

DROP TABLE IF EXISTS ClassRegistration;
CREATE TABLE IF NOT EXISTS ClassRegistration(
   Num VARCHAR (10),
   RId VARCHAR(10),
   CId VARCHAR(10),
   StudentName VARCHAR(25),
   ClassName VARCHAR(25),
   CONSTRAINT PRIMARY KEY (Num),
   CONSTRAINT FOREIGN KEY (RId) REFERENCES Registration(RId)
);
SHOW TABLES ;
DESCRIBE ClassRegistration;

#----------------------------------------------------

DROP TABLE IF EXISTS TuteBook ;
CREATE TABLE IF NOT EXISTS TuteBook(
   TId VARCHAR(10),
   Name VARCHAR(25),
   QtyOnHand INT,
   CONSTRAINT PRIMARY KEY (TId)
);
SHOW TABLES ;
DESCRIBE TuteBook ;

#-----------------------------------------------

DROP TABLE IF EXISTS TuteBookDetails ;
CREATE TABLE IF NOT EXISTS TuteBookDetails(
   RId VARCHAR(15),
   TId VARCHAR(15),
   Qty INT,
   CONSTRAINT PRIMARY KEY (RId,TId),
   CONSTRAINT FOREIGN KEY (RId) REFERENCES Registration(RId) ON DELETE CASCADE ON UPDATE CASCADE ,
   CONSTRAINT FOREIGN KEY (TId) REFERENCES TuteBook(TId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE TuteBookDetails;

#-----------------------------------------------

DROP TABLE IF EXISTS Exam;
CREATE TABLE IF NOT EXISTS Exam(
   EId VARCHAR(10),
   ExamTitle VARCHAR(25),
   Date DATE,
   CONSTRAINT PRIMARY KEY (EId)
);

SHOW TABLES ;
DESCRIBE Exam;

#----------------------------------------------

DROP TABLE IF EXISTS ExamDetails ;
CREATE TABLE IF NOT EXISTS ExamDetails(
   RId VARCHAR(15),
   EId VARCHAR(15),
   MarksForExam INT,
   CONSTRAINT PRIMARY KEY (RId,EId),
   CONSTRAINT FOREIGN KEY (RId) REFERENCES Registration(RId) ON DELETE CASCADE ON UPDATE CASCADE ,
   CONSTRAINT FOREIGN KEY (EId) REFERENCES Exam(EId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE ExamDetails;

#-----------------------------------------------------

DROP TABLE IF EXISTS Installment ;
CREATE TABLE IF NOT EXISTS Installment(
   IId VARCHAR(10),
   InstallmentName VARCHAR(25),
   InstallmentFee INT,
   CONSTRAINT PRIMARY KEY (IId)
);
SHOW TABLES ;
DESCRIBE Installment ;

------------------------------------------------------

DROP TABLE IF EXISTS Payment ;
CREATE TABLE IF NOT EXISTS Payment(
   PId VARCHAR(10),
   RId VARCHAR(10),
   CId VARCHAR (10),
   IId VARCHAR (10),
   PaymentDate DATE,
   PaymentTime VARCHAR(15),
   Status VARCHAR(10),
   CONSTRAINT PRIMARY KEY (PId),
   CONSTRAINT FOREIGN KEY (RId) REFERENCES Registration(RId) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (CId) REFERENCES Class(CId) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (IId) REFERENCES Installment(IId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE Payment ;


DROP TABLE IF EXISTS Login ;
CREATE TABLE IF NOT EXISTS Login(
   UserName VARCHAR(10),
   Password VARCHAR(50),
   role VARCHAR(45),
   CONSTRAINT PRIMARY KEY (UserName)
);
SHOW TABLES ;
DESCRIBE Login ;
