CREATE TABLE project_members
(
    id UUID PRIMARY KEY,

    project_id UUID NOT NULL,

    user_id UUID NOT NULL,

    role VARCHAR(30) NOT NULL,

    joined_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_project_member_project
        FOREIGN KEY (project_id)
        REFERENCES projects(id)
        ON DELETE CASCADE
);

CREATE UNIQUE INDEX uk_project_member
ON project_members(project_id, user_id);

CREATE INDEX idx_project_member_project
ON project_members(project_id);

CREATE INDEX idx_project_member_user
ON project_members(user_id);