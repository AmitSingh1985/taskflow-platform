ALTER TABLE tasks
    ADD COLUMN predicted_priority VARCHAR(20),
    ADD COLUMN ai_confidence DOUBLE PRECISION,
    ADD COLUMN ai_model_version VARCHAR(50);