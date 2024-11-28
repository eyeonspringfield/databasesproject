package org.personal.markcs.Control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.personal.markcs.App;
import org.personal.markcs.DAO.OwnershipDaoImpl;
import org.personal.markcs.DAO.PlotDaoImpl;
import org.personal.markcs.DAO.RealEstateDaoImpl;
import org.personal.markcs.Model.Session;

import java.io.IOException;

public class StatisticsController {
    @FXML
    private Label numOfRealEstate;
    @FXML
    private Label numOfPlot;
    @FXML
    private Label sumOfValueOfRealEstate;
    @FXML
    private Label sumOfValueOfPlot;
    @FXML
    private Label sumOfValueOfPortfolio;

    private RealEstateDaoImpl realEstateDao = new RealEstateDaoImpl();
    private PlotDaoImpl plotDao = new PlotDaoImpl();
    private OwnershipDaoImpl ownershipDao = new OwnershipDaoImpl();

    public void initialize() {
        numOfRealEstate.setText(ownershipDao.getNumOfRealEstatesByUsername(Session.getName()) + " db");
        numOfPlot.setText(ownershipDao.getNumOfPlotsByUsername(Session.getName()) + " db");
        sumOfValueOfRealEstate.setText(NumberFormat.formatWithSpaces(String.valueOf(
                ownershipDao.getSumOfRealEstateValueByUsername(Session.getName()))) + " HUF");
        sumOfValueOfPlot.setText(NumberFormat.formatWithSpaces(String.valueOf(
                ownershipDao.getSumOfPlotsValueByUsername(Session.getName()))) + " HUF");
        sumOfValueOfPortfolio.setText(NumberFormat.formatWithSpaces(String.valueOf(
                ownershipDao.getSumOfValueOfPlotsAndRealEstatesByUsername(Session.getName()))) + " HUF");
    }

    @FXML
    private void returnToList() throws IOException {
        App.setRoot("list");
    }
}
