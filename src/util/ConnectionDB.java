package util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/research_student_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "000000";

    public static Connection openConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection conn, CallableStatement callSt){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw  new RuntimeException(e);
            }
        }
    }
}
