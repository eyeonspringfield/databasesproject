package org.personal.markcs.Model;

import java.util.Date;
import java.util.List;

public class User {
    private int ID;
    private String name;
    private String password;
    private int taxID;
    private String address;
    private String phone;
    private Date birthdate;
    private String mothersMaidenName;
    private boolean isLoggedIn;
    private Date lastLogin;
    private String role;

    public User(int ID, String name, String password, int taxID, String address, String phone, Date birthdate, String mothersMaidenName, boolean isLoggedIn, Date lastLogin, String role) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.taxID = taxID;
        this.address = address;
        this.phone = phone;
        this.birthdate = birthdate;
        this.mothersMaidenName = mothersMaidenName;
        this.isLoggedIn = isLoggedIn;
        this.lastLogin = lastLogin;
        this.role = role;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTaxID() {
        return taxID;
    }

    public void setTaxID(int taxID) {
        this.taxID = taxID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getMothersMaidenName() {
        return mothersMaidenName;
    }

    public void setMothersMaidenName(String mothersMaidenName) {
        this.mothersMaidenName = mothersMaidenName;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
