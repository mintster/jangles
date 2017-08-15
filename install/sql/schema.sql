DROP TABLE IF EXISTS jangles_users;
CREATE TABLE `jangles_users` (
  `user_id`      BIGINT(20) NOT NULL AUTO_INCREMENT,
  `username`     VARCHAR(25)         DEFAULT NULL,
  `password`     VARCHAR(50)         DEFAULT NULL,
  `display_name` VARCHAR(50)         DEFAULT NULL,
  `date_created` TIMESTAMP  NULL     DEFAULT NULL,
  `is_active`    TINYINT(1)          DEFAULT NULL,
  PRIMARY KEY (`user_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = latin1;



