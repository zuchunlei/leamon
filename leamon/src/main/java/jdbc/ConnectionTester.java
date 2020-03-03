package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * JDBC Connection 关闭以后，会被回收掉。
 */
public class ConnectionTester {

    public static void main(String[] args) throws Exception {
        String driverName = "";
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "root";

        Class.forName(driverName);
        Connection connection = DriverManager.getConnection(url, username, password);
        // handle some things via this connection
        connection.close();
    }

}
