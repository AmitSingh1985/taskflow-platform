CREATE TABLE notifications(

    id UUID PRIMARY KEY,

    task_id UUID,

    project_id UUID,

    assigned_user_id UUID,

    title VARCHAR(255),

    status VARCHAR(30),

    created_at TIMESTAMP

);