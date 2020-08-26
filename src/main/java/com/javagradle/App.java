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

    public void updateRecord(Connection connection) throws SQLException {

    }
}
