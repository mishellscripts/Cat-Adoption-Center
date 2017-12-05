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

delimiter //
CREATE TRIGGER free_cat
BEFORE INSERT ON cat
FOR EACH ROW
BEGIN
    IF NEW.adoption_fee IS NULL THEN
        INSERT INTO cat(cID, cName, age, gender, breed, adoption_fee, locID)
        VALUES(NEW.cID, NEW.cName, NEW.age, NEW.gender, NEW.breed, 0, NEW.locID);
    END IF;
END;
//
delimiter ;

delimiter //
CREATE TRIGGER adoption_age_restriction
AFTER INSERT ON adoption
FOR EACH ROW
BEGIN
    IF NEW.pID IN (SELECT pID FROM person WHERE age < 12) THEN
        DELETE FROM adoption WHERE pID=NEW.pID AND cID=NEW.cID;
	ELSE
		UPDATE cat SET adopted=1 WHERE cID=NEW.cID;
    END IF;
END;
//
delimiter ;