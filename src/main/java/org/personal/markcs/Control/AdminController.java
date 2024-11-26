package org.personal.markcs.Control;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.personal.markcs.App;
import org.personal.markcs.DAO.OwnershipDaoImpl;
import org.personal.markcs.DAO.PlotDaoImpl;
import org.personal.markcs.DAO.RealEstateDaoImpl;
import org.personal.markcs.DAO.UserDaoImpl;
import org.personal.markcs.Model.*;

import java.io.IOException;
import java.sql.Date;

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

        //TODO tableview initialization
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
        }

        if(user == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Nincs ilyen nevű felhasználó!");
            alert.showAndWait();
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
