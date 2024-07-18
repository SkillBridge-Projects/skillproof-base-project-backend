CREATE DATABASE  IF NOT EXISTS linkedin_db /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE linkedin_db;
--
-- Table structure for table user
--
CREATE TABLE IF NOT EXISTS user (
  id varchar(20) NOT NULL,
  first_name varchar(100) NOT NULL,
  last_name varchar(100) NOT NULL,
  city varchar(250) NOT NULL,
  phone varchar(20) NULL,
  email_address varchar(100) NOT NULL,
  role varchar(20) NOT NULL,
  password varchar(100) NOT NULL,
  skills TEXT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_date TIMESTAMP NULL,
  PRIMARY KEY (id),
  UNIQUE (`email_address`)
);