package org.personal.markcs.Model;

import java.util.Date;
import java.util.List;

public class Plot {

    private List<RealEstate> realEstates;
    private int plotNumber;
    private String type;
    private int size;
    private int approxValue;
    private Date dateOfPurchase;
    private float amountOfOwnership;

    public Plot(List<RealEstate> realEstates, int plotNumber, String type, int size, int approxValue, Date dateOfPurchase, float amountOfOwnership) {
        this.realEstates = realEstates;
        this.plotNumber = plotNumber;
        this.type = type;
        this.size = size;
        this.approxValue = approxValue;
        this.dateOfPurchase = dateOfPurchase;
        this.amountOfOwnership = amountOfOwnership;
    }

    public List<RealEstate> getRealEstates() {
        return realEstates;
    }

    public void setRealEstates(List<RealEstate> realEstates) {
        this.realEstates = realEstates;
    }

    public int getPlotNumber() {
        return plotNumber;
    }

    public void setPlotNumber(int plotNumber) {
        this.plotNumber = plotNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getApproxValue() {
        return approxValue;
    }

    public void setApproxValue(int approxValue) {
        this.approxValue = approxValue;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public float getAmountOfOwnership() {
        return amountOfOwnership;
    }

    public void setAmountOfOwnership(float amountOfOwnership) {
        this.amountOfOwnership = amountOfOwnership;
    }
}
