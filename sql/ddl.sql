DROP DATABASE IF EXISTS catadoptiondb;
CREATE DATABASE catadoptiondb;
USE catadoptiondb;

CREATE TABLE adoption_center (
    locID INT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(20) NOT NULL
);

CREATE TABLE cat (
    cID INT AUTO_INCREMENT PRIMARY KEY,
    cName VARCHAR(20),
    age INT NOT NULL,
    gender CHAR(1) NOT NULL,
    breed VARCHAR(20) NOT NULL,
    adoption_fee DOUBLE,
    locID INT,
    adopted INT DEFAULT 0,
    FOREIGN KEY (locID) REFERENCES adoption_center(locID) ON DELETE CASCADE
);

CREATE TABLE person (
    pID INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    age INT NOT NULL,
    experience VARCHAR(10) NOT NULL
);

CREATE TABLE medical (
    cID INT,
    disease VARCHAR(20) NOT NULL,
    medical_fee DOUBLE,
    PRIMARY KEY(cID, disease),
    FOREIGN KEY(cID) REFERENCES cat(cID) ON DELETE CASCADE
);

CREATE TABLE adoption (
    aID INT AUTO_INCREMENT PRIMARY KEY,
    pID INT,
    cID INT,
    adoption_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (pID) REFERENCES person(pID) ON DELETE CASCADE,
    FOREIGN KEY (cID) REFERENCES cat(cID) ON DELETE CASCADE
);

CREATE TABLE archive (
    aID INT AUTO_INCREMENT PRIMARY KEY,
    pID INT,
    cID INT,
    adoption_date TIMESTAMP
);

DROP TRIGGER IF EXISTS adoption_age_restriction;
delimiter //
CREATE TRIGGER adoption_age_restriction
BEFORE INSERT ON person
FOR EACH ROW
BEGIN
  IF (NEW.age<12) THEN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Age must be 12 or older in order to adopt a cat';
  END IF;
END;
//
delimiter ;

DROP TRIGGER IF EXISTS set_adopted;
delimiter //
CREATE TRIGGER set_adopted
AFTER INSERT ON adoption
FOR EACH ROW
BEGIN
        UPDATE cat SET adopted=1 WHERE cID=NEW.cID;
END;
//
delimiter ;

DROP TRIGGER IF EXISTS reset_adopted;
delimiter //
CREATE TRIGGER reset_adopted
AFTER DELETE ON adoption
FOR EACH ROW
BEGIN
    UPDATE cat SET adopted=0 WHERE cID=OLD.cID;
END;
//
delimiter ;

LOAD DATA LOCAL INFILE 'C:/Users/Michelle/mysql/adoption_center.csv' INTO TABLE adoption_center
FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';
LOAD DATA LOCAL INFILE 'C:/Users/Michelle/mysql/cat.csv' INTO TABLE cat
FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';
LOAD DATA LOCAL INFILE 'C:/Users/Michelle/mysql/person.csv' INTO TABLE person
FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';
LOAD DATA LOCAL INFILE 'C:/Users/Michelle/mysql/medical.csv' INTO TABLE medical
FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';
LOAD DATA LOCAL INFILE 'C:/Users/Michelle/mysql/adoption.csv' INTO TABLE adoption
FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';
