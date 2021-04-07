CREATE TABLE usuarios (
    username varchar(45) PRIMARY KEY,
    password varchar(60) NOT NULL,
    enabled TINYINT NOT NULL DEFAULT 1
);

CREATE TABLE usuarios_roles (
    id IDENTITY PRIMARY KEY,
    username VARCHAR(45) NOT NULL,
    role VARCHAR(45) NOT NULL,
    UNIQUE KEY usuarios_roles_uq (role,username)
);

ALTER TABLE usuarios_roles ADD FOREIGN KEY (username) REFERENCES usuarios( username );
