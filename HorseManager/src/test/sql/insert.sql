INSERT INTO Horse (name, weight, height, imgfilepath, bdate) VALUES ('Wham', '500','501', '', '2000-11-11');
INSERT INTO Horse (name, weight, height, imgfilepath, bdate) VALUES ('Bam', '400','401', '', '2000-11-12');
INSERT INTO Horse (name, weight, height, imgfilepath, bdate) VALUES ('Thank', '300', '301', '', '2000-11-13');
INSERT INTO Horse (name, weight, height, imgfilepath, bdate) VALUES ('You', '200','201', '', '2000-11-14');
INSERT INTO Horse (name, weight, height, imgfilepath, bdate) VALUES ('Mam','100','101', '', '2000-11-15');

INSERT INTO Booking (bnr, dayofbooking, customer) VALUES (nextval('seq_bnr'), '2014-11-01', 'David');
INSERT INTO Appointment (anr, adate, starttime, endtime, bnr, hname) VALUES (nextval('seq_anr'), '2014-10-30', '10:00:00', '12:00:00', currval('seq_bnr'), 'Wham');

INSERT INTO Booking (bnr, dayofbooking, customer) VALUES (nextval('seq_bnr'), '2014-11-01', 'David');
INSERT INTO Appointment (anr, adate, starttime, endtime, bnr, hname) VALUES (nextval('seq_anr'), '2014-12-01', '10:00:00', '12:00:00', currval('seq_bnr'), 'Wham');
INSERT INTO Appointment (anr, adate, starttime, endtime, bnr, hname) VALUES (nextval('seq_anr'), '2014-12-01', '13:00:00', '14:00:00', currval('seq_bnr'), 'Wham');

INSERT INTO Booking (bnr, dayofbooking, customer) VALUES (nextval('seq_bnr'), '2014-11-01', 'Jenny');
INSERT INTO Appointment (anr, adate, starttime, endtime, bnr, hname) VALUES (nextval('seq_anr'), '2014-12-02', '10:00:00', '12:00:00', currval('seq_bnr'), 'Bam');
INSERT INTO Appointment (anr, adate, starttime, endtime, bnr, hname) VALUES (nextval('seq_anr'), '2014-12-01', '13:00:00', '15:00:00', currval('seq_bnr'), 'Bam');

INSERT INTO Booking (bnr, dayofbooking, customer) VALUES (nextval('seq_bnr'), '2014-11-01', 'Steve');
INSERT INTO Appointment (anr, adate, starttime, endtime, bnr, hname) VALUES (nextval('seq_anr'), '2014-12-03', '14:00:00', '15:00:00', currval('seq_bnr'), 'Thank');
INSERT INTO Appointment (anr, adate, starttime, endtime, bnr, hname) VALUES (nextval('seq_anr'), '2014-12-03', '16:00:00', '19:00:00', currval('seq_bnr'), 'Thank');

INSERT INTO Booking (bnr, dayofbooking, customer) VALUES (nextval('seq_bnr'), '2014-11-01', 'Franz');
INSERT INTO Appointment (anr, adate, starttime, endtime, bnr, hname) VALUES (nextval('seq_anr'), '2014-12-04', '10:00:00', '12:00:00', currval('seq_bnr'), 'Wham');
INSERT INTO Appointment (anr, adate, starttime, endtime, bnr, hname) VALUES (nextval('seq_anr'), '2014-12-04', '12:00:00', '13:00:00', currval('seq_bnr'), 'Bam');
INSERT INTO Appointment (anr, adate, starttime, endtime, bnr, hname) VALUES (nextval('seq_anr'), '2014-12-04', '16:00:00', '17:00:00', currval('seq_bnr'), 'Thank');

INSERT INTO Booking (bnr, dayofbooking, customer) VALUES (nextval('seq_bnr'), '2014-11-01', 'Richard');
INSERT INTO Appointment (anr, adate, starttime, endtime, bnr, hname) VALUES (nextval('seq_anr'), '2014-12-05', '10:00:00', '12:00:00', currval('seq_bnr'), 'You');

INSERT INTO Booking (bnr, dayofbooking, customer) VALUES (nextval('seq_bnr'), '2014-11-01', 'Eric');
INSERT INTO Appointment (anr, adate, starttime, endtime, bnr, hname) VALUES (nextval('seq_anr'), '2014-12-06', '10:00:00', '12:00:00', currval('seq_bnr'), 'Mam');