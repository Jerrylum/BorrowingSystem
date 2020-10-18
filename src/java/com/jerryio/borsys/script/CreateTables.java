package com.jerryio.borsys.script;

import java.io.File;
import java.sql.*;
import java.util.Scanner;

import com.jerryio.borsys.Util;

public class CreateTables {

    public static void main(String[] args) {
        try {
            Connection cnnct = Util.getConnection();
            Statement stmnt = cnnct.createStatement();

            ////////////////////////////////////////////////

            File myObj = new File("./init.sql");
            Scanner myReader = new Scanner(myObj);

            String sql = "";
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
                sql += data + '\n';
            }
            myReader.close();

            ////////////////////////////////////////////////

            cnnct.setAutoCommit(false);

            for (String s : sql.split(";")) {
                stmnt.execute(s);
            }

            cnnct.commit();

            ////////////////////////////////////////////////

            stmnt.close();
            cnnct.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Ok");
    }
}
