package com.javagradle;
import org.h2.jdbcx.JdbcDataSource;
import java.sql.*;

public class App {
    public static void main(String[] args){
        System.out.println("Hello Java World 2!");
        String url = "jdbc:h2:mem:";
        Statement stmt = null;
// ...
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(url);
        try {  Connection conn = ds.getConnection();
            System.out.println("connection");
            stmt = conn.createStatement();

            String sql = "CREATE TABLE test " +
                    "(testid INTEGER not NULL, " +
                    " message VARCHAR(255))";

            stmt.executeUpdate(sql);
            System.out.println("create");
            stmt.executeUpdate("INSERT INTO test (testid, message) VALUES ( 1,'Hello World from DB!' )" );
            System.out.println("insert");

            stmt = conn.createStatement();
            sql="select testid,message from test";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs);
            System.out.println("rs");
            while( rs.next() )
            {

                String name = rs.getString("message");
                System.out.println( name );
            }
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("error");
            System.out.println(ex.getMessage());
            //Logger lgr = Logger.getLogger(JavaSeH2Memory.class.getName());
            //gr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
