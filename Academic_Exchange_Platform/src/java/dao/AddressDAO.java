package dao;

import model.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing Address data in the database.
 * Implements CRUD operations as defined in the GenericDAO interface.
 * 
 * Uses DatabaseConnectionUtil to manage database connections.
 * 
 * @author Ethan Tremblay
 */
public class AddressDAO implements GenericDAO<Address> {

    /**
     * Creates a new Address entry in the database.
     *
     * @param address The Address object containing data to be inserted.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void create(Address address) throws SQLException {
        String insertSQL = "INSERT INTO Address (zip, country, state, city, street) VALUES (?, ?, ?, ?, ?)";

        try (var conn = DatabaseConnectionUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

            stmt.setInt(1, address.getZip());
            stmt.setString(2, address.getCountry());
            stmt.setString(3, address.getState());
            stmt.setString(4, address.getCity());
            stmt.setString(5, address.getStreet());

            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves an Address entry by its ZIP code.
     *
     * @param zip The ZIP code of the Address to retrieve.
     * @return The Address object, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public Address getByID(int zip) throws SQLException {
        String selectSQL = "SELECT * FROM Address WHERE zip = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {

            stmt.setInt(1, zip);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Address(
                        rs.getInt("zip"),
                        rs.getString("country"),
                        rs.getString("state"),
                        rs.getString("city"),
                        rs.getString("street")
                );
            }
            return null;
        }
    }

    /**
     * Retrieves all Address entries from the database.
     *
     * @return A list of Address objects.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<Address> getAll() throws SQLException {
        List<Address> addressList = new ArrayList<>();
        String selectAllSQL = "SELECT zip FROM Address ORDER BY zip";

        try (Connection conn = DatabaseConnectionUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(selectAllSQL); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int zip = rs.getInt("zip");
                addressList.add(getByID(zip));
            }
        }
        return addressList;
    }

    /**
     * Updates an existing Address entry in the database.
     *
     * @param address The Address object containing updated data.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void update(Address address) throws SQLException {
        String updateSQL = "UPDATE Address SET country = ?, state = ?, city = ?, street = ? WHERE zip = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

            stmt.setString(1, address.getCountry());
            stmt.setString(2, address.getState());
            stmt.setString(3, address.getCity());
            stmt.setString(4, address.getStreet());
            stmt.setInt(5, address.getZip());

            stmt.executeUpdate();
        }
    }
} // end of class
