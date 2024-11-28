package org.personal.markcs.DAO;

import org.personal.markcs.Model.Ownership;
import org.personal.markcs.Model.OwnershipType;
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
        List<Ownership> result = new ArrayList<Ownership>();
        String query = "select * from tulajdon";
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()) {
                Ownership ownership = new Ownership();
                ownership.setUser(userDao.getUserByTaxId(rs.getInt("adoszam")));
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

    public Ownership getOwnershipByRealEstateId(int realEstateId) {
        String query = "select * from tulajdon where ingatlan_azonosito = ?";
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setInt(1, realEstateId);
            rs = stmt.executeQuery();
            Ownership ownership = new Ownership();
            if(rs.next()) {
                ownership.setUser(userDao.getUserByTaxId(rs.getInt("adoszam")));
                ownership.setPlot(plotDao.getPlotByPlotNumber(rs.getString("helyrajzi_szam")));
                ownership.setRealEstate(realEstateDao.getRealEstateById(rs.getInt("ingatlan_azonosito")));
                ownership.setDateOfPurchase(rs.getDate("tulajdonba_kerules_datuma").toLocalDate());
                ownership.setPartOfOwnership(rs.getInt("tulajdoni_hanyad"));
            }
            return ownership;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Ownership getOwnershipByPlotNumber(String plotNumber) {
        // Fetch Ownership record where plot number matches the given plot.
        // Implementation depends on your query structure.
        return null;
    }

    public int getNumOfRealEstatesByUsername(String username) {
        String query = "select count(*) from tulajdon where adoszam = ? and ingatlan_azonosito IS NOT NULL";
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setInt(1, userDao.getUserByName(username).getTaxID());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getNumOfPlotsByUsername(String username){
        String query = "select count(*) from tulajdon where adoszam = ? and ingatlan_azonosito IS NULL";
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setInt(1, userDao.getUserByName(username).getTaxID());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getSumOfRealEstateValueByUsername(String username){
        String query = "select sum(becsult_ertek) from tulajdon as t, ingatlan as i " +
                "where t.adoszam = ? and t.ingatlan_azonosito IS NOT NULL " +
                "and i.ingatlan_azonosito = t.ingatlan_azonosito";
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setInt(1, userDao.getUserByName(username).getTaxID());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getSumOfPlotsValueByUsername(String username){
        String query = "select sum(becsult_ertek) from tulajdon as tu, telek as te " +
                "where tu.adoszam = ? and tu.ingatlan_azonosito IS NULL " +
                "and tu.helyrajzi_szam = te.helyrajzi_szam";
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setInt(1, userDao.getUserByName(username).getTaxID());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getSumOfValueOfPlotsAndRealEstatesByUsername(String username){
        String query = "select sum(te.becsult_ertek + ifnull(i.becsult_ertek, 0)) as total_value " +
                "from tulajdon as tu " +
                "left join telek as te on te.helyrajzi_szam = tu.helyrajzi_szam " +
                "left join ingatlan as i on i.ingatlan_azonosito = tu.ingatlan_azonosito " +
                "where tu.adoszam = ?;";
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setInt(1, userDao.getUserByName(username).getTaxID());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
