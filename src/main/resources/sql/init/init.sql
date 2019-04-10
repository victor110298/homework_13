CREATE TABLE developers (
  developers_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  age VARCHAR(255) NOT NULL,
  sex VARCHAR(255) NOT NULL,
  PRIMARY KEY (developers_id ));

CREATE TABLE skills (
  skills_id INT NOT NULL AUTO_INCREMENT,
  language VARCHAR(255) NOT NULL,
  level VARCHAR(255) NOT NULL,
PRIMARY KEY (skills_id));

CREATE TABLE projects (
  projects_id INT NOT NULL AUTO_INCREMENT,
  project_name VARCHAR(255) NOT NULL,
  type VARCHAR(255),
  PRIMARY KEY (projects_id));

CREATE TABLE companies (
  companies_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (companies_id));

CREATE TABLE customers (
  customers_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (customers_id));

CREATE TABLE developers_projects(
  dev_id INT NOT NULL,
  proj_id INT NOT NULL,
  FOREIGN KEY(dev_id) REFERENCES developers(developers_id),
  FOREIGN KEY(proj_id) REFERENCES projects(projects_id)
);

CREATE TABLE developers_skills(
  dev_id INT NOT NULL,
  skill_id INT NOT NULL,
  FOREIGN KEY(dev_id) REFERENCES developers(developers_id),
  FOREIGN KEY(skill_id) REFERENCES skills(skills_id)
);

CREATE TABLE companies_project(
  comp_id INT NOT NULL ,
  proj_id INT NOT NULL ,
  FOREIGN KEY (comp_id) REFERENCES companies(companies.id),
  FOREIGN KEY (proj_id) REFERENCES projects(projects.id)
);

CREATE TABLE cust_proj(
  cust_id INT NOT NULL ,
  project_id INT NOT NULL ,
  FOREIGN KEY (cust_id) REFERENCES customers(customers_id),
  FOREIGN KEY (project_id) REFERENCES projects(projects_id)
);
