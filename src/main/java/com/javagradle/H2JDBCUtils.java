package com.javagradle;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class H2JDBCUtils {

    private static String jdbcURL = "jdbc:h2:mem:";

    public static Connection getConnection() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(jdbcURL);
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
