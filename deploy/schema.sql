CREATE DATABASE IF NOT EXISTS fonyou;
USE fonyou;
CREATE TABLE IF NOT EXISTS employees (
     id                      INT NOT NULL AUTO_INCREMENT,
     first_name              VARCHAR(255) NOT NULL,
     last_name               VARCHAR(255) NOT NULL,
     start_date     	    DATE NOT NULL COMMENT 'Date of joining the company.',
     end_date		         DATE COMMENT 'Date of departure from the company.',
     base_salary             NUMERIC(19, 2) NOT NULL,
     PRIMARY KEY (id)
);
