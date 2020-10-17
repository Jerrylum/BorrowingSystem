package com.jerryio.borsys.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.jerryio.borsys.Util;

public abstract class ObjectDB {
    protected Connection cnnct = null;
    
    protected PreparedStatement selectStmnt;
    protected PreparedStatement insertStmnt;
    protected PreparedStatement updateStmnt;
    protected PreparedStatement deleteStmnt;

    public ObjectDB() {
        try {
            cnnct = Util.getConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // @Override
    // public void finalize() throws Throwable {        
    //     if (cnnct != null)
    //         cnnct.close();
    // }

}
