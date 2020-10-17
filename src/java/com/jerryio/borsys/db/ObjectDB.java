package com.jerryio.borsys.db;

import java.sql.Connection;

import com.jerryio.borsys.Util;

public abstract class ObjectDB {
    
    protected Connection cnnct = null;

    public ObjectDB() {
        try {
            cnnct = Util.getConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void finalize() throws Throwable {        
        if (cnnct != null)
            cnnct.close();
    }

}
