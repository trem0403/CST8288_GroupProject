-- Use the aep database
USE aep;

-- Disable foreign key checks to simplify dropping tables
SET FOREIGN_KEY_CHECKS = 0;

-- Drop tables in the correct order
DROP TABLE IF EXISTS RequestToTeach;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS Term;
DROP TABLE IF EXISTS AcademicProfessional;
DROP TABLE IF EXISTS AcademicInstitution;
DROP TABLE IF EXISTS InstitutionName;
DROP TABLE IF EXISTS Address;
DROP TABLE IF EXISTS User;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;
