package org.personal.markcs.Model;

import java.time.LocalDate;
import java.util.Date;

public class Ownership {
    private User user;
    private Plot plot;
    private RealEstate realEstate;
    private double partOfOwnership;
    private LocalDate dateOfPurchase;

    public Ownership(){}

    public Ownership(User user, Plot plot, RealEstate realEstate, double partOfOwnership, LocalDate dateOfPurchase) {
        this.user = user;
        this.plot = plot;
        this.realEstate = realEstate;
        this.partOfOwnership = partOfOwnership;
        this.dateOfPurchase = dateOfPurchase;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    public RealEstate getRealEstate() {
        return realEstate;
    }

    public void setRealEstate(RealEstate realEstate) {
        this.realEstate = realEstate;
    }

    public double getPartOfOwnership() {
        return partOfOwnership;
    }

    public void setPartOfOwnership(double partOfOwnership) {
        this.partOfOwnership = partOfOwnership;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }
}
