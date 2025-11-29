DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS instructors;
DROP TABLE IF EXISTS packs;

CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    code VARCHAR(10) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    year INT NOT NULL
);

CREATE TABLE instructors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100)
);

CREATE TABLE packs (
    id SERIAL PRIMARY KEY,
    year INT NOT NULL,
    semester INT NOT NULL,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE courses (
    id SERIAL PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE,
    abbr VARCHAR(20),
    name VARCHAR(150) NOT NULL,
    instructor_id INT REFERENCES instructors(id),
    pack_id INT REFERENCES packs(id),
    group_count INT,
    description TEXT
);

CREATE TABLE enrollments (
    id SERIAL PRIMARY KEY,
    student_id INT NOT NULL REFERENCES students(id) ON DELETE CASCADE,
    course_id INT NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
    pack_id INT NOT NULL REFERENCES packs(id) ON DELETE CASCADE,
    UNIQUE (student_id, pack_id)
);
