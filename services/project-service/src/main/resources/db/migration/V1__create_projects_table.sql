CREATE TABLE projects
(
    id UUID PRIMARY KEY,

    name VARCHAR(100) NOT NULL,

    description VARCHAR(1000),

    status VARCHAR(30) NOT NULL,

    owner_id UUID NOT NULL,

    start_date DATE,

    end_date DATE,

    archived BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_projects_owner
ON projects(owner_id);

CREATE INDEX idx_projects_status
ON projects(status);