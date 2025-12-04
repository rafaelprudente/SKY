CREATE TABLE IF NOT EXISTS tb_users
(
    id                      BIGINT PRIMARY KEY AUTO_INCREMENT,
    username                VARCHAR(200) NOT NULL UNIQUE,
    password                VARCHAR(129) NOT NULL,
    email                   VARCHAR(200) NOT NULL,
    name                    VARCHAR(120) NOT NULL,
    enabled                 BOOLEAN,
    account_non_expired     BOOLEAN,
    account_non_locked      BOOLEAN,
    credentials_non_expired BOOLEAN
);

CREATE TABLE IF NOT EXISTS tb_roles
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    CONSTRAINT `FKhjusdfgsdfgsdfglig05ktofg` FOREIGN KEY (`user_id`) REFERENCES `tb_users` (`id`),
    CONSTRAINT `FKhjuy9y4fd8v5m3klig05ktofg` FOREIGN KEY (`role_id`) REFERENCES `tb_roles` (`id`)
);

CREATE TABLE IF NOT EXISTS tb_user_external_project
(
    id      VARCHAR(200) PRIMARY KEY,
    user_id BIGINT       NOT NULL,
    name    VARCHAR(120) NOT NULL,
    CONSTRAINT `Fdfvnlkjiuyfgsdfglig05ktofg` FOREIGN KEY (`user_id`) REFERENCES `tb_users` (`id`)
);