package dao;

import model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing Course data in the database. Implements
 * CRUD operations as defined in the GenericDAO interface.
 *
 * Uses DatabaseConnectionUtil to manage database connections.
 *
 * @author Ethan Tremblay
 */
public class CourseDAO implements GenericDAO<Course> {

    /**
     * Creates a new Course entry in the database.
     *
     * @param course The Course object containing data to be inserted.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void create(Course course) throws SQLException {
        String insertSQL = "INSERT INTO Course (institutionID, title, code, termID, outline, schedule, deliveryMethod, preferredQualifications, compensation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (var conn = DatabaseConnectionUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, course.getInstitutionID());
            stmt.setString(2, course.getTitle());
            stmt.setString(3, course.getCode());
            stmt.setInt(4, course.getTermID());
            stmt.setString(5, course.getOutline());
            stmt.setString(6, course.getSchedule());
            stmt.setString(7, course.getDeliveryMethod());
            stmt.setString(8, course.getPreferredQualifications());
            stmt.setDouble(9, course.getCompensation());

            stmt.executeUpdate();

            // Retrieve the generated courseID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                course.setCourseID(generatedKeys.getInt(1));
            }
        }
    }

    /**
     * Retrieves an Course entry by its ID.
     *
     * @param courseID The ID of the Course to retrieve.
     * @return The Course object, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public Course getByID(int courseID) throws SQLException {
        String selectSQL = "SELECT * FROM Course WHERE courseID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(selectSQL)) {

            stmt.setInt(1, courseID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Course(
                        rs.getInt("courseID"),
                        rs.getInt("InstitutionID"),
                        rs.getString("title"),
                        rs.getString("code"),
                        rs.getInt("termID"),
                        rs.getString("outline"),
                        rs.getString("schedule"),
                        rs.getString("deliveryMethod"),
                        rs.getString("preferredQualifications"),
                        rs.getDouble("compensation")
                );
            }
            return null;
        }
    }

    /**
     * Retrieves all Course entries from the database.
     *
     * @return A list of Course objects.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<Course> getAll() throws SQLException {
        List<Course> courseList = new ArrayList<>();
        String selectAllSQL = "SELECT courseID "
                + "FROM Course ORDER BY courseID";

        try (
                Connection conn = DatabaseConnectionUtil.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(selectAllSQL)) {

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int courseID = rs.getInt("courseID");
                    
                    courseList.add(getByID(courseID));
                }
            }
        }
        return courseList;
    }

    /**
     * Updates an existing Course entry in the database.
     *
     * @param course The Course object containing updated
     * data.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void update(Course course) throws SQLException {
        String updateSQL = "UPDATE Course SET InstitutionID = ?, title = ?, code = ?, termID = ?, outline = ?, schedule = ?, deliveryMethod = ?, preferredQualifications = ?, compensation = ?  WHERE courseiD = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
            
            stmt.setInt(1, course.getInstitutionID());
            stmt.setString(2, course.getTitle());
            stmt.setString(3, course.getCode());
            stmt.setInt(4, course.getTermID());
            stmt.setString(5, course.getOutline());
            stmt.setString(6, course.getSchedule());
            stmt.setString(7, course.getDeliveryMethod());
            stmt.setString(8, course.getPreferredQualifications());
            stmt.setDouble(9, course.getCompensation());
            stmt.setInt(10, course.getCourseID());


            stmt.executeUpdate();
        }
    }
    
    public static List<Course> searchCourses(String title, String code, int termID, int institutionID) throws SQLException {
        List<Course> courses = new ArrayList<>();
        String searchSQL = "SELECT * FROM Course WHERE title LIKE ? AND code LIKE ? AND termID = ? AND institutionID = ? ORDER BY title";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(searchSQL)) {

            stmt.setString(1, "%" + title + "%"); // Matches courses with similar titles
            stmt.setString(2, "%" + code + "%");  // Matches courses with similar codes
            stmt.setInt(3, termID);               // Filters by term
            stmt.setInt(4, institutionID);        // Filters by institution

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(new Course(
                        rs.getInt("courseID"),
                        rs.getInt("institutionID"),
                        rs.getString("title"),
                        rs.getString("code"),
                        rs.getInt("termID"),
                        rs.getString("outline"),
                        rs.getString("schedule"),
                        rs.getString("deliveryMethod"),
                        rs.getString("preferredQualifications"),
                        rs.getDouble("compensation")
                    ));
                }
            }
        }
        return courses;
    }

} // end of class
