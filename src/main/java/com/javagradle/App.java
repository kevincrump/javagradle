package com.javagradle;
import org.h2.jdbcx.JdbcDataSource;
import java.sql.*;

public class App {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE test (id INTEGER not NULL, message VARCHAR(255))";
    private static final String INSERT_TEST_SQL = "INSERT INTO test (id, message) VALUES (?, ?);";
    private static final String SELECT_QUERY = "select id,message from test";
    private static final String UPDATE_TEST_SQL = "update test set message = ? where id = ?;";
    private static final String DELETE_TEST_SQL = "delete from test where id = ?";

    private static Connection connection = H2JDBCUtils.getConnection();

    public static void main(String[] args) throws SQLException{
        App app = new App();

        app.createTable();
        app.insertRecord();
        app.selectRecords();
        app.updateRecord(1);
        app.selectRecords();
        app.deleteRecord(1);

        app.selectRecords();
    }

    public void createTable() throws SQLException {

        System.out.println(CREATE_TABLE_SQL);
        try (Statement statement = connection.createStatement();) {

            statement.execute(CREATE_TABLE_SQL);

        } catch (SQLException e) {
            System.out.println("error create");
            System.out.println(e.getMessage());
        }
    }

    public void insertRecord() throws SQLException {
        System.out.println(INSERT_TEST_SQL);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TEST_SQL)) {

            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Hello World from DB!!");

            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error insert");
            System.out.println(e.getMessage());
        }
    }

    public void selectRecords() throws  SQLException {
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

    public void updateRecord(Integer id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TEST_SQL)) {
            preparedStatement.setString(1, "Updated Hello World");
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error update");
            System.out.println(e.getMessage());
        }
    }

    public void deleteRecord(Integer id) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TEST_SQL)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("error update");
            System.out.println(e.getMessage());
        }
    }
}
