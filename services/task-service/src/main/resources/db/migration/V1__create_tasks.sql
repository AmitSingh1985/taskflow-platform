CREATE TABLE tasks
(
    id UUID PRIMARY KEY,

    project_id UUID NOT NULL,

    title VARCHAR(200) NOT NULL,

    description TEXT,

    status VARCHAR(30) NOT NULL,

    priority VARCHAR(30) NOT NULL,

    assigned_to UUID,

    created_by UUID NOT NULL,

    due_date DATE,

    estimated_hours INTEGER,

    completed BOOLEAN NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_task_project
ON tasks(project_id);

CREATE INDEX idx_task_assigned
ON tasks(assigned_to);

CREATE INDEX idx_task_status
ON tasks(status);