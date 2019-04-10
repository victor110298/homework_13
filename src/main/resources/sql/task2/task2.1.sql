ALTER TABLE developers ADD COLUMN salary INT NOT NULL;

UPDATE developers
SET salary = 1000
WHERE developers_id = 1;

UPDATE developers
SET salary = 2300
WHERE developers_id= 2;

UPDATE developers
SET salary = 1500
WHERE developers_id= 3;
