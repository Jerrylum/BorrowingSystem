package com.jerryio.borsys.db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.jerryio.borsys.bean.BorrowRecord;
import com.jerryio.borsys.enums.BorrowType;
import com.jerryio.borsys.enums.RequestStatus;

public class BorrowRecordDB extends ObjectDB {
    private static ArrayList<BorrowRecord> allList = null;
    private static boolean isChanged = true;
    
    public BorrowRecordDB() {
        try {
            selectStmnt = cnnct.prepareStatement("SELECT * FROM BorrowRecord");
            insertStmnt = cnnct.prepareStatement("insert into BorrowRecord (user_id, status) values (?,?)", Statement.RETURN_GENERATED_KEYS);
            updateStmnt = cnnct.prepareStatement("update BorrowRecord set user_id=?,status=? where bor_id=?");
            deleteStmnt = cnnct.prepareStatement("delete from BorrowRecord where bor_id=?");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public ArrayList<BorrowRecord> getAllBorrowRecords() {
        if (isChanged)
            return queryAll();
        else
            return allList;
    }

    public BorrowRecord getBorrowRecord(int id) {
        for (BorrowRecord br : getAllBorrowRecords())
            if (br.getId() == id)
                return br;
        return null;
    }

    public ArrayList<BorrowRecord> queryAll() {
        allList = new ArrayList<BorrowRecord>();

        try {
            
            ResultSet rs = null;
            rs = selectStmnt.executeQuery();
            
            while (rs.next()) {
                BorrowRecord e = new BorrowRecord();
                e.setId(rs.getInt("bor_id"));
                e.setUserId(rs.getInt("user_id"));
                e.setStatus(RequestStatus.valueOf(rs.getString("status")));
                
                allList.add(e);
            }

            isChanged = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return allList;
    }

    public BorrowRecord add(int userId, RequestStatus status) {
        try {
            insertStmnt.setInt(1, userId);
            insertStmnt.setString(2, status.toString());

            if (insertStmnt.executeUpdate() != 0) {
                ResultSet rs = insertStmnt.getGeneratedKeys();
                rs.next();            
            
                BorrowRecord br = new BorrowRecord();
                br.setId(rs.getInt(1));
                br.setUserId(userId);
                br.setStatus(status);
    
                isChanged = true;

                return br;
            } else {
                return null;
            }          

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean update(BorrowRecord br) {
        try {
            updateStmnt.setInt(1, br.getUserId());
            updateStmnt.setString(2, br.getStatus().toString());
            updateStmnt.setInt(3, br.getId());
            
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

    public boolean delete(BorrowRecord br) {
        return delete(br.getId());
    }

}
