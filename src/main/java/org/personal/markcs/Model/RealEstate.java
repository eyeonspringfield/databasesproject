package org.personal.markcs.Model;

import java.util.Date;
import java.util.List;

public class RealEstate {

    private int ID;
    private Plot plot;
    private String type;
    private int timeOfConstruction;
    private int postalCode;
    private String settlement;
    private String streetAndHouseNumber;
    private int approxValue;

    public RealEstate(int ID, Plot plot, String type, int timeOfConstruction, int postalCode, String settlement, String streetAndHouseNumber, int approxValue) {
        this.ID = ID;
        this.plot = plot;
        this.type = type;
        this.timeOfConstruction = timeOfConstruction;
        this.postalCode = postalCode;
        this.settlement = settlement;
        this.streetAndHouseNumber = streetAndHouseNumber;
        this.approxValue = approxValue;
    }

    public RealEstate(Plot plot, String type, int timeOfConstruction, int postalCode, String settlement, String streetAndHouseNumber, int approxValue) {
        this.plot = plot;
        this.type = type;
        this.timeOfConstruction = timeOfConstruction;
        this.postalCode = postalCode;
        this.settlement = settlement;
        this.streetAndHouseNumber = streetAndHouseNumber;
        this.approxValue = approxValue;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTimeOfConstruction() {
        return timeOfConstruction;
    }

    public void setTimeOfConstruction(int timeOfConstruction) {
        this.timeOfConstruction = timeOfConstruction;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getStreetAndHouseNumber() {
        return streetAndHouseNumber;
    }

    public void setStreetAndHouseNumber(String streetAndHouseNumber) {
        this.streetAndHouseNumber = streetAndHouseNumber;
    }

    public int getApproxValue() {
        return approxValue;
    }

    public void setApproxValue(int approxValue) {
        this.approxValue = approxValue;
    }
}
