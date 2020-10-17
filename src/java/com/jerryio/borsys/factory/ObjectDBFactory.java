package com.jerryio.borsys.factory;

import com.jerryio.borsys.db.BorrowItemDB;
import com.jerryio.borsys.db.BorrowRecordDB;
import com.jerryio.borsys.db.EquipmentDB;
import com.jerryio.borsys.db.UserDB;

public class ObjectDBFactory {
    public static EquipmentDB edb = null;
    public static UserDB udb = null;
    public static BorrowItemDB bidb = null;
    public static BorrowRecordDB brdb = null;

    public static EquipmentDB getEquipmentDB() {
        return edb == null ? (edb = new EquipmentDB()) : edb;
    }

    public static UserDB getUserDB() {
        return udb == null ? (udb = new UserDB()) : udb;
    }

    public static BorrowItemDB getBorrowItemDB() {
        return bidb == null ? (bidb = new BorrowItemDB()) : bidb;
    }

    public static BorrowRecordDB getBorrowRecordDB() {
        return brdb == null ? (brdb = new BorrowRecordDB()) : brdb;
    }
}
