ALTER TABLE projects ADD COLUMN cost INT NOT NULL;

UPDATE projects
SET cost = 2000
WHERE projects_id = 1;

UPDATE projects
SET cost = 24300
WHERE projects_id= 2;

UPDATE projects
SET cost = 12500
WHERE projects_id= 3;

UPDATE projects
SET cost = 25500
WHERE projects_id= 4;

UPDATE projects
SET cost = 5500
WHERE projects_id= 5;
