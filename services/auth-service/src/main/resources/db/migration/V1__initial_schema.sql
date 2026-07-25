CREATE TABLE application_version
(
    id              BIGSERIAL PRIMARY KEY,
    version         VARCHAR(20) NOT NULL,
    description     VARCHAR(255),
    installed_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO application_version(version, description)
VALUES ('1.0.0', 'Initial schema');