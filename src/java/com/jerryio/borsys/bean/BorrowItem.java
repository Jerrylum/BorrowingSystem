package com.jerryio.borsys.bean;

import java.util.Date;

import com.jerryio.borsys.db.EquipmentDB;
import com.jerryio.borsys.enums.BorrowType;
import java.io.Serializable;

public class BorrowItem implements Serializable  {
    /**
     *
     */
    private static final long serialVersionUID = 3769548742220540094L;

    private int id;
    private int equipmentId;
    private BorrowType status;
    private Date from;
    private Date to;

    public BorrowItem() {
        
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEquipmentId() {
        return this.equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Equipment getEquipment() {
        return EquipmentDB.getEquipment(equipmentId);
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
