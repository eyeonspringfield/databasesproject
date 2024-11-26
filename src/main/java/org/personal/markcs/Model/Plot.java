package org.personal.markcs.Model;

import java.util.Date;
import java.util.List;

public class Plot {

    private List<RealEstate> realEstates;
    private String plotNumber;
    private String type;
    private int size;
    private int approxValue;
    private Date dateOfPurchase;
    private float amountOfOwnership;

    public Plot(List<RealEstate> realEstates, String plotNumber, String type, int size, int approxValue, Date dateOfPurchase, float amountOfOwnership) {
        this.realEstates = realEstates;
        this.plotNumber = plotNumber;
        this.type = type;
        this.size = size;
        this.approxValue = approxValue;
        this.dateOfPurchase = dateOfPurchase;
        this.amountOfOwnership = amountOfOwnership;
    }

    public Plot(String plotNumber, String type, int size, int approxValue) {
        this.plotNumber = plotNumber;
        this.type = type;
        this.size = size;
        this.approxValue = approxValue;
    }

    public List<RealEstate> getRealEstates() {
        return realEstates;
    }

    public void setRealEstates(List<RealEstate> realEstates) {
        this.realEstates = realEstates;
    }

    public String getPlotNumber() {
        return plotNumber;
    }

    public void setPlotNumber(String plotNumber) {
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
