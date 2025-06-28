CREATE TABLE IF NOT EXISTS t_training (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(256),
    start_date_time TIMESTAMP,
    end_date_time TIMESTAMP,
    reserved INTEGER,
    capacity INTEGER
);

CREATE TABLE IF NOT EXISTS t_reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    training_id BIGINT,
    student_type_id BIGINT,
    start_date_time TIMESTAMP,
    reserved_date_time TIMESTAMP,
    name VARCHAR(256),
    phone VARCHAR(256),
    email_address VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS t_student_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(8),
    name VARCHAR(256)
);
