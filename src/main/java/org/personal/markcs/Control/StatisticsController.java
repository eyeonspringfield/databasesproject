package org.personal.markcs.Control;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.personal.markcs.App;
import org.personal.markcs.DAO.OwnershipDaoImpl;
import org.personal.markcs.DAO.PlotDaoImpl;
import org.personal.markcs.DAO.RealEstateDaoImpl;
import org.personal.markcs.Model.Session;

import java.io.IOException;
import java.util.Map;

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
    @FXML
    private TableView<Map<String, Object>> reTableView;
    @FXML
    private TableColumn<Map<String, Object>, String> reTypeCol;
    @FXML
    private TableColumn<Map<String, Object>, String> reCountCol;
    @FXML
    private TableView<Map<String, Object>> plotTableView;
    @FXML
    private TableColumn<Map<String, Object>, String> plotTypeCol;
    @FXML
    private TableColumn<Map<String, Object>, String> plotCountCol;

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

        reTypeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("jelleg").toString()));
        reCountCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("count").toString()));

        plotTypeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("jelleg").toString()));
        plotCountCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("count").toString()));

        ObservableList<Map<String, Object>> realEstates = ownershipDao.getListOfRealEstatesByType(Session.getName());
        ObservableList<Map<String, Object>> plots = ownershipDao.getListOfPlotsByType(Session.getName());
        reTableView.setItems(realEstates);
        plotTableView.setItems(plots);





    }

    @FXML
    private void returnToList() throws IOException {
        App.setRoot("list");
    }
}
