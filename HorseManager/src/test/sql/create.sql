CREATE TABLE Booking (
	bnr INTEGER PRIMARY KEY,
	dayofbooking DATE NOT NULL,
	customer VARCHAR(20) NOT NULL
);

--TODO: imgfilepath? What to put here in INSERT?	
CREATE TABLE Horse (
	name VARCHAR(20) PRIMARY KEY,
	weight INTEGER NOT NULL,
	height INTEGER NOT NULL,
	imgfilepath VARCHAR(50) NOT NULL,
	bdate DATE NOT NULL,
	deleted BIT DEFAULT 0
);

CREATE TABLE Appointment (
	anr INTEGER PRIMARY KEY,
	adate DATE NOT NULL,
	starttime TIME NOT NULL,
	endtime TIME NOT NULL,
	editable BIT DEFAULT 1,
	deleted BIT DEFAULT 0,
	bnr INTEGER NOT NULL,
	hname VARCHAR(20) NOT NULL
);


ALTER TABLE Appointment ADD CONSTRAINT fk_hname FOREIGN KEY (hname) REFERENCES Horse (name);
ALTER TABLE Appointment ADD CONSTRAINT fk_bnr FOREIGN KEY (bnr) REFERENCES Booking (bnr);
CREATE SEQUENCE seq_anr START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_bnr START WITH 100 INCREMENT BY 10;