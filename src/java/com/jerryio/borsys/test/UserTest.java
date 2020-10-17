package com.jerryio.borsys.test;

import java.util.ArrayList;

import com.jerryio.borsys.bean.User;
import com.jerryio.borsys.db.UserDB;
import com.jerryio.borsys.enums.RoleType;
import com.jerryio.borsys.factory.ObjectDBFactory;

public class UserTest {
    public static UserDB db = ObjectDBFactory.getUserDB();

    public static void main(String[] args) {
        showAll();

        User u = db.add("user1", "123456", RoleType.STUDENT);

        showAll();

        u.setName("user 1 changed");
        u.setRole(RoleType.ADMIN);

        db.update(u);

        showAll();

        db.delete(u);

        showAll();
    }

    public static void showAll() {
        ArrayList<User> list = db.getAllUsers();

        System.out.println("Size: " + list.size());

        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            System.out.println(u.getId() + ": " + u.getName() + " " + u.getRole());
        }

    }
}
