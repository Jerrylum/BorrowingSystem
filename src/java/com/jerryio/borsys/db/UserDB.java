package com.jerryio.borsys.db;

import java.sql.*;
import java.util.ArrayList;

import com.jerryio.borsys.bean.User;
import com.jerryio.borsys.enums.RoleType;

public class UserDB extends ObjectDB {
    private static ArrayList<User> allList = null;
    private static boolean isChanged = true;

    private PreparedStatement selectStmnt;
    private PreparedStatement insertStmnt;
    private PreparedStatement updateStmnt;
    private PreparedStatement deleteStmnt;

    public UserDB() {
        try {
            selectStmnt = cnnct.prepareStatement("SELECT * FROM TheUser");
            insertStmnt = cnnct.prepareStatement("insert into TheUser (name, pwd, role) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            updateStmnt = cnnct.prepareStatement("update TheUser set name=?,pwd=?,role=? where user_id=?");
            deleteStmnt = cnnct.prepareStatement("delete from TheUser where user_id=?");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<User> getAllUsers() {
        if (isChanged)
            return queryAll();
        else
            return allList;
    }

    public User getUser(int id) {
        for (User u : getAllUsers())
            if (u.getId() == id)
                return u;
        return null;
    }

    public ArrayList<User> queryAll() {
        allList = new ArrayList<User>();

        try {
            ResultSet rs = null;
            rs = selectStmnt.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("user_id"));
                u.setName(rs.getString("name"));
                u.setPwd(rs.getString("pwd"));
                u.setRole(RoleType.valueOf(rs.getString("role")));

                allList.add(u);
            }
            isChanged = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return allList;
    }

    public User add(String name, String pwd, RoleType role) {

        try {
            insertStmnt.setString(1, name);
            insertStmnt.setString(2, pwd);
            insertStmnt.setString(3, role.toString());

            if (insertStmnt.executeUpdate() != 0) {
                ResultSet rs = insertStmnt.getGeneratedKeys();
                rs.next();

                User u = new User();
                u.setId(rs.getInt(1));
                u.setName(name);
                u.setPwd(pwd);
                u.setRole(role);

                isChanged = true;

                return u;
            } else {
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean update(User u) {
        try {
            updateStmnt.setString(1, u.getName());
            updateStmnt.setString(2, u.getPwd());
            updateStmnt.setString(3, u.getRole().toString());
            updateStmnt.setInt(4, u.getId());

            return updateStmnt.executeUpdate() != 0 && (isChanged = true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id) {
        try {
            deleteStmnt.setInt(1, id);

            return deleteStmnt.executeUpdate() != 0 && (isChanged = true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean delete(User u) {
        return delete(u.getId());
    }

}
