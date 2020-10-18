package com.jerryio.borsys.db;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.jerryio.borsys.bean.BorrowItem;
import com.jerryio.borsys.enums.BorrowType;

public class BorrowItemDB extends ObjectDB {
    private static ArrayList<BorrowItem> allList = null;
    private static boolean isChanged = true;

    public BorrowItemDB() {
        try {
            selectStmnt = cnnct.prepareStatement("SELECT * FROM BorrowItem");
            insertStmnt = cnnct.prepareStatement("insert into BorrowItem (bor_id, eq_id, status, dfrom, dto) values (?,?,?,?,?)");
            updateStmnt = cnnct.prepareStatement("update BorrowItem set bor_id=?,eq_id=?,status=?,dfrom=?,dto=? where bor_id=? and eq_id=?");
            deleteStmnt = cnnct.prepareStatement("delete from BorrowItem where bor_id=? and eq_id=?");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public ArrayList<BorrowItem> getAllBorrowItems() {
        if (isChanged)
            return queryAll();
        else
            return allList;
    }

    public ArrayList<BorrowItem> getBorrowItemsList(int recordId) {
        ArrayList<BorrowItem> rtn = new ArrayList<BorrowItem>();

        for (BorrowItem i : getAllBorrowItems())
            if (i.getRecordId() == recordId)
                rtn.add(i);
        return rtn;
    }
    
    public ArrayList<BorrowItem> queryAll() {
        allList = new ArrayList<BorrowItem>();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            
            ResultSet rs = null;
            rs = selectStmnt.executeQuery();
            
            while (rs.next()) {
                BorrowItem bi = new BorrowItem();
                bi.setRecordId(rs.getInt("bor_id"));
                bi.setEquipmentId(rs.getInt("eq_id"));
                bi.setStatus(BorrowType.valueOf(rs.getString("status")));

                String dfrom = rs.getString("dfrom");
                bi.setFrom(dfrom.equals("") ? null : sdf.parse(dfrom + " 00:00:00"));

                String dto = rs.getString("dto");
                bi.setTo(dto.equals("") ? null : sdf.parse(dto + " 23:59:59"));
                
                allList.add(bi);
            }

            isChanged = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return allList;
    }
    
    public BorrowItem add(int recordId, int eqId, BorrowType status, Date from, Date to) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            insertStmnt.setInt(1, recordId);
            insertStmnt.setInt(2, eqId);
            insertStmnt.setString(3, status.toString());
            insertStmnt.setString(4, from == null ? "" : sdf.format(from));
            insertStmnt.setString(5, to == null ? "" : sdf.format(to));

            if (insertStmnt.executeUpdate() != 0) {
                BorrowItem bi = new BorrowItem();
                bi.setRecordId(recordId);
                bi.setEquipmentId(eqId);
                bi.setStatus(status);
                bi.setFrom(from);
                bi.setTo(to);
    
                isChanged = true;

                return bi;
            } else {
                return null;
            }          

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean update(BorrowItem bi) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            updateStmnt.setInt(1, bi.getRecordId());
            updateStmnt.setInt(2, bi.getEquipmentId());
            updateStmnt.setString(3, bi.getStatus().toString());
            updateStmnt.setString(4, bi.getFrom() == null ? "" : sdf.format(bi.getFrom()));
            updateStmnt.setString(5, bi.getTo() == null ? "" : sdf.format(bi.getTo()));
            updateStmnt.setInt(6, bi.getRecordId());
            updateStmnt.setInt(7, bi.getEquipmentId());
            
            return updateStmnt.executeUpdate() != 0 && (isChanged = true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean delete(BorrowItem bi) {
        try {
            deleteStmnt.setInt(1, bi.getRecordId());
            deleteStmnt.setInt(2, bi.getEquipmentId());
            
            return deleteStmnt.executeUpdate() != 0 && (isChanged = true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

}
