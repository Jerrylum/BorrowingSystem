package com.jerryio.borsys;

import com.jerryio.borsys.bean.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Util {

    private static final String url = "jdbc:derby://localhost:1527/BorSys_DB";
    private static final String username = "dbroot";
    private static final String password = "KiEoF4Gt4mJmWkjqqyGnNb2rYjzLMA";

    private static Connection conn = null;

    public static Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "org.apache.derby.jdbc.ClientDriver");

        if (conn == null || !conn.isValid(50) || conn.isClosed()) { // isValid performance bad
            debug("Util: Create connection");
            return (conn = DriverManager.getConnection(url, username, password));
        } else {
            return conn;
        }
    }

    public static void debug(String str) {
        System.out.println("Debug: " + str);
    }

    public static User getMe(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            return (User) session.getAttribute("me");
        }

        return null;
    }

    public static void forward(ServletContext context, HttpServletRequest request, HttpServletResponse response, String url) {
        try {
            RequestDispatcher rd = context.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception ex) {
            Util.debug("Failed to forward to: " + url);
        }
    }

    public static void redirect(HttpServletRequest request, HttpServletResponse response, String url) {
        try {
            response.sendRedirect(request.getContextPath() + url);
        } catch (Exception ex) {
            Util.debug("Failed to redirect to: " + url);
        }
    }
}
