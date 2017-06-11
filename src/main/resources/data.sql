CREATE TABLE jangles_users
(
  user_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_name    VARCHAR(25) NULL,
  password     VARCHAR(50) NULL,
  display_name VARCHAR(50) NULL,
  date_created TIMESTAMP   NULL,
  is_active    TINYINT(1)  NULL
);

INSERT INTO jangles_users (user_id, user_name, password, display_name, date_created, is_active) VALUES (1, 'Jim', 'password', 'Jim Johnson', '2017-05-29 09:56:25', 1);
INSERT INTO jangles_users (user_id, user_name, password, display_name, date_created, is_active) VALUES (2, 'Bill', 'password', 'Bill Blaster', '2017-05-29 09:56:45', 1);
INSERT INTO jangles_users (user_id, user_name, password, display_name, date_created, is_active) VALUES (3, 'Sally', 'password', 'Sally Sql', '2017-05-29 09:57:06', 1);
