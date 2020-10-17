package com.jerryio.borsys;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:derby://localhost:1527/BorSys_DB";
    private static final String username = "dbroot";
    private static final String password = "KiEoF4Gt4mJmWkjqqyGnNb2rYjzLMA";

    private static Connection conn = null;

    public static Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "org.apache.derby.jdbc.ClientDriver");
        if (conn == null || !conn.isValid(100) || conn.isClosed())
            return (conn = DriverManager.getConnection(url, username, password));
        else
            return conn;
    }
}
