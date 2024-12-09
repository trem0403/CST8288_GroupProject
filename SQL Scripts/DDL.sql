-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS aep;

-- Use the aep database
USE aep;

-- Create all required tables

-- User Table
CREATE TABLE User (
  userID INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role ENUM('AcademicProfessional', 'AcademicInstitution') NOT NULL
);

-- InstitutionNames Table
CREATE TABLE InstitutionName (
  institutionNameID INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

-- Address Table
CREATE TABLE Address (
  zip INT PRIMARY KEY,
  country VARCHAR(255) NOT NULL,
  state VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  street VARCHAR(255) NOT NULL
);

-- AcademicInstitution Table
CREATE TABLE AcademicInstitution (
  institutionID INT PRIMARY KEY,
  institutionNameID INT NOT NULL,
  zip INT,
  FOREIGN KEY (institutionID) REFERENCES User(userID)
   ON DELETE CASCADE,
  FOREIGN KEY (institutionNameID) REFERENCES InstitutionName(institutionNameID)
    ON DELETE CASCADE,
  FOREIGN KEY (zip) REFERENCES Address(zip)
    ON DELETE CASCADE
);

-- AcademicProfessional Table
CREATE TABLE AcademicProfessional (
  professionalID INT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  institutionID INT NOT NULL,
  academicPosition VARCHAR(255) NOT NULL,
  currentPositionAtInstitution VARCHAR(255),
  educationBackground TEXT,
  areaOfExpertise TEXT,
  FOREIGN KEY (professionalID) REFERENCES User(userID)
	ON DELETE CASCADE,
  FOREIGN KEY (institutionID) REFERENCES AcademicInstitution(institutionID)
);

-- Term Table
CREATE TABLE Term (
  termID INT AUTO_INCREMENT PRIMARY KEY,
  term VARCHAR(50) NOT NULL
);

-- Course Table
CREATE TABLE Course (
  courseID INT AUTO_INCREMENT PRIMARY KEY,
  institutionID INT NOT NULL,
  title VARCHAR(255) NOT NULL,
  code VARCHAR(50) NOT NULL,
  termID INT NOT NULL,
  outline TEXT NOT NULL,
  schedule ENUM('Morning', 'Afternoon', 'Evening') NOT NULL,
  deliveryMethod ENUM('In-Person', 'Remote', 'Hybrid') NOT NULL,
  preferredQualifications TEXT NOT NULL,
  compensation DOUBLE NOT NULL,
  FOREIGN KEY (institutionID) REFERENCES AcademicInstitution(institutionID)
    ON DELETE CASCADE,
  FOREIGN KEY (termID) REFERENCES Term(termID)
);

-- RequestToTeach Table
CREATE TABLE RequestToTeach (
  requestToTeachID INT AUTO_INCREMENT PRIMARY KEY,
  professionalID INT NOT NULL,
  courseID INT NOT NULL,
  requestDate DATE NOT NULL,
  status ENUM('Pending', 'Accepted', 'Rejected') DEFAULT 'Pending' NOT NULL,
  notificationMessage VARCHAR(255),
  notificationDate DATETIME,
  FOREIGN KEY (professionalID) REFERENCES AcademicProfessional(professionalID)
    ON DELETE CASCADE,
  FOREIGN KEY (courseID) REFERENCES Course(courseID)
    ON DELETE CASCADE
);

-- Insert required data into certain tables

-- Insert into InstitutionName
INSERT INTO InstitutionName (name) VALUES 
('Harvard University'),
('Stanford University'),
('University of Toronto'),
('MIT'),
('Cambridge University'),
('Oxford University'),
('Yale University'),
("Algonquin College"),
("Carlenton University");

-- Insert into Course
INSERT INTO Course (institutionID, title, code, termID, outline, schedule, deliveryMethod, preferredQualifications, compensation)
VALUES
(1, 'Introduction to Computer Science', 'CS101', 1, 'Fundamental concepts of computer science and programming.', 'Morning', 'In-Person', 'Bachelor’s degree in Computer Science.', 5000.00);

INSERT INTO Course (institutionID, title, code, termID, outline, schedule, deliveryMethod, preferredQualifications, compensation)
VALUES
(1, 'Advanced Mathematics', 'MATH201', 2, 'In-depth study of calculus and linear algebra.', 'Afternoon', 'Remote', 'Master’s degree in Mathematics.', 6000.00);

INSERT INTO Course (institutionID, title, code, termID, outline, schedule, deliveryMethod, preferredQualifications, compensation)
VALUES
(1, 'Database Systems', 'DBS301', 1, 'Comprehensive course on relational databases and SQL.', 'Evening', 'Hybrid', 'Bachelor’s degree in Computer Science.', 5500.00);

INSERT INTO Course (institutionID, title, code, termID, outline, schedule, deliveryMethod, preferredQualifications, compensation)
VALUES
(1, 'Artificial Intelligence Basics', 'AI101', 3, 'Introduction to AI concepts and machine learning.', 'Morning', 'Remote', 'Master’s degree in AI or related field.', 7000.00);

INSERT INTO Course (institutionID, title, code, termID, outline, schedule, deliveryMethod, preferredQualifications, compensation)
VALUES
(1, 'Physics for Engineers', 'PHY205', 2, 'Fundamentals of mechanics, thermodynamics, and electromagnetism.', 'Afternoon', 'In-Person', 'Bachelor’s degree in Physics or Engineering.', 5200.00);

-- Insert into Term
INSERT INTO Term (term)
VALUES 
('Fall 2024');

INSERT INTO Term (term)
VALUES 
('Winter 2025');

INSERT INTO Term (term)
VALUES 
('Spring 2025');

INSERT INTO Term (term)
VALUES 
('Summer 2025');

INSERT INTO Term (term)
VALUES 
('Fall 2025');




