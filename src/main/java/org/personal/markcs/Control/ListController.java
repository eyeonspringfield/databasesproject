package org.personal.markcs.Control;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.personal.markcs.App;
import org.personal.markcs.DAO.OwnershipDaoImpl;
import org.personal.markcs.DAO.PlotDaoImpl;
import org.personal.markcs.DAO.RealEstateDaoImpl;
import org.personal.markcs.DAO.UserDaoImpl;
import org.personal.markcs.Model.*;

public class ListController {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label dobLabel;
    @FXML
    private Label taxIdLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label mothersNameLabel;
    @FXML
    private TableView<RealEstate> reTable;
    @FXML
    private TableColumn<RealEstate, String> reTypeCol;
    @FXML
    private TableColumn<RealEstate, Integer> reBuildYearCol;
    @FXML
    private TableColumn<RealEstate, Integer> rePostalCodeCol;
    @FXML
    private TableColumn<RealEstate, String> reSettlementCol;
    @FXML
    private TableColumn<RealEstate, String> reHouseAndStreetCol;
    @FXML
    private TableColumn<RealEstate, Integer> reApproxValueCol;
    @FXML
    private TableView<Plot> plotTable;
    @FXML
    private TableColumn<Plot, String> plotIdCol;
    @FXML
    private TableColumn<Plot, String> plotTypeCol;
    @FXML
    private TableColumn<Plot, Integer> plotSizeCol;
    @FXML
    private TableColumn<Plot, Integer> plotApproxValueCol;

    private UserDaoImpl dao = new UserDaoImpl();
    private OwnershipDaoImpl ownershipDao = new OwnershipDaoImpl();
    private PlotDaoImpl plotDao = new PlotDaoImpl();
    private RealEstateDaoImpl realEstateDao = new RealEstateDaoImpl();
    @FXML
    private void initialize() {
        User user = dao.getUserByName(Session.getName());

        if(!Session.isAuthenticated() || user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Érvénytelen azonosító!");
            alert.showAndWait();
            return;
        }

        usernameLabel.setText(user.getName());
        addressLabel.setText(user.getAddress());
        dobLabel.setText(user.getBirthdate().toString());
        taxIdLabel.setText(String.valueOf(user.getTaxID()));
        phoneNumberLabel.setText(user.getPhone());
        mothersNameLabel.setText(user.getMothersMaidenName());

        List<Ownership> ownerships = ownershipDao.getAllOwnershipOfUser(user);

        reTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        reBuildYearCol.setCellValueFactory(new PropertyValueFactory<>("timeOfConstruction"));
        rePostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        reSettlementCol.setCellValueFactory(new PropertyValueFactory<>("settlement"));
        reHouseAndStreetCol.setCellValueFactory(new PropertyValueFactory<>("streetAndHouseNumber"));
        reApproxValueCol.setCellValueFactory(new PropertyValueFactory<>("approxValue"));

        plotIdCol.setCellValueFactory(new PropertyValueFactory<>("plotNumber"));
        plotTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        plotSizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        plotApproxValueCol.setCellValueFactory(new PropertyValueFactory<>("approxValue"));

        ObservableList<RealEstate> realEstateObservableList = FXCollections.observableArrayList();
        ObservableList<Plot> plotObservableList = FXCollections.observableArrayList();
        for(Ownership ownership : ownerships) {
            System.out.println("ownership exists");
            if(ownership.getRealEstate() != null) {
                System.out.println("real eastate exists");
                realEstateObservableList.add(realEstateDao.getRealEstateById(realEstateDao.getRealEstateID(ownership.getRealEstate())));
            }else{
                plotObservableList.add(plotDao.getPlotByPlotNumber(ownership.getPlot().getPlotNumber()));
            }
        }
        reTable.setItems(realEstateObservableList);
        plotTable.setItems(plotObservableList);


        //TODO tableview initialization
    }

    @FXML
    private void editUserData() throws IOException {
        App.setRoot("editUserData");
    }

    @FXML
    private void logout() throws IOException {
        if(!Session.isAuthenticated()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Érvénytelen azonosító!");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Jóváhagyás szükséges!");
        alert.setHeaderText(null);
        alert.setContentText("Biztosan szeretne kijelentkezni?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            Session.setName(null);
            Session.setIsAuthenticated(false);
            App.setRoot("primary");
        }
    }

    @FXML
    private void showStatistics() throws IOException {
        App.setRoot("statistics");
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}