package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Utility class for managing database connections using HikariCP connection pooling.
 * This class sets up HikariCP configuration and provides methods for retrieving database connections.
 * 
 * @author Ethan Tremblay
 */
public class DatabaseConnectionUtil {

    // DataSource instance for HikariCP connection pool
    static HikariDataSource dataSource;

    // Static block to initialize the connection pool
    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName("com.mysql.cj.jdbc.Driver"); // MySQL driver
            config.setJdbcUrl("jdbc:mysql://localhost:3306/aep"); // URL of your database
            config.setUsername("root"); // Your database username
            config.setPassword("root"); // Your database password
            config.setMaximumPoolSize(10); // Maximum pool size
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("HikariCP initialization failed: " + e.getMessage());
        }
    }

    /**
     * Gets a connection from the HikariCP connection pool.
     *
     * @return A connection object from the pool.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Closes the DataSource when the application shuts down.
     */
    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
