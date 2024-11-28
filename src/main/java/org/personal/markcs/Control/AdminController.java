package org.personal.markcs.Control;

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

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class AdminController {
    UserDaoImpl userDao = new UserDaoImpl();
    PlotDaoImpl plotDao = new PlotDaoImpl();
    RealEstateDaoImpl realEstateDao = new RealEstateDaoImpl();
    OwnershipDaoImpl ownershipDao = new OwnershipDaoImpl();

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
    private TextField reType;
    @FXML
    private TextField reDateOfBuilding;
    @FXML
    private TextField rePostalCode;
    @FXML
    private TextField reSettlement;
    @FXML
    private TextField reStreetAndHouse;
    @FXML
    private TextField reApproxValue;
    @FXML
    private TextField reNameOfOwner;
    @FXML
    private TextField rePercentOfOwnership;
    @FXML
    private TextField rePlotId;
    @FXML
    private DatePicker reDateOfPurchase;
    @FXML
    private TextField plotNumber;
    @FXML
    private TextField plotType;
    @FXML
    private TextField plotSize;
    @FXML
    private TextField plotApproxValue;
    @FXML
    private TextField plotOwnerName;
    @FXML
    private TextField plotPercentOfOwnership;
    @FXML
    private DatePicker plotDateOfPurchase;
    @FXML
    private TableView<RealEstate> reTableView;
    @FXML
    private TableColumn<RealEstate, String> reTypeCol;
    @FXML
    private TableColumn<RealEstate, Date> reDateOfBuildingCol;
    @FXML
    private TableColumn<RealEstate, Integer> rePostalCodeCol;
    @FXML
    private TableColumn<RealEstate, String> reSettlementCol;
    @FXML
    private TableColumn<RealEstate, String> reStreetAndHouseCol;
    @FXML
    private TableColumn<RealEstate, Integer> reApproxValueCol;
    @FXML
    private TableColumn<RealEstate, Void> actionColumnRe;
    @FXML
    private TableView<Plot> plotTableView;
    @FXML
    private TableColumn<Plot, String> plotNumberCol;
    @FXML
    private TableColumn<Plot, String> plotTypeCol;
    @FXML
    private TableColumn<Plot, String> plotSizeCol;
    @FXML
    private TableColumn<Plot, String> plotApproxValueCol;
    @FXML
    private TableColumn<Plot, Void> actionColumnPlot;
    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, String> taxIdCol;
    @FXML
    private TableColumn<User, String> phoneNumberCol;

    ObservableList<RealEstate> realEstateObservableList = FXCollections.observableArrayList();
    ObservableList<Plot> plotObservableList = FXCollections.observableArrayList();
    ObservableList<User> userObservableList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        User user = userDao.getUserByName(Session.getName());

        if(!Session.isAuthenticated() || !Session.isAdmin() || user == null) {
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

        List<Ownership> ownerships = ownershipDao.getAllOwnerships();
        List<User> users = userDao.getAllUsers();

        reTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        reDateOfBuildingCol.setCellValueFactory(new PropertyValueFactory<>("timeOfConstruction"));
        rePostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        reSettlementCol.setCellValueFactory(new PropertyValueFactory<>("settlement"));
        reStreetAndHouseCol.setCellValueFactory(new PropertyValueFactory<>("streetAndHouseNumber"));
        reApproxValueCol.setCellValueFactory(new PropertyValueFactory<>("approxValue"));

        plotNumberCol.setCellValueFactory(new PropertyValueFactory<>("plotNumber"));
        plotTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        plotSizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        plotApproxValueCol.setCellValueFactory(new PropertyValueFactory<>("approxValue"));

        usernameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        taxIdCol.setCellValueFactory(new PropertyValueFactory<>("taxID"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        for(User userFromList : users){
            userObservableList.add(userDao.getUserByName(userFromList.getName()));
        }

        for(Ownership ownership : ownerships) {
            if(ownership.getRealEstate() != null) {
                realEstateObservableList.add(realEstateDao.getRealEstateById(realEstateDao.getRealEstateID(ownership.getRealEstate())));
            }else{
                plotObservableList.add(plotDao.getPlotByPlotNumber(ownership.getPlot().getPlotNumber()));
            }
        }
        reTableView.setItems(realEstateObservableList);
        plotTableView.setItems(plotObservableList);
        userTableView.setItems(userObservableList);

        refreshData();

        actionColumnRe.setCellFactory(param -> {
            Button deleteButton = new Button("X");
            return new TableCell<RealEstate, Void>() {
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                        deleteButton.setOnAction(event -> {
                            TableRow<RealEstate> row = getTableRow();
                            if (row != null) {
                                int index = row.getIndex();
                                RealEstate realEstateItem = getTableView().getItems().get(index);
                                System.out.println(realEstateItem.getID() + realEstateItem.getApproxValue());
                                handleDeleteActionForRealEstate(realEstateItem);
                            }
                        });
                    }
                }
            };
        });
        if (!reTableView.getColumns().contains(actionColumnRe)) {
            reTableView.getColumns().add(actionColumnRe);
        }

        actionColumnPlot.setCellFactory(param -> {
            Button deleteButton = new Button("X");
            return new TableCell<Plot, Void>() {
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                        deleteButton.setOnAction(event -> {
                            TableRow<Plot> row = getTableRow();
                            if (row != null) {
                                int index = row.getIndex();
                                Plot plot = getTableView().getItems().get(index);
                                handleDeleteActionForPlot(plot);
                            }
                        });
                    }
                }
            };
        });
        if (!reTableView.getColumns().contains(actionColumnRe)) {
            reTableView.getColumns().add(actionColumnRe);
        }
    }

    private void handleDeleteActionForRealEstate(RealEstate realEstate) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Jóváhagyás szükséges!");
        alert.setHeaderText(null);
        alert.setContentText("Biztosan szeretné kitörölni az ingatlant? Ez a folyamat visszafordíthatatlan!");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            System.out.println(realEstate.getID());
            boolean deleted = realEstateDao.deleteRealEstate(realEstate);
            System.out.println(deleted);
            refreshData();
        }
    }

    private void handleDeleteActionForPlot(Plot plot){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Jóváhagyás szükséges!");
        alert.setHeaderText(null);
        alert.setContentText("Biztosan szeretné kitörölni az ingatlant? Ez a folyamat visszafordíthatatlan!");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            boolean deleted = plotDao.deletePlot(plot);
            System.out.println(deleted);
            refreshData();
        }
    }

    private void refreshData() {
        List<Ownership> ownerships = ownershipDao.getAllOwnerships();

        realEstateObservableList.clear();
        plotObservableList.clear();

        for (Ownership ownership : ownerships) {
            if (ownership.getRealEstate() != null) {
                realEstateObservableList.add(realEstateDao.getRealEstateById(
                        realEstateDao.getRealEstateID(ownership.getRealEstate())));
            } else {
                plotObservableList.add(plotDao.getPlotByPlotNumber(ownership.getPlot().getPlotNumber()));
            }
        }
    }

    @FXML
    private void addRealEstate() throws IOException {
        User user = userDao.getUserByName(reNameOfOwner.getText());

        if(reType.getText().isEmpty() || reDateOfBuilding.getText().isEmpty() || rePostalCode.getText().isEmpty()
        || reSettlement.getText().isEmpty() || reStreetAndHouse.getText().isEmpty() || reApproxValue.getText().isEmpty()
        || reNameOfOwner.getText().isEmpty() || rePercentOfOwnership.getText().isEmpty() || rePlotId.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Nem lehet üres beviteli mező!");
            alert.showAndWait();
            return;
        }

        if(user == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Nincs ilyen nevű felhasználó!");
            alert.showAndWait();
            return;
        }

        if(plotDao.getPlotByPlotNumber(rePlotId.getText()) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Nincs ilyen helyrajzi számmal rendelkező telek!");
            alert.showAndWait();
            return;
        }

        Plot plot = plotDao.getPlotByPlotNumber(rePlotId.getText());

        RealEstate realEstate = new RealEstate(
                plot,
                reType.getText(),
                Integer.parseInt(reDateOfBuilding.getText()),
                Integer.parseInt(rePostalCode.getText()),
                reSettlement.getText(),
                reStreetAndHouse.getText(),
                Integer.parseInt(reApproxValue.getText())
        );

        Ownership ownership = new Ownership();
        ownership.setUser(user);
        ownership.setPlot(plot);
        ownership.setRealEstate(realEstate);
        ownership.setPartOfOwnership(Float.parseFloat(rePercentOfOwnership.getText()));
        ownership.setDateOfPurchase(reDateOfPurchase.getValue());

        realEstateDao.addRealEstate(realEstate);
        realEstate.setID(realEstateDao.getRealEstateID(realEstate));
        ownershipDao.addOwnership(ownership, OwnershipType.REAL_ESTATE_TYPE);

        refreshData();
    }

    @FXML
    private void addPlot() throws IOException{
        User user = userDao.getUserByName(plotOwnerName.getText());

        if(plotNumber.getText().isEmpty() || plotSize.getText().isEmpty() || plotApproxValue.getText().isEmpty()
            || plotOwnerName.getText().isEmpty() || plotPercentOfOwnership.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Nem lehet üres beviteli mező!");
            alert.showAndWait();
            return;
        }

        if(user == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Nincs ilyen nevű felhasználó!");
            alert.showAndWait();
            return;
        }

        Plot plot = new Plot(
                plotNumber.getText(),
                plotType.getText(),
                Integer.parseInt(plotSize.getText()),
                Integer.parseInt(plotApproxValue.getText())
        );

        Ownership ownership = new Ownership();
        ownership.setUser(user);
        ownership.setPlot(plot);
        ownership.setPartOfOwnership(Float.parseFloat(plotPercentOfOwnership.getText()));
        ownership.setDateOfPurchase(plotDateOfPurchase.getValue());

        plotDao.addPlot(plot);
        ownershipDao.addOwnership(ownership, OwnershipType.PLOT_TYPE);

        refreshData();
    }

    @FXML
    private void editUserData() throws IOException {
        App.setRoot("editUserData");
    }

    @FXML
    private void logout() throws IOException {
        if(!Session.isAuthenticated() || !Session.isAdmin()) {
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
}
