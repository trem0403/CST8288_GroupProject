-- Insert normal users into the User table
INSERT INTO User (email, password, role) VALUES
('user1@harvard.edu', 'password123', 'AcademicInstitution'),
('user2@stanford.edu', 'password123', 'AcademicInstitution'),
('user3@utoronto.ca', 'password123', 'AcademicInstitution'),
('user4@mit.edu', 'password123', 'AcademicInstitution'),
('user5@cambridge.ac.uk', 'password123', 'AcademicInstitution'),
('user6@oxford.ac.uk', 'password123', 'AcademicInstitution'),
('user7@yale.edu', 'password123', 'AcademicInstitution'),
('user8@algonquincollege.ca', 'password123', 'AcademicInstitution');

-- Insert academic institution admin users into AcademicInstitution table
INSERT INTO AcademicInstitution (institutionID, institutionNameID) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8);