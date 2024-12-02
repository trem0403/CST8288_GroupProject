/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.AcademicProfessional;
/**
 *
 * @author Sancheaz
 */
public class ProfessionalProfileDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/AcademicDB";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Americ@2024";

    private static final String INSERT_PROFILE_SQL = "INSERT INTO AcademicProfessional (name, institutionID, academicPosition, currentPositionAtInstitution, educationBackground, areaOfExpertise) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_PROFILE_BY_ID = "SELECT * FROM AcademicProfessional WHERE id = ?";

    protected Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Note the updated driver class
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertProfile(AcademicProfessional profile) throws SQLException {
        try (Connection connection = getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROFILE_SQL)) {
            preparedStatement.setString(1, profile.getName());
            preparedStatement.setInt(2, profile.getInstitutionID());
            preparedStatement.setString(3, profile.getAcademicPosition());
            preparedStatement.setString(4, profile.getCurrentPositionAtInstitution());
            preparedStatement.setString(5, profile.getEducationBackground());
            preparedStatement.setString(6, profile.getAreaOfExpertise());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public AcademicProfessional selectProfile(int id) {
        AcademicProfessional profile = null;
        try (Connection connection = getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROFILE_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                // Retrieve data from result set and construct an AcademicProfessional object
                String name = rs.getString("name");
                int institutionID = rs.getInt("institutionID");
                String academicPosition = rs.getString("academicPosition");
                String currentPositionAtInstitution = rs.getString("currentPositionAtInstitution");
                String educationBackground = rs.getString("educationBackground");
                String areaOfExpertise = rs.getString("areaOfExpertise");
                profile = new AcademicProfessional(name, id, academicPosition, currentPositionAtInstitution, educationBackground, areaOfExpertise);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return profile;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}