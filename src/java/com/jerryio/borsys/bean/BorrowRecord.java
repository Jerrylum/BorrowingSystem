package com.jerryio.borsys.bean;

import java.util.ArrayList;

import com.jerryio.borsys.enums.RequestStatus;
import java.io.Serializable;

public class BorrowRecord implements Serializable  {
    /**
     *
     */
    private static final long serialVersionUID = 571110441909526542L;
    
    private int id;
    private int userId;
    private RequestStatus status;

    private ArrayList<BorrowItem> items; // = new ArrayList<BorrowItem>();

    public BorrowRecord() {
        // TODO
        // items = 
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        // TODO
        return null;
    }

    public void setUser(User u) {
        this.userId = u.getId();
    }

    public RequestStatus getStatus() {
        return this.status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public ArrayList<BorrowItem> getItemList() {
        return this.items;
    }
}