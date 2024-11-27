package org.personal.markcs.DAO;

import org.personal.markcs.Model.Ownership;
import org.personal.markcs.Model.OwnershipType;
import org.personal.markcs.Model.Plot;
import org.personal.markcs.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnershipDaoImpl implements OwnershipDaoInterface{
    PreparedStatement stmt;
    ResultSet rs;
    String url = "jdbc:mysql://localhost:3306/ingatlanok";
    UserDaoImpl userDao = new UserDaoImpl();
    PlotDaoImpl plotDao = new PlotDaoImpl();
    RealEstateDaoImpl realEstateDao = new RealEstateDaoImpl();
    @Override
    public boolean addOwnership(Ownership ownership, OwnershipType type) {
        String query = null;

        if(type == OwnershipType.PLOT_TYPE) {
            query = "insert into tulajdon (adoszam, helyrajzi_szam, ingatlan_azonosito, tulajdonba_kerules_datuma, tulajdoni_hanyad) values (?, ?, NULL, ?, ?)";
        }else if(type == OwnershipType.REAL_ESTATE_TYPE) {
            query = "insert into tulajdon (adoszam, helyrajzi_szam, ingatlan_azonosito, tulajdonba_kerules_datuma, tulajdoni_hanyad) values (?, ?, ?, ?, ?)";
        }
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);

            stmt.setInt(1, ownership.getUser().getTaxID());
            stmt.setString(2, ownership.getPlot().getPlotNumber());
            if(type == OwnershipType.REAL_ESTATE_TYPE) {
                System.out.println("id" + ownership.getRealEstate().getID());
                stmt.setInt(3, ownership.getRealEstate().getID());
                stmt.setDate(4, Date.valueOf(ownership.getDateOfPurchase()));
                stmt.setDouble(5, ownership.getPartOfOwnership());
            }else if(type == OwnershipType.PLOT_TYPE) {
                stmt.setDate(3, Date.valueOf(ownership.getDateOfPurchase()));
                stmt.setDouble(4, ownership.getPartOfOwnership());
            }

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

    public List<Ownership> getAllOwnershipOfUser(User user) {
        List<Ownership> result = new ArrayList<Ownership>();
        String query = "select * from tulajdon where adoszam = ?";
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setInt(1, user.getTaxID());
            rs = stmt.executeQuery();

            while(rs.next()) {
                Ownership ownership = new Ownership();
                ownership.setUser(userDao.getUserByName(user.getName()));
                ownership.setPlot(plotDao.getPlotByPlotNumber(rs.getString("helyrajzi_szam")));
                if(rs.getInt("ingatlan_azonosito") != 0) {
                    ownership.setRealEstate(realEstateDao.getRealEstateById(rs.getInt("ingatlan_azonosito")));
                }
                ownership.setDateOfPurchase(rs.getDate("tulajdonba_kerules_datuma").toLocalDate());
                ownership.setPartOfOwnership(rs.getInt("tulajdoni_hanyad"));
                result.add(ownership);
            }

            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ownership> getAllOwnerships() {
        return List.of();
    }
}
