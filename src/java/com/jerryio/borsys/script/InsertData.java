package com.jerryio.borsys.script;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jerryio.borsys.bean.*;
import com.jerryio.borsys.db.*;
import com.jerryio.borsys.enums.AvailabilityStatus;
import com.jerryio.borsys.enums.BorrowType;
import com.jerryio.borsys.enums.ListingStatus;
import com.jerryio.borsys.enums.RequestStatus;
import com.jerryio.borsys.enums.RoleType;
import com.jerryio.borsys.factory.ObjectDBFactory;

public class InsertData {
    public static EquipmentDB edb = null;
    public static UserDB udb = null;
    public static BorrowItemDB bidb = null;
    public static BorrowRecordDB brdb = null;

    public static void main(String[] args) throws Exception {

        long A0 = System.currentTimeMillis();

        edb = ObjectDBFactory.getEquipmentDB();
        udb = ObjectDBFactory.getUserDB();
        bidb = ObjectDBFactory.getBorrowItemDB();
        brdb = ObjectDBFactory.getBorrowRecordDB();

        ////////////////////////////////////////////////

        long A1 = System.currentTimeMillis();

        User stdA = udb.add("Student A", "letmein", RoleType.STUDENT);
        User stdB = udb.add("Student B", "letmein", RoleType.STUDENT);
        User stdC = udb.add("Student C", "letmein", RoleType.STUDENT);
        User stdD = udb.add("Student D", "letmein", RoleType.STUDENT);
        User stdE = udb.add("Student E", "letmein", RoleType.STUDENT);
        User stdF = udb.add("Student F", "letmein", RoleType.STUDENT);
        User stdG = udb.add("Student G", "letmein", RoleType.STUDENT);
        User stdH = udb.add("Student H", "letmein", RoleType.STUDENT);
        User stdI = udb.add("Student I", "letmein", RoleType.STUDENT);
        User stdJ = udb.add("Student J", "letmein", RoleType.STUDENT);

        User techA = udb.add("Technician A", "letmein", RoleType.TECHNICIAN);
        User techB = udb.add("Technician B", "letmein", RoleType.TECHNICIAN);
        User techC = udb.add("Technician C", "letmein", RoleType.TECHNICIAN);

        User adminA = udb.add("Admin A", "letmein", RoleType.ADMIN);
        User adminB = udb.add("Admin B", "letmein", RoleType.ADMIN);

        ////////////////////////////////////////////////

        Equipment redEq      = edb.add("iPad", "red label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment orangeEq   = edb.add("iPad", "orange label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment greenEq    = edb.add("iPad", "green label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment purpleEq   = edb.add("iPad", "purple label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment yellowEq   = edb.add("iPad", "yellow label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment blueEq     = edb.add("iPad", "blue label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment pinkEq     = edb.add("iPad", "pink label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment blackEq    = edb.add("iPad", "black label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment whiteEq    = edb.add("iPad", "white label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment dogEq      = edb.add("ASUS Notebook", "dog label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment catEq      = edb.add("ASUS Notebook", "cat label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment rabbitEq   = edb.add("ASUS Notebook", "rabbit label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment snakeEq    = edb.add("ASUS Notebook", "snake label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment bearEq     = edb.add("ASUS Notebook", "bear label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment hamsterEq  = edb.add("ASUS Notebook", "hamster label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment lizardEq   = edb.add("ASUS Notebook", " lizard label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment hamEq      = edb.add("iPhone X", "ham label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment beefEq     = edb.add("iPhone X", "beef label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment porkEq     = edb.add("iPhone X", "pork label", AvailabilityStatus.FREE, ListingStatus.ENABLE);
        Equipment fishEq     = edb.add("iPhone X", "fish label", AvailabilityStatus.FREE, ListingStatus.ENABLE);

        ////////////////////////////////////////////////      

        
        record(stdA, new Equipment[]{redEq}, "2020-09-01", "2020-09-05");
        record(stdD, new Equipment[]{purpleEq, dogEq}, "2020-09-02", "2020-09-06");
        record(stdA, new Equipment[]{orangeEq, greenEq}, "2020-09-02", "2020-09-06");
        record(stdD, new Equipment[]{yellowEq, catEq}, "2020-09-03", "2020-09-05");
        record(stdC, new Equipment[]{blueEq, beefEq}, "2020-09-03", "2020-09-05");
        record(stdF, new Equipment[]{pinkEq, rabbitEq, porkEq}, "2020-09-04", "2020-09-10");
        record(stdI, new Equipment[]{blackEq, whiteEq}, "2020-09-04", "2020-09-08");
        record(stdH, new Equipment[]{catEq}, "2020-09-06", "2020-09-08");
        record(stdE, new Equipment[]{redEq}, "2020-09-06", "2020-09-12");
        record(stdG, new Equipment[]{blueEq, beefEq}, "2020-09-06", "2020-09-10");
        record(stdA, new Equipment[]{greenEq, hamEq}, "2020-09-06", "2020-09-09");
        record(stdD, new Equipment[]{purpleEq, dogEq}, "2020-09-06", "2020-09-12");
        record(stdC, new Equipment[]{blackEq, whiteEq}, "2020-09-09", "2020-09-13");
        record(stdH, new Equipment[]{hamEq}, "2020-09-09", "2020-09-14");
        record(stdG, new Equipment[]{yellowEq, catEq}, "2020-09-09", "2020-09-15");
        record(stdF, new Equipment[]{snakeEq, bearEq}, "2020-09-10", "2020-09-14");
        record(stdB, new Equipment[]{purpleEq, dogEq}, "2020-09-12", "2020-09-14");
        record(stdH, new Equipment[]{redEq}, "2020-09-14", "2020-09-18");
        record(stdJ, new Equipment[]{orangeEq, greenEq}, "2020-09-14", "2020-09-18");
        record(stdJ, new Equipment[]{whiteEq}, "2020-09-15", "2020-09-18");
        record(stdA, new Equipment[]{snakeEq, bearEq}, "2020-09-15", "2020-09-20");
        record(stdF, new Equipment[]{redEq}, "2020-09-20", "2020-09-24");
        record(stdC, new Equipment[]{orangeEq, greenEq}, "2020-09-20", "2020-09-22");
        record(stdF, new Equipment[]{blueEq, beefEq}, "2020-09-21", "2020-09-24");
        record(stdB, new Equipment[]{pinkEq, rabbitEq, porkEq}, "2020-09-22", "2020-09-25");
        record(stdG, new Equipment[]{bearEq}, "2020-09-23", "2020-09-25");
        record(stdG, new Equipment[]{redEq}, "2020-09-25", "2020-09-30");
        record(stdH, new Equipment[]{yellowEq, catEq}, "2020-09-25", "2020-09-28");
        record(stdE, new Equipment[]{purpleEq, dogEq}, "2020-09-26", "2020-09-30");
        record(stdJ, new Equipment[]{rabbitEq, porkEq}, "2020-09-26", "2020-09-28");
        record(stdA, new Equipment[]{orangeEq, greenEq}, "2020-09-27", "2020-09-30");
                

        ////////////////////////////////////////////////

        long A2 = System.currentTimeMillis();

        System.out.println("init db in ms: " + (A1 - A0));
        System.out.println("insert time in ms: " + (A2 - A1));
    }

    public static void record(User std, Equipment[] list, String daccept, String dreturn) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date da = sdf.parse(daccept);
            Date dt = sdf.parse(dreturn);
//            Date dt = new Date(da.getTime());
//            Calendar c = Calendar.getInstance(); 
//            c.setTime(dt); 
//            c.add(Calendar.DATE, 7); // 1 week
//            dt = c.getTime();

            BorrowRecord br = brdb.add(std.getId(), RequestStatus.CONFIRM);

            for (int i = 0; i < list.length; i ++) {
                Equipment e = list[i];
                bidb.add(br.getId(), e.getId(), BorrowType.RETURNED, da, dt);
            }
        } catch (Exception e) {
            System.out.println("Insert failed");
            e.printStackTrace();
        }
    }
}
