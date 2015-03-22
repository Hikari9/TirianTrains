-- unfinished: route, trip_schedule, ticket, trip_assignment
INSERT INTO TRAIN_MODEL VALUES
(0001, 'S-500', 100, 50, 4, False, False, True, True, False, False, 'S-train'),
(0002, 'A-400', 80, 45, 3, True, True, False, False, False, True, 'A-train'),
(0003, 'S-520', 120, 55, 5, True, True, True, False, False, False, 'S-train'),
(0004, 'A-450', 90, 48, 4, True, True, True, True, False, False, 'A-train'),
(0005, 'S-596', 120, 56, 4, True, True, True, False, True, False, 'S-train');

INSERT INTO TRAIN VALUES
(0001, 0003),
(0002, 0003),
(0003, 0004),
(0004, 0001),
(0005, 0002);

INSERT INTO CREW VALUES
( 0001, 'Beaver', 'B', 'Beaverton'),
( 0002, 'Ryan', 'C', 'Crest'),
( 0003, 'Mister', 'J', 'Sean'),
( 0004, 'Hal', 'E', 'Berry'),
( 0005, 'Johhny', 'O', 'Bryant');

INSERT INTO MAINTENANCE_HISTORY VALUES
(0001, 0003, 0002, 09, 8, 2014, 'Good'),
(0002, 0004, 0002, 09, 20, 2014, 'Very Good'),
(0003, 0001, 0001, 11, 18, 2014, 'Excellent'),
(0004, 0002, 0005, 12, 22, 2014, 'Excellent'),
(0005, 0003, 0003, 02, 13, 2015, 'Good');

INSERT INTO CUSTOMER VALUES
( 0001, 'Lucy', 'R', 'Pevensie', 10, 12, 1995, 'Female'),
( 0002, 'Edmund', 'R', 'Pevensie', 02, 19, 1992, 'Male'),
( 0003, 'Susan', 'R', 'Pevensie', 04, 8, 1989, 'Female'),
( 0004, 'Peter', 'R', 'Pevensie', 12, 25, 1986, 'Male'),
( 0005, 'Prince', 'A', 'Caspian', 11, 9, 1990, 'Male');

INSERT INTO TOWN
VALUES ( 0001, 'Mr.Tumms');
    INSERT INTO STATION
    VALUES ( 0011, 'Mr.Tumms', 0001);
INSERT INTO TOWN
VALUES ( 0002, 'Allies Enclave');
    INSERT INTO STATION
    VALUES ( 0021 , 'Allies Enclave', 0002);
    INSERT INTO STATION
    VALUES ( 0022 , 'The Wardrobe', 0002);
    INSERT INTO STATION
    VALUES ( 0023 , 'The Lamp Post', 0002);
    INSERT INTO STATION
    VALUES ( 0024 , 'Beavers Dam', 0002);
INSERT INTO TOWN
VALUES ( 0003, 'Cauldron Pool');
    INSERT INTO STATION
    VALUES ( 0031, 'Cauldron Pool', 0003);
INSERT INTO TOWN
VALUES ( 0004, 'Father Christmas');
    INSERT INTO STATION
    VALUES ( 0041, 'Father Christmas', 0004);
INSERT INTO TOWN
VALUES ( 0005, 'Cherry Tree');
    INSERT INTO STATION
    VALUES ( 0051, 'Cherry Tree', 0005);
INSERT INTO TOWN
VALUES ( 0006, 'Witchs Camp');
    INSERT INTO STATION
    VALUES ( 0061, 'Witchs Camp', 0006);
INSERT INTO TOWN
VALUES ( 0007, 'The Stone Table');
    INSERT INTO STATION
    VALUES ( 0071, 'The Stone Table', 0007);
INSERT INTO TOWN
VALUES ( 0008, 'Dancing Lawn');
    INSERT INTO STATION
    VALUES ( 0081, 'Dancing Lawn', 0008);
INSERT INTO TOWN
VALUES ( 0009, 'Anvard');
    INSERT INTO STATION
    VALUES ( 0091, 'Anvard', 0009);
INSERT INTO TOWN
VALUES ( 0010, 'Aslans Cave');
    INSERT INTO STATION
    VALUES ( 0101, 'Aslans Cave', 0010);

-- Local Routes in Allies Enclave (unidirectional)

INSERT INTO ROUTE VALUES
(2001, 10, 2, 0022, 0023),
(2002, 10, 2, 0023, 0024),
(2003, 10, 2, 0024, 0022),
(2004, 10, 2, 0023, 0021),
(2005, 10, 2, 0021, 0024);
    
-- Inter-town Routes (bidirectional)

