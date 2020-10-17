package com.jerryio.borsys.bean;

import com.jerryio.borsys.enums.AvailabilityStatus;
import com.jerryio.borsys.enums.ListingStatus;
import java.io.Serializable;

public class Equipment implements Serializable  {
    /**
     *
     */
    private static final long serialVersionUID = -7312483646877174365L;

    private int id;
    private String name;
    private String description;
    private AvailabilityStatus status;
    private ListingStatus listing;

    public Equipment() {
        
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AvailabilityStatus getStatus() {
        return this.status;
    }

    public void setStatus(AvailabilityStatus status) {
        this.status = status;
    }

    public ListingStatus getListing() {
        return this.listing;
    }

    public void setListing(ListingStatus listing) {
        this.listing = listing;
    }
}
