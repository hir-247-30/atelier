CREATE TABLE IF NOT EXISTS t_training (
    id VARCHAR(8) PRIMARY KEY,
    title VARCHAR(256),
    start_date_time TIMESTAMP,
    end_date_time TIMESTAMP,
    reserved INTEGER,
    capacity INTEGER
);

CREATE TABLE IF NOT EXISTS t_reservation (
    id VARCHAR(8) PRIMARY KEY,
    training_id VARCHAR(8),
    student_type_id VARCHAR(4),
    start_date_time TIMESTAMP,
    reserved_date_time TIMESTAMP,
    name VARCHAR(256),
    phone VARCHAR(256),
    email_address VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS t_student_type (
    id VARCHAR(8) PRIMARY KEY,
    code VARCHAR(8),
    name VARCHAR(256)
);
