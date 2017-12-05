DROP PROCEDURE IF EXISTS adopt_cat;
delimiter //
CREATE PROCEDURE adopt_cat(IN personID INT, IN catID INT)
BEGIN
INSERT INTO adoption(pID, cID) VALUES(personID, catID);
END;
//
delimiter ;

DROP PROCEDURE IF EXISTS donate_cat;
delimiter //
CREATE PROCEDURE donate_cat(IN age INT, IN gender CHAR, IN breed VARCHAR(20), IN locID INT, IN cat_name VARCHAR(20))
BEGIN
INSERT INTO cat(cName, age, gender, breed, locID)
VALUES(cat_name, age, gender, breed, locID);
END;
//
delimiter ;

DROP PROCEDURE IF EXISTS search_adoption_center;
delimiter //
CREATE PROCEDURE search_adoption_center(IN location VARCHAR(20))
BEGIN
SELECT * FROM adoption_center a WHERE a.location=location AND locID IN
(SELECT c.locID FROM adoption_center a, cat c GROUP BY c.locID HAVING(count(*)>0));
END;
//
delimiter ;

DROP PROCEDURE IF EXISTS search_adoption_center_cats;
delimiter //
CREATE PROCEDURE search_adoption_center_cats(IN locID INT)
BEGIN
SELECT * FROM cat c WHERE c.locID=locID AND adopted=0;
END;
//
delimiter ;

DROP PROCEDURE IF EXISTS search_adoption_center_records;
delimiter //
CREATE PROCEDURE search_adoption_center_records(IN locID INT)
BEGIN
SELECT cID, age, gender, breed, disease 
FROM cat c JOIN medical m USING(cID) 
WHERE c.locID=locID AND adopted=0;
END;
//
delimiter ;

DROP PROCEDURE IF EXISTS return_cat;
delimiter //
CREATE PROCEDURE return_cat(IN catID INT)
BEGIN
DELETE FROM adoption WHERE adoption.cID=catID;
UPDATE cat SET adopted=0 WHERE cat.cID=catID;
END;
//
delimiter ;

DROP PROCEDURE IF EXISTS search_records;
delimiter //
CREATE PROCEDURE search_records()
BEGIN
SELECT c.cID, c.cName, c.age, c.gender, c.breed, m.disease, m.medical_fee
FROM cat c LEFT OUTER JOIN medical m USING(cID);
END;
//
delimiter ;

DROP PROCEDURE IF EXISTS register_cat;
delimiter //
CREATE PROCEDURE register_cat(
  IN uName VARCHAR(20),
  IN uAge INT,
  IN uGender VARCHAR(1),
  IN uBreed VARCHAR(20),
  IN uAdoption_fee DOUBLE,
  IN uLocID INT
)
BEGIN
  INSERT INTO cat (cName, age, gender, breed, adoption_fee, locID)
  VALUES (uName, uAge, uGender, uBreed, uAdoption_fee, uLocID);
END;
//
delimiter ;

DROP PROCEDURE IF EXISTS update_cat_medical_fee;
delimiter //
CREATE PROCEDURE update_cat_medical_fee(IN uCID INT, IN uDisease VARCHAR(20), IN uFee DOUBLE)
BEGIN
   UPDATE medical SET medical_fee = uFee WHERE cID = uCID AND disease = uDisease;
END;
//
delimiter ;

DROP PROCEDURE IF EXISTS remove_cat_medical_record;
delimiter //
CREATE PROCEDURE remove_cat_medical_record(IN uCID INT, IN uDisease VARCHAR(20))
BEGIN
  DELETE FROM medical WHERE cID = uCID AND disease = uDisease;
END;
//
delimiter ;

DROP PROCEDURE IF EXISTS register_cat_medical_record;
delimiter //
CREATE PROCEDURE register_cat_medical_record(IN uCID INT, IN uDisease VARCHAR(20), IN uFee DOUBLE)
BEGIN
  INSERT INTO medical (cID, disease, medical_fee) VALUES (uCID, uDisease, uFee);
END;
//
delimiter ;

DROP PROCEDURE IF EXISTS view_all_adoptions;
delimiter //
CREATE PROCEDURE view_all_adoptions()
BEGIN
  SELECT * FROM
  adoption JOIN cat
  ON adoption.cID = cat.cID
  JOIN person ON adoption.pID = person.pID;
END;
//
delimiter ;