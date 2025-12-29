CREATE TABLE course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE operator (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255)
);

CREATE TABLE application_request (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255),
    commentary VARCHAR(255),
    phone VARCHAR(50),
    handled BOOLEAN,
    course_id BIGINT,
    operator_id BIGINT,
    FOREIGN KEY (course_id) REFERENCES course(id),
    FOREIGN KEY (operator_id) REFERENCES operator(id)
);
