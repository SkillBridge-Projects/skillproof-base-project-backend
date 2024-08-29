CREATE DATABASE  IF NOT EXISTS linkedin_db /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE linkedin_db;
--
-- Table structure for table user
--
CREATE TABLE IF NOT EXISTS user (
  id VARCHAR(20) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  username VARCHAR(201) NOT NULL,
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
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT `FK_NOTIFICATION_USER`
        FOREIGN KEY (follower_user_id)
            REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS post (
    id BIGINT AUTO_INCREMENT,
    user_id VARCHAR(20) NOT NULL,
    image_url TEXT NULL,
    video_url TEXT NULL,
    content varchar(1000) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT `FK_POST_USER`
        FOREIGN KEY (user_id)
            REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS likes (
    id BIGINT AUTO_INCREMENT,
    user_id VARCHAR(20) NOT NULL,
    post_id BIGINT NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT `FK_LIKE_USER`
        FOREIGN KEY (user_id)
            REFERENCES user(id),
    CONSTRAINT `FK_LIKE_POST`
        FOREIGN KEY (post_id)
            REFERENCES post(id)
);

CREATE TABLE IF NOT EXISTS comment (
    id BIGINT AUTO_INCREMENT,
    user_id VARCHAR(20) NOT NULL,
    post_id BIGINT NOT NULL,
    content varchar(1000) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT `FK_COMMENT_USER`
        FOREIGN KEY (user_id)
            REFERENCES user(id),
    CONSTRAINT `FK_COMMENT_POST`
        FOREIGN KEY (post_id)
            REFERENCES post(id)
);

CREATE TABLE IF NOT EXISTS conversation (
    id BIGINT AUTO_INCREMENT,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS message (
    id BIGINT AUTO_INCREMENT,
    conversation_id BIGINT NOT NULL,
    sender_id VARCHAR(20) NOT NULL,
    content VARCHAR(1000) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT `FK_MESSAGE_CONVERSATION`
        FOREIGN KEY (conversation_id)
            REFERENCES conversation(id),
    CONSTRAINT `FK_MESSAGE_USER`
        FOREIGN KEY (sender_id)
            REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS conversation_users (
    conversation_id BIGINT NOT NULL,
    user_id VARCHAR(20) NOT NULL,
    PRIMARY KEY (conversation_id, user_id),
    CONSTRAINT `FK_CONVERSATION_USERS_CONVERSATION`
        FOREIGN KEY (conversation_id)
            REFERENCES conversation(id),
    CONSTRAINT `FK_CONVERSATION_USERS_USER`
        FOREIGN KEY (user_id)
            REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS portfolio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(20) NOT NULL,
    video_url TEXT NOT NULL,
    post_ids TEXT NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL,
    CONSTRAINT `FK_PORTFOLIO_USER`
        FOREIGN KEY (user_id)
            REFERENCES user(id)
);
