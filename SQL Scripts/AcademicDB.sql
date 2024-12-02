CREATE DATABASE IF NOT EXISTS AcademicDB;

USE AcademicDB;

CREATE TABLE AcademicProfessional (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    currentPosition VARCHAR(100),
    currentInstitution VARCHAR(100),
    educationBackground TEXT,
    areaOfExpertise VARCHAR(255)
);