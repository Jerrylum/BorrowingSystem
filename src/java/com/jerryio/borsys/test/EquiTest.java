package com.jerryio.borsys.test;

import java.util.ArrayList;

import com.jerryio.borsys.bean.Equipment;
import com.jerryio.borsys.db.EquipmentDB;
import com.jerryio.borsys.enums.AvailabilityStatus;
import com.jerryio.borsys.enums.ListingStatus;
import com.jerryio.borsys.factory.ObjectDBFactory;

public class EquiTest {
    public static EquipmentDB db = ObjectDBFactory.getEquipmentDB();

    public static void main(String[] args) {
        Equipment e = db.add("iPhone", "", AvailabilityStatus.FREE, ListingStatus.ENABLE);

        showAll();
        
        e.setStatus(AvailabilityStatus.USED);
        db.update(e);

        showAll();

        db.delete(e);

        showAll();
    }

    public static void showAll() {
        ArrayList<Equipment> list = db.getAllEquipments();

        System.out.println("Size: " + list.size());

        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i).getId() + ": " + list.get(i).getStatus());

    }
}
