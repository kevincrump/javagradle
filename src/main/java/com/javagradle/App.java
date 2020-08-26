package com.javagradle;
import org.h2.jdbcx.JdbcDataSource;
import java.sql.*;

public class App {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE test (id INTEGER not NULL, message VARCHAR(255))";
    private static final String INSERT_TEST_SQL = "INSERT INTO test (id, message) VALUES (?, ?);";
    private static final String SELECT_QUERY = "select id,message from test";
    private static final String UPDATE_USERS_SQL = "update test set message = ?;";

    public static void main(String[] args) throws SQLException{
        Connection connection = H2JDBCUtils.getConnection();

        App app = new App();
        app.createTable(connection);

        app.insertRecord(connection);

        app.selectRecords(connection);

//        System.out.println("Hello Java World 2!");
//        String url = "jdbc:h2:mem:";
//        Statement stmt = null;
//// ...
//        JdbcDataSource ds = new JdbcDataSource();
//        ds.setURL(url);
//        try {  Connection conn = ds.getConnection();
//            System.out.println("connection");
//            stmt = conn.createStatement();
//
//
//            stmt.executeUpdate(CREATE_TABLE_SQL);
//            System.out.println("create");
//            stmt.executeUpdate(INSERT_TEST_SQL);
//            System.out.println("insert");
//
//            stmt = conn.createStatement();
//
//            ResultSet rs = stmt.executeQuery(SELECT_QUERY);
//            System.out.println(rs);
//            System.out.println("rs");
//            while( rs.next() )
//            {
//
//                String name = rs.getString("message");
//                System.out.println( name );
//            }
//            stmt.close();
//            conn.close();
//
//        } catch (SQLException ex) {
//            System.out.println("error");
//            System.out.println(ex.getMessage());
//            //Logger lgr = Logger.getLogger(JavaSeH2Memory.class.getName());
//            //gr.log(Level.SEVERE, ex.getMessage(), ex);
//        }
    }

    public void createTable(Connection connection) throws SQLException {

        System.out.println(CREATE_TABLE_SQL);
        try (Statement statement = connection.createStatement();) {

            statement.execute(CREATE_TABLE_SQL);

        } catch (SQLException e) {
            System.out.println("error create");
            System.out.println(e.getMessage());
        }
    }

    public void insertRecord(Connection connection) throws SQLException {
        System.out.println(INSERT_TEST_SQL);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TEST_SQL)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Hello World from DB!");

            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error insert");
            System.out.println(e.getMessage());
        }
    }

    public void selectRecords(Connection connection) throws  SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int testId = rs.getInt("id");
                String message = rs.getString("message");
                System.out.println(testId + "," + message);
            }
        } catch (SQLException e) {
            System.out.println("error select");
            System.out.println(e.getMessage());
        }
    }

//    public void updateRecord(Connection connection) throws SQLException {
//        System.out.println(UPDATE_USERS_SQL);
//        // Step 1: Establishing a Connection
//        try (Connection connection = H2JDBCUtils.getConnection();
//             // Step 2:Create a statement using connection object
//             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
//            preparedStatement.setString(1, "Ram");
//            preparedStatement.setInt(2, 1);
//
//            // Step 3: Execute the query or update query
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//
//            // print SQL exception information
//            H2JDBCUtils.printSQLException(e);
//        }
//
//    }
}
