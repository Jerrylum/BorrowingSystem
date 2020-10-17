package com.jerryio.borsys.db;

import java.sql.*;
import java.util.ArrayList;

import com.jerryio.borsys.bean.Equipment;
import com.jerryio.borsys.enums.AvailabilityStatus;
import com.jerryio.borsys.enums.ListingStatus;

public class EquipmentDB extends ObjectDB {
    private static ArrayList<Equipment> allList = null;
    private static boolean isChanged = true;

    private PreparedStatement selectStmnt;
    private PreparedStatement insertStmnt;
    private PreparedStatement updateStmnt;
    private PreparedStatement deleteStmnt;

    public EquipmentDB() {
        try {
            selectStmnt = cnnct.prepareStatement("SELECT * FROM Equipment");
            insertStmnt = cnnct.prepareStatement("insert into Equipment (name, description, status, listing) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            updateStmnt = cnnct.prepareStatement("update Equipment set name=?,description=?,status=?,listing=? where eq_id=?");
            deleteStmnt = cnnct.prepareStatement("delete from Equipment where eq_id=?");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public ArrayList<Equipment> getAllEquipments() {
        if (isChanged)
            return queryAll();
        else
            return allList;
    }

    public Equipment getEquipment(int id) {
        for (Equipment e : getAllEquipments())
            if (e.getId() == id)
                return e;
        return null;
    }

    public ArrayList<Equipment> queryAll() {
        allList = new ArrayList<Equipment>();

        try {
            
            ResultSet rs = null;
            rs = selectStmnt.executeQuery();
            
            while (rs.next()) {
                Equipment e = new Equipment();
                e.setId(rs.getInt("eq_id"));
                e.setName(rs.getString("name"));
                e.setDescription(rs.getString("description"));
                e.setStatus(AvailabilityStatus.valueOf(rs.getString("status")));
                e.setListing(ListingStatus.valueOf(rs.getString("listing")));
                
                allList.add(e);
            }

            isChanged = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return allList;
    }
    
    public Equipment add(String name, String description, AvailabilityStatus status, ListingStatus lStatus) {
        try {
            insertStmnt.setString(1, name);
            insertStmnt.setString(2, description);
            insertStmnt.setString(3, status.toString());
            insertStmnt.setString(4, lStatus.toString());

            if (insertStmnt.executeUpdate() != 0) {
                ResultSet rs = insertStmnt.getGeneratedKeys();
                rs.next();            
            
                Equipment e = new Equipment();
                e.setId(rs.getInt(1));
                e.setName(name);
                e.setDescription(description);
                e.setStatus(status);
                e.setListing(lStatus);
    
                isChanged = true;

                return e;
            } else {
                return null;
            }          

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean update(Equipment e) {
        try {
            updateStmnt.setString(1, e.getName());
            updateStmnt.setString(2, e.getDescription());
            updateStmnt.setString(3, e.getStatus().toString());
            updateStmnt.setString(4, e.getListing().toString());
            updateStmnt.setInt(5, e.getId());
            
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

    public boolean delete(Equipment e) {
        return delete(e.getId());
    }
}
