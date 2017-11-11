delimiter //
CREATE PROCEDURE adopt_cat(IN pID INT, IN cID INT)
BEGIN
INSERT INTO adoption(pID, cID) VALUES(pID, cID);
END;
//
delimiter ;

delimiter //
CREATE PROCEDURE donate_cat(IN name VARCHAR(20), IN age INT, 
IN gender CHAR(1), IN breed VARCHAR(20), IN locID INT)
BEGIN
INSERT INTO cat(name, age, gender, breed, locID) 
VALUES(name, age, gender, breed, locID);
END;
//
delimiter ;

delimiter //
CREATE PROCEDURE search_adoption_center(IN location VARCHAR(20))
BEGIN
SELECT * FROM adoption_center WHERE location=location;
END;
//
delimiter ;

delimiter //
CREATE PROCEDURE search_adoption_center_cats(IN locID INT)
BEGIN
SELECT * FROM cat WHERE locID=locID AND adopted=0;
END;
//
delimiter ;

delimiter //
CREATE PROCEDURE search_adoption_center_records(IN locID INT)
BEGIN
SELECT cID, age, gender, breed, disease 
FROM cat JOIN medical USING(cID) 
WHERE locID=locID AND adopted=0;
END;
//
delimiter ;

delimiter //
CREATE PROCEDURE return_cat(IN cID INT)
BEGIN
DELETE FROM adoption WHERE cID=cID;
UPDATE cat SET adopted=0 WHERE cID=cID;
END;
//
delimiter ;

delimiter //
CREATE PROCEDURE search_records()
BEGIN
SELECT c.CID, c.name, c.age, c.gender, c.breed, m.disease
FROM cat c, medical m
WHERE c.cID = m.cID;
END;
//
delimiter ;