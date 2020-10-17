package com.jerryio.borsys.db;

import java.sql.*;
import java.util.ArrayList;

import com.jerryio.borsys.Util;
import com.jerryio.borsys.bean.Equipment;
import com.jerryio.borsys.enums.AvailabilityStatus;
import com.jerryio.borsys.enums.ListingStatus;

public class EquipmentDB {
    private static ArrayList<Equipment> allList = null;
    private static boolean isChanged = true;

    public static ArrayList<Equipment> getAllEquipments() {
        if (isChanged)
            return queryAll();
        else
            return allList;
    }

    public static Equipment getEquipment(int id) {
        for (Equipment e : getAllEquipments())
            if (e.getId() == id)
                return e;
        return null;
    }

    public static ArrayList<Equipment> queryAll() {
        allList = new ArrayList<Equipment>();

        try {
            Connection cnnct = Util.getConnection();

            PreparedStatement pStmnt = cnnct.prepareStatement("SELECT * FROM Equipment");
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            
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
    
    public static Equipment add(String name, String description, AvailabilityStatus status, ListingStatus lStatus) {

        try {
            Connection cnnct = Util.getConnection();

            PreparedStatement pStmnt = cnnct.prepareStatement("insert into Equipment (name, description, status, listing) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pStmnt.setString(1, name);
            pStmnt.setString(2, description);
            pStmnt.setString(3, status.toString());
            pStmnt.setString(4, lStatus.toString());

            if (pStmnt.executeUpdate() != 0) {
                ResultSet rs = pStmnt.getGeneratedKeys();
                rs.next();            
            
                Equipment e = new Equipment();
                e.setId(rs.getInt(1));
                e.setName(name);
                e.setDescription(description);
                e.setStatus(status);
                e.setListing(lStatus);
    
                isChanged = true;

                pStmnt.close();
                cnnct.close();

                return e;
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

    public static boolean update(Equipment e) {
        try {
            Connection cnnct = Util.getConnection();

            PreparedStatement pStmnt = cnnct.prepareStatement("update Equipment set name=?,description=?,status=?,listing=? where eq_id=?");
            pStmnt.setString(1, e.getName());
            pStmnt.setString(2, e.getDescription());
            pStmnt.setString(3, e.getStatus().toString());
            pStmnt.setString(4, e.getListing().toString());
            pStmnt.setInt(5, e.getId());
            
            return pStmnt.executeUpdate() != 0 && (isChanged = true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean delete(int id) {
        try {
            Connection cnnct = Util.getConnection();

            PreparedStatement pStmnt = cnnct.prepareStatement("delete from Equipment where eq_id=?");
            pStmnt.setInt(1, id);
            
            return pStmnt.executeUpdate() != 0 && (isChanged = true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean delete(Equipment e) {
        return delete(e.getId());
    }
}
