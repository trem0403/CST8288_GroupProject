package dao;

import dao.DatabaseConnectionUtil;
import dao.GenericDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.AcademicProfessional;
import enums.Role;

/**
 * Data Access Object (DAO) for managing AcademicProfessional data in the database.
 * Implements CRUD operations as defined in the GenericDAO interface.
 * 
 * @author Ethan Tremblay
 */
public class AcademicProfessionalDAO implements GenericDAO<AcademicProfessional> {

    /**
     * Creates a new AcademicProfessional in the database.
     *
     * @param professional The AcademicProfessional object containing data to be inserted.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void create(AcademicProfessional professional) throws SQLException {
        String insertUserSQL = "INSERT INTO User (email, password, role) VALUES (?, ?, ?)";
        String insertProfessionalSQL = "INSERT INTO AcademicProfessional (professionalID, name, institutionID, academicPosition) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement insertUserStmt = conn.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertProfessionalStmt = conn.prepareStatement(insertProfessionalSQL)) {

            // Insert into User table
            insertUserStmt.setString(1, professional.getEmail());
            insertUserStmt.setString(2, professional.getPassword());
            insertUserStmt.setString(3, professional.getRole());
            insertUserStmt.executeUpdate();

            // Get the generated userID
            ResultSet generatedKeys = insertUserStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                professional.setUserID(generatedKeys.getInt(1));

                // Insert into AcademicProfessional table
                insertProfessionalStmt.setInt(1, professional.getUserID());
                insertProfessionalStmt.setString(2, professional.getName());
                insertProfessionalStmt.setInt(3, professional.getInstitutionID());
                insertProfessionalStmt.setString(4, professional.getAcademicPosition());
                insertProfessionalStmt.executeUpdate();
            }
        }
    }

    /**
     * Retrieves an AcademicProfessional by userID.
     *
     * @param  professionalID The id of the professional
     * @return The AcademicProfessional object.
     * @throws SQLException   If a database access error occurs.
     */
    @Override
    public AcademicProfessional getByID(int professionalID) throws SQLException {
        String getProfessionalSQL = "SELECT * FROM AcademicProfessional WHERE professionalID = ?";
        String getUserSQL = "SELECT * FROM User WHERE userID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement getProfessionalStmt = conn.prepareStatement(getProfessionalSQL);
             PreparedStatement getUserStmt = conn.prepareStatement(getUserSQL)) {

            // Get the academic professional
            getProfessionalStmt.setInt(1, professionalID);
            ResultSet professionalRS = getProfessionalStmt.executeQuery();

            if (professionalRS.next()) {
                int userID = professionalRS.getInt("professionalID");

                getUserStmt.setInt(1, userID);
                ResultSet userRS = getUserStmt.executeQuery();

                if (userRS.next()) {
                    return new AcademicProfessional(
                        userID,
                        userRS.getString("email"),
                        userRS.getString("password"),
                        userRS.getString("role"),
                        professionalRS.getString("name"),
                        professionalRS.getInt("institutionID"),
                        professionalRS.getString("academicPosition"),
                        professionalRS.getString("currentPositionAtInstitution"),
                        professionalRS.getString("educationBackground"),
                        professionalRS.getString("areaOfExpertise")
                    );
                }
            }
            return null;
        }
    }

    /**
     * Retrieves all AcademicProfessional records.
     *
     * @return A list of AcademicProfessional objects.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<AcademicProfessional> getAll() throws SQLException {
        List<AcademicProfessional> professionals = new ArrayList<>();
        String getAllProfessionalsSQL = "SELECT professionalID, name, institutionID, academicPosition, currentPositionAtInstitution, educationBackground, areaOfExpertise" +
                                        "FROM AcademicProfessional ORDER BY professionalID"; 
        
        try (
            Connection conn = DatabaseConnectionUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(getAllProfessionalsSQL)) {
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int professionalID = rs.getInt("professionalID");
                    String name = rs.getString("name");
                    int institutionID = rs.getInt("institutionID");
                    String academicPosition = rs.getString("academicPosition");
                    String educationBackground = rs.getString("educationBackground");
                    String currentPositionAtInstitution = rs.getString("currentPositionAtInstitution");
                    String areaOfExpertise = rs.getString("areaOfExpertise");
                    
                    professionals.add(new AcademicProfessional(professionalID, name, institutionID, academicPosition, currentPositionAtInstitution, educationBackground, areaOfExpertise));
                }
            }
        }
        return professionals;
    }

    /**
     * Updates an AcademicProfessional record.
     *
     * @param professional The AcademicProfessional object to update.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void update(AcademicProfessional professional) throws SQLException {
        String updateUserSQL = "UPDATE User SET email = ?, password = ?, role = ? WHERE userID = ?";
        String updateProfessionalSQL = "UPDATE AcademicProfessional SET name = ?, institutionID = ?, academicPosition = ?, currentPositionAtInstitution = ?, educationBackground = ?, areaOfExpertise = ? WHERE userID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement updateUserStmt = conn.prepareStatement(updateUserSQL);
             PreparedStatement updateProfessionalStmt = conn.prepareStatement(updateProfessionalSQL)) {

            updateUserStmt.setString(1, professional.getEmail());
            updateUserStmt.setString(2, professional.getPassword());
            updateUserStmt.setString(3, professional.getRole());
            updateUserStmt.setInt(4, professional.getUserID());
            updateUserStmt.executeUpdate();

            updateProfessionalStmt.setString(1, professional.getName());
            updateProfessionalStmt.setInt(2, professional.getInstitutionID());
            updateProfessionalStmt.setString(3, professional.getAcademicPosition());
            updateProfessionalStmt.setString(3, professional.getCurrentPositionAtInstitution());
            updateProfessionalStmt.setString(4, professional.getEducationBackground());
            updateProfessionalStmt.setString(5, professional.getAreaOfExpertise());
            updateProfessionalStmt.setInt(6, professional.getUserID());
            updateProfessionalStmt.executeUpdate();
        }
    }
} // end of class
