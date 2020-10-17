package com.jerryio.borsys.test;

import java.util.ArrayList;

import com.jerryio.borsys.bean.User;
import com.jerryio.borsys.db.UserDB;
import com.jerryio.borsys.enums.RoleType;

public class UserTest {
    public static void main(String[] args) {
        showAll();
        
        User u = UserDB.add("user1", "123456", RoleType.STUDENT);

        showAll();
        
        u.setName("user 1 changed");
        u.setRole(RoleType.ADMIN);
        
        UserDB.update(u);

        showAll();

        UserDB.delete(u);

        showAll();
    }

    public static void showAll() {
        ArrayList<User> list = UserDB.getAllUsers();

        System.out.println("Size: " + list.size());

        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            System.out.println(u.getId() + ": " + u.getName() + " " +  u.getRole());
        }

    }
}
