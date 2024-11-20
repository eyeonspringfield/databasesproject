package org.personal.markcs.DAO;

import org.personal.markcs.Model.Ownership;

import java.sql.*;
import java.util.List;

public class OwnershipDaoImpl implements OwnershipDaoInterface{
    PreparedStatement stmt;
    ResultSet rs;
    String url = "jdbc:mysql://localhost:3306/ingatlanok";
    @Override
    public boolean addOwnership(Ownership ownership) {
        String query = "insert into tulajdon (adoszam, helyrajzi_szam, ingatlan_azonosito, tulajdonba_kerules_datuma, tulajdoni_hanyad) values (?, ?, ?, ?, ?)";
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setInt(1, ownership.getUser().getTaxID());
            stmt.setString(2, ownership.getPlot().getPlotNumber());
            stmt.setInt(3, ownership.getRealEstate().getID());
            stmt.setDate(4, Date.valueOf(ownership.getDateOfPurchase()));
            stmt.setDouble(5, ownership.getPartOfOwnership());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Ownership getOwnershipByTaxId(String TaxId) {
        return null;
    }

    @Override
    public List<Ownership> getAllOwnerships() {
        return List.of();
    }
}
