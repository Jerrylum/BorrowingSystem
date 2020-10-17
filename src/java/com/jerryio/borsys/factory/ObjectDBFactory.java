package com.jerryio.borsys.factory;

import com.jerryio.borsys.db.EquipmentDB;
import com.jerryio.borsys.db.UserDB;

public class ObjectDBFactory {
    public static EquipmentDB edb = null;
    public static UserDB udb = null;

    public static EquipmentDB getEquipmentDB() {
        return edb == null ? (edb = new EquipmentDB()) : edb;
    }

    public static UserDB getUserDB() {
        return udb == null ? (udb = new UserDB()) : udb;
    }
}
