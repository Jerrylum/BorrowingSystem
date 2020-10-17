package com.jerryio.borsys.bean;

import java.util.Date;

import com.jerryio.borsys.factory.ObjectDBFactory;
import com.jerryio.borsys.enums.BorrowType;
import java.io.Serializable;

public class BorrowItem implements Serializable  {
    /**
     *
     */
    private static final long serialVersionUID = 3769548742220540094L;

    private int recordId;
    private int equipmentId;
    private BorrowType status;
    private Date from;
    private Date to;

    public BorrowItem() {
        
    }

    public int getRecordId() {
        return this.recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public BorrowRecord getRecord() {
        return ObjectDBFactory.getBorrowRecordDB().getBorrowRecord(recordId);
    }

    public void setRecord(BorrowRecord r) {
        this.recordId = r.getId();
    }

    public int getEquipmentId() {
        return this.equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Equipment getEquipment() {
        return ObjectDBFactory.getEquipmentDB().getEquipment(equipmentId);
    }

    public void setEquipment(Equipment e) {
        this.equipmentId = e.getId();
    }

    public BorrowType getStatus() {
        return this.status;
    }

    public void setStatus(BorrowType status) {
        this.status = status;
    }

    public Date getFrom() {
        return this.from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return this.to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

}
