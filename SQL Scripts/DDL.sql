-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS aep;

-- Use the aep database
USE aep;

-- User Table
CREATE TABLE User (
  userID INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role ENUM('AcademicProfessional', 'AcademicInstitution') NOT NULL
);

-- InstitutionNames Table
CREATE TABLE InstitutionNames (
  institutionNamesID INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

-- Address Table
CREATE TABLE Address (
  zip VARCHAR(20) PRIMARY KEY,
  Country VARCHAR(255) NOT NULL,
  state VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  street VARCHAR(255) NOT NULL
);

-- AcademicInstitution Table
CREATE TABLE AcademicInstitution (
  institutionID INT PRIMARY KEY,
  institutionNameID INT NOT NULL,
  zip VARCHAR(20) UNIQUE,
  FOREIGN KEY (institutionID) REFERENCES User(userID)
   ON DELETE CASCADE,
  FOREIGN KEY (institutionNameID) REFERENCES InstitutionNames(institutionNamesID)
    ON DELETE CASCADE,
  FOREIGN KEY (zip) REFERENCES Address(zip)
    ON DELETE CASCADE
);

-- AcademicProfessional Table
CREATE TABLE AcademicProfessional (
  professionalID INT PRIMARY KEY,
  Name VARCHAR(255) NOT NULL,
  institutionID INT NOT NULL,
  academicPosition VARCHAR(255) NOT NULL,
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
  Title VARCHAR(255) NOT NULL,
  code VARCHAR(50) NOT NULL,
  TermID INT NOT NULL,
  outline TEXT NOT NULL,
  Schedule ENUM('Morning', 'Afternoon', 'Evening') NOT NULL,
  DeliveryMethod ENUM('In-Person', 'Remote', 'Hybrid') NOT NULL,
  PreferredQualifications TEXT NOT NULL,
  Compensation DOUBLE NOT NULL,
  FOREIGN KEY (institutionID) REFERENCES AcademicInstitution(institutionID)
    ON DELETE CASCADE,
  FOREIGN KEY (TermID) REFERENCES Term(termID)
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