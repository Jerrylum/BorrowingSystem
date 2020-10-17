package com.jerryio.borsys.test;

import com.jerryio.borsys.bean.Equipment;
import com.jerryio.borsys.bean.User;
import com.jerryio.borsys.enums.AvailabilityStatus;
import com.jerryio.borsys.enums.ListingStatus;
import com.jerryio.borsys.enums.RoleType;

/**
 * ObjTest
 */
public class ObjTest {

    public static void main(String[] args) {
        Equipment e = new Equipment();
        e.setId(123);
        e.setName("iPad");
        e.setDescription("Some Description");
        e.setStatus(AvailabilityStatus.valueOf("FREE"));
        e.setListing(ListingStatus.ENABLE);

        User u = new User();
        u.setId(456);
        u.setName("Jerry");
        u.setPwd("letmein");
        u.setRole(RoleType.STUDENT);

        System.out.println("Type: " + e.getStatus().toString());
        System.out.println("Ok");
    }
}