-- Users
CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(120) NOT NULL UNIQUE,
                       email VARCHAR(190) NOT NULL UNIQUE,
                       password VARCHAR(60) NOT NULL
) ENGINE=InnoDB;

-- Roles / Permissions
CREATE TABLE roles (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(64) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE permissions (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             name VARCHAR(128) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Join tables with CASCADE
CREATE TABLE users_roles (
                             user_id BIGINT NOT NULL,
                             role_id BIGINT NOT NULL,
                             PRIMARY KEY (user_id, role_id),
                             CONSTRAINT fk_users_roles_user
                                 FOREIGN KEY (user_id) REFERENCES users(id)
                                     ON DELETE CASCADE,
                             CONSTRAINT fk_users_roles_role
                                 FOREIGN KEY (role_id) REFERENCES roles(id)
                                     ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE roles_permissions (
                                   role_id BIGINT NOT NULL,
                                   permission_id BIGINT NOT NULL,
                                   PRIMARY KEY (role_id, permission_id),
                                   CONSTRAINT fk_roles_permissions_role
                                       FOREIGN KEY (role_id) REFERENCES roles(id)
                                           ON DELETE CASCADE,
                                   CONSTRAINT fk_roles_permissions_permission
                                       FOREIGN KEY (permission_id) REFERENCES permissions(id)
                                           ON DELETE CASCADE
) ENGINE=InnoDB;

-- Refresh Tokens with CASCADE on user delete
CREATE TABLE refresh_tokens (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                token VARCHAR(256) NOT NULL UNIQUE,
                                user_id BIGINT NOT NULL,
                                expires_at DATETIME(6) NOT NULL,
                                revoked BOOLEAN NOT NULL DEFAULT FALSE,
                                CONSTRAINT fk_refresh_token_user
                                    FOREIGN KEY (user_id) REFERENCES users(id)
                                        ON DELETE CASCADE
) ENGINE=InnoDB;

-- Seed basic role/permission (optional)
INSERT INTO roles(name) VALUES ('ROLE_USER')
    ON DUPLICATE KEY UPDATE name=name;