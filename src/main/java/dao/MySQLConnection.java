package dao;

import java.sql.*;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/tmdt"; // Thay your_database bằng tên database của bạn
    private static final String USER = "root"; // Thay root bằng username MySQL của bạn
    private static final String PASSWORD = "12345"; // Thay password bằng mật khẩu MySQL của bạn

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
