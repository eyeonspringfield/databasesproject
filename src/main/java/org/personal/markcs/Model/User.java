package org.personal.markcs.Model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class User {
    private int ID;
    private String name;
    private String password;
    private int taxID;
    private String address;
    private String phone;
    private LocalDate birthdate;
    private String mothersMaidenName;
    private boolean isLoggedIn;
    private LocalDate lastLogin;
    private String role;

    public User(int ID, String name, String password, int taxID, String address, String phone, LocalDate birthdate, String mothersMaidenName, boolean isLoggedIn, LocalDate lastLogin, String role) {
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

    public User(String name, String password, int taxID, String address, String phone, LocalDate birthdate, String mothersMaidenName, boolean isLoggedIn, LocalDate lastLogin, String role) {
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
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

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
