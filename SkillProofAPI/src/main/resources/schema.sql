CREATE DATABASE  IF NOT EXISTS linkedin_db /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE linkedin_db;
--
-- Table structure for table user
--
CREATE TABLE IF NOT EXISTS user (
  id VARCHAR(20) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  city VARCHAR(250) NOT NULL,
  phone VARCHAR(20) NULL,
  email_address VARCHAR(100) NOT NULL,
  role VARCHAR(20) NOT NULL,
  password VARCHAR(100) NOT NULL,
  bio TEXT NULL,
  profile_picture_url TEXT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_date TIMESTAMP NULL,
  PRIMARY KEY (id),
  UNIQUE (`email_address`)
);

CREATE TABLE IF NOT EXISTS experience (
  id BIGINT AUTO_INCREMENT,
  company_name VARCHAR(100) NOT NULL,
  designation VARCHAR(100) NOT NULL,
  description VARCHAR(250),
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  user_id VARCHAR(20) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_date TIMESTAMP NULL,
  PRIMARY KEY (id),
  CONSTRAINT `FK_EXPERIENCE_USER`
    FOREIGN KEY (user_id)
    REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS education (
  id BIGINT AUTO_INCREMENT,
  university VARCHAR(250) NOT NULL,
  college_or_school VARCHAR(250) NOT NULL,
  degree VARCHAR(100) NOT NULL,
  grade FLOAT NOT NULL,
  description VARCHAR(250),
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  user_id VARCHAR(20) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_date TIMESTAMP NULL,
  PRIMARY KEY (id),
  CONSTRAINT `FK_EDUCATION_USER`
    FOREIGN KEY (user_id)
    REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS skills (
  id BIGINT AUTO_INCREMENT,
  technology VARCHAR(250) NOT NULL,
  tools TEXT NOT NULL,
  user_id VARCHAR(20) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_date TIMESTAMP NULL,
  PRIMARY KEY (id),
  CONSTRAINT `FK_SKILL_USER`
    FOREIGN KEY (user_id)
      REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS connection (
    id BIGINT AUTO_INCREMENT,
    user_following VARCHAR(20) NOT NULL,
    user_followed_by VARCHAR(20) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT `FK_CONNECTION_FOLLOW_USER`
        FOREIGN KEY (user_following)
            REFERENCES user(id),
    CONSTRAINT `FK_CONNECTION_FOLLOW_BY_USER`
        FOREIGN KEY (user_followed_by)
            REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS notification (
    id BIGINT AUTO_INCREMENT,
    follower_user_id VARCHAR(20) NOT NULL,
    is_read BIT(1) DEFAULT 1,
    message TEXT NOT NULL,
    profile_picture_url TEXT NULL,
    following_user_id varchar(20) NOT NULL,
    following_user_name varchar(200) NOT NULL,
    notification_type VARCHAR(50) NOT NULL,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT `FK_NOTIFICATION_USER`
        FOREIGN KEY (follower_user_id)
            REFERENCES user(id)
);
