package com.jerryio.borsys.test;

import java.sql.Connection;
import java.util.ArrayList;

import com.jerryio.borsys.Util;
import com.jerryio.borsys.bean.BorrowItem;
import com.jerryio.borsys.bean.BorrowRecord;
import com.jerryio.borsys.db.BorrowItemDB;
import com.jerryio.borsys.db.BorrowRecordDB;
import com.jerryio.borsys.enums.BorrowType;
import com.jerryio.borsys.enums.RequestStatus;
import com.jerryio.borsys.factory.ObjectDBFactory;

public class BorrowTest {
    public static Connection conn;
    public static BorrowRecordDB db = ObjectDBFactory.getBorrowRecordDB();
    public static BorrowItemDB db2 = ObjectDBFactory.getBorrowItemDB();

    public static void main(String[] args) throws Exception {
        Connection conn = Util.getConnection();

        showAll();

        conn.setAutoCommit(false);

        BorrowRecord br = db.add(7, RequestStatus.ASKED);
        
        db2.add(br.getId(), 10, BorrowType.ASKED, null, null);
        db2.add(br.getId(), 11, BorrowType.ASKED, null, null);

        conn.commit();
        conn.setAutoCommit(true);

        showAll();

        db2.add(br.getId(), 13, BorrowType.ASKED, null, null);

        showAll();

        br.setUserId(8);
        db.update(br);

        showAll();

        conn.setAutoCommit(false);

        for (BorrowItem bi : br.getItemList())
            db2.delete(bi);

        db.delete(br);

        conn.commit();
        conn.setAutoCommit(true);

        showAll();
    }

    public static void showAll() {
        ArrayList<BorrowRecord> list = db.getAllBorrowRecords();

        System.out.println("Size: " + list.size());

        for (int i = 0; i < list.size(); i++) {
            BorrowRecord br = list.get(i);
            System.out.println(br.getId() + ": " + br.getUserId() + " " + br.getItemList().size());
        }

    }
}
