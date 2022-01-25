create table if not exists authorities (
  username varchar(50) DEFAULT NULL,
  authority varchar(50) DEFAULT NULL
);

create table if not exists users (
  username varchar(45) NOT NULL,
  password varchar(300) DEFAULT NULL,
  enabled tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (username)
);

create table if not exists user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username)
);