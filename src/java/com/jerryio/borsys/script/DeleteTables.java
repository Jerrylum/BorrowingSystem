package com.jerryio.borsys.script;

import java.sql.Statement;
import java.sql.Connection;

import com.jerryio.borsys.Util;

public class DeleteTables {
    public static void main(String[] args) {
        try {
            Connection cnnct = Util.getConnection();
            Statement stmnt = cnnct.createStatement();


            cnnct.setAutoCommit(false);
            
            // order important
            stmnt.execute("drop table BorrowItem");
            stmnt.execute("drop table BorrowRecord");
            stmnt.execute("drop table Equipment");
            stmnt.execute("drop table TheUser");

            cnnct.commit();

            stmnt.close();
            cnnct.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Ok");
    }
}
