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

delimiter //
CREATE PROCEDURE register_cat(
  IN uName VARCHAR(20),
  IN uAge INT,
  IN uGender VARCHAR(1),
  IN uBreed VARCHAR(20),
  IN uAdoption_fee DOUBLE,
  IN uLocID INT,
  IN uAdopted INT
)
BEGIN
  INSERT INTO cat (name, age, gender, breed, adoption_fee, locID, adopted)
  VALUES (uName, uAge, uGender, uBreed, uAdoption_fee, uLocID, uAdopted);
END;
//
delimiter ;

delimiter //
CREATE PROCEDURE update_cat_medical_fee(IN uCID INT, IN uDisease VARCHAR(20), IN uFee DOUBLE)
BEGIN
   UPDATE medical SET medical_fee = uFee WHERE cID = uCID AND disease = uDisease;
END;
//
delimiter ;

delimiter //
CREATE PROCEDURE remove_cat_medical_record(IN uCID INT, IN uDisease VARCHAR(20))
BEGIN
  DELETE FROM medical WHERE cID = uCID AND disease = uDisease;
END;
//
delimiter ;

delimiter //
CREATE PROCEDURE register_cat_medical_record(IN uCID INT, IN uDisease VARCHAR(20), IN uFee DOUBLE)
BEGIN
  INSERT INTO medical (cID, disease, medical_fee) VALUES (uCID, uDisease, uFee);
END;
//
delimiter ;

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