INSERT INTO ROUTE VALUES
(1000, 92, 27, 0011, 0021),
(1001, 21, 40, 0011, 0031),
(1002, 71, 31, 0011, 0041),
(1003, 51, 32, 0011, 0051),
(1004, 81, 38, 0011, 0061),
(1005, 110, 33, 0011, 0071),
(1006, 44, 10, 0011, 0081),
(1007, 87, 13, 0011, 0091),
(1008, 69, 26, 0011, 0101),
(1009, 73, 37, 0021, 0011),
(1010, 53, 26, 0021, 0031),
(1011, 108, 23, 0021, 0041),
(1012, 36, 22, 0021, 0051),
(1013, 25, 6, 0021, 0061),
(1014, 109, 21, 0021, 0071),
(1015, 51, 26, 0021, 0081),
(1016, 33, 38, 0021, 0091),
(1017, 77, 37, 0021, 0101),
(1018, 90, 34, 0031, 0011),
(1019, 94, 3, 0031, 0021),
(1020, 112, 28, 0031, 0041),
(1021, 33, 10, 0031, 0051),
(1022, 75, 33, 0031, 0061),
(1023, 59, 6, 0031, 0071),
(1024, 79, 29, 0031, 0081),
(1025, 51, 18, 0031, 0091),
(1026, 119, 3, 0031, 0101),
(1027, 51, 40, 0041, 0011),
(1028, 29, 34, 0041, 0021),
(1029, 69, 36, 0041, 0031),
(1030, 73, 9, 0041, 0051),
(1031, 113, 22, 0041, 0061),
(1032, 115, 39, 0041, 0071),
(1033, 66, 4, 0041, 0081),
(1034, 118, 40, 0041, 0091),
(1035, 66, 11, 0041, 0101),
(1036, 34, 6, 0051, 0011),
(1037, 27, 35, 0051, 0021),
(1038, 64, 4, 0051, 0031),
(1039, 72, 28, 0051, 0041),
(1040, 33, 26, 0051, 0061),
(1041, 86, 6, 0051, 0071),
(1042, 61, 32, 0051, 0081),
(1043, 24, 15, 0051, 0091),
(1044, 36, 36, 0051, 0101),
(1045, 71, 31, 0061, 0011),
(1046, 29, 2, 0061, 0021),
(1047, 37, 21, 0061, 0031),
(1048, 101, 39, 0061, 0041),
(1049, 63, 16, 0061, 0051),
(1050, 79, 6, 0061, 0071),
(1051, 52, 13, 0061, 0081),
(1052, 105, 34, 0061, 0091),
(1053, 63, 2, 0061, 0101),
(1054, 93, 7, 0071, 0011),
(1055, 33, 27, 0071, 0021),
(1056, 66, 24, 0071, 0031),
(1057, 99, 12, 0071, 0041),
(1058, 73, 10, 0071, 0051),
(1059, 55, 25, 0071, 0061),
(1060, 59, 5, 0071, 0081),
(1061, 116, 36, 0071, 0091),
(1062, 106, 4, 0071, 0101),
(1063, 82, 15, 0081, 0011),
(1064, 28, 4, 0081, 0021),
(1065, 80, 20, 0081, 0031),
(1066, 102, 14, 0081, 0041),
(1067, 80, 35, 0081, 0051),
(1068, 59, 20, 0081, 0061),
(1069, 49, 35, 0081, 0071),
(1070, 32, 26, 0081, 0091),
(1071, 81, 38, 0081, 0101),
(1072, 26, 18, 0091, 0011),
(1073, 79, 38, 0091, 0021),
(1074, 107, 33, 0091, 0031),
(1075, 73, 26, 0091, 0041),
(1076, 69, 34, 0091, 0051),
(1077, 78, 34, 0091, 0061),
(1078, 76, 20, 0091, 0071),
(1079, 93, 12, 0091, 0081),
(1080, 59, 21, 0091, 0101),
(1081, 52, 9, 0101, 0011),
(1082, 51, 24, 0101, 0021),
(1083, 36, 2, 0101, 0031),
(1084, 59, 5, 0101, 0041),
(1085, 60, 35, 0101, 0051),
(1086, 117, 24, 0101, 0061),
(1087, 83, 37, 0101, 0071),
(1088, 32, 16, 0101, 0081),
(1089, 66, 13, 0101, 0091);

-- INSERT INTO TRIP_SCHEDULE
-- VALUES (

-- INSERT INTO TICKET
-- VALUES (

INSERT INTO TASK VALUES
(0001, 'Cleaning'),
(0002, 'Replacement of brake discs'),
(0003, 'Complete oil change'),
(0004, 'Interior painting'),
(0005, 'Wheel maintenance');

-- INSERT INTO TRIP_ASSIGNMENT
-- VALUES (

INSERT INTO TASK_DOING VALUES
(0001,0005),
(0002,0004),
(0003,0003),
(0004,0002),
(0005,0001);

