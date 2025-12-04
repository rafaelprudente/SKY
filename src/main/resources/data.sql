INSERT IGNORE INTO tb_users (username, password, email, name, enabled, account_non_expired, account_non_locked,
                             credentials_non_expired)
VALUES ('admin', '$2a$12$F7jY6ntdMzZpDi7IziTOCeXQLyeDG5YsUkdxSuS9J8I679d3mNqzy', 'test@sky.uk', 'Test User', true, true,
        true, true);

INSERT IGNORE INTO tb_roles (id, name)
VALUES (1, 'ROLE_ADMIN');

INSERT IGNORE INTO tb_roles (id, name)
VALUES (2, 'ROLE_USER');

INSERT IGNORE INTO tb_user_roles (user_id, role_id)
VALUES (1, 1);