package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {

    private static final String url = "jdbc:mysql://localhost:3306/trivia_db?useSSL=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String pass = "usbw";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(url,user,pass);
    }
}