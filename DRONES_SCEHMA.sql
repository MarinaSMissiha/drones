CREATE DATABASE restapi;
USE restapi;
CREATE TABLE drone (
  id INT(100) PRIMARY KEY,
  serial_number varchar(100) NOT NULL,
  model VARCHAR(20) NOT NULL,
  weight_limit INT(3) NOT NULL,
  battery_capacity INT(3) NOT NULL,
  state VARCHAR(20) NOT NULL
);
CREATE TABLE medication (
	id INT(100) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    weight int(10) NOT NULL,
    code VARCHAR(100) NOT NULL,
    image BLOB,
    drone_id int(100)
);

INSERT INTO drone values (1, '1234567890', 'Lightweight', 249, 100, 'IDLE');
INSERT INTO drone values (2, '0987654321', 'Middleweight', 249, 90, 'LOADING');
INSERT INTO drone values (3, '6475738593', 'Cruiserweight', 249, 80, 'LOADED');
INSERT INTO drone values (4, '9467435473', 'Heavyweight', 249, 70, 'DELIVERING');
INSERT INTO drone values (5, '6577499444', 'Middleweight', 249, 60, 'DELIVERED');
INSERT INTO drone values (6, '9743674738', 'Heavyweight', 249, 50, 'RETURNING');

INSERT INTO medication values (1, 'med1', 50, 'MED_1');
INSERT INTO medication values (4, 'med2', 100, 'MED_2');
INSERT INTO medication values (5, 'med3', 150, 'MED_3');
INSERT INTO medication values (6, 'med4', 200, 'MED_4');