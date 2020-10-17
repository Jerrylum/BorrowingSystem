package com.jerryio.borsys.bean;

import com.jerryio.borsys.enums.RoleType;
import java.io.Serializable;

public class User implements Serializable  {
    /**
     *
     */
    private static final long serialVersionUID = 1823061413373398895L;

    private int id;
    private String name;
    private String pwd;
    private RoleType role;

    public User() {
        
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

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public RoleType getRole() {
        return this.role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

}
