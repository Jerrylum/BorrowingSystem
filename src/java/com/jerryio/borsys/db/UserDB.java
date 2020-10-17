package com.jerryio.borsys.db;

import java.sql.*;
import java.util.ArrayList;

import com.jerryio.borsys.Util;
import com.jerryio.borsys.bean.User;
import com.jerryio.borsys.enums.RoleType;

public class UserDB {
    private static ArrayList<User> allList = null;
    private static boolean isChanged = true;

    public static ArrayList<User> getAllUsers(){
        if (isChanged)
            return queryAll();
        else
            return allList;
    }

    public static User getUser(int id) {
        for (User u : getAllUsers())
            if (u.getId() == id)
                return u;
        return null;
    }

    public static ArrayList<User> queryAll() {
        allList = new ArrayList<User>();

        try {
            Connection cnnct = Util.getConnection();

            PreparedStatement pStmnt = cnnct.prepareStatement("SELECT * FROM TheUser");
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            
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
    
    public static User add(String name, String pwd, RoleType role) {

        try {
            Connection cnnct = Util.getConnection();

            PreparedStatement pStmnt = cnnct.prepareStatement("insert into TheUser (name, pwd, role) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pStmnt.setString(1, name);
            pStmnt.setString(2, pwd);
            pStmnt.setString(3, role.toString());

            if (pStmnt.executeUpdate() != 0) {
                ResultSet rs = pStmnt.getGeneratedKeys();
                rs.next();            
            
                User u = new User();
                u.setId(rs.getInt(1));
                u.setName(name);
                u.setPwd(pwd);
                u.setRole(role);
    
                isChanged = true;

                pStmnt.close();
                cnnct.close();

                return u;
            } else {
                pStmnt.close();
                cnnct.close();

                return null;
            }          

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean update(User u) {
        try {
            Connection cnnct = Util.getConnection();

            PreparedStatement pStmnt = cnnct.prepareStatement("update TheUser set name=?,pwd=?,role=? where user_id=?");
            pStmnt.setString(1, u.getName());
            pStmnt.setString(2, u.getPwd());
            pStmnt.setString(3, u.getRole().toString());
            pStmnt.setInt(4, u.getId());
            
            return pStmnt.executeUpdate() != 0 && (isChanged = true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean delete(int id) {
        try {
            Connection cnnct = Util.getConnection();

            PreparedStatement pStmnt = cnnct.prepareStatement("delete from TheUser where user_id=?");
            pStmnt.setInt(1, id);
            
            return pStmnt.executeUpdate() != 0 && (isChanged = true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean delete(User u) {
        return delete(u.getId());
    }

}
