package org.personal.markcs.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.personal.markcs.Model.Ownership;
import org.personal.markcs.Model.OwnershipType;
import org.personal.markcs.Model.RealEstate;
import org.personal.markcs.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnershipDaoImpl implements OwnershipDaoInterface{
    PreparedStatement stmt;
    ResultSet rs;
    String url = "jdbc:mysql://localhost:3306/ingatlanok";
    UserDaoImpl userDao = new UserDaoImpl();
    PlotDaoImpl plotDao = new PlotDaoImpl();
    RealEstateDaoImpl realEstateDao = new RealEstateDaoImpl();
    @Override
    public boolean addOwnership(Ownership ownership, OwnershipType type) {
        String insertQuery = null;
        String checkQuery = "select sum(tulajdoni_hanyad) as t " +
                "from tulajdon " +
                "where helyrajzi_szam = ? " +
                "and (ingatlan_azonosito = ? or ? is null)";

        if (type == OwnershipType.PLOT_TYPE) {
            insertQuery = "insert into tulajdon (adoszam, helyrajzi_szam, ingatlan_azonosito, tulajdonba_kerules_datuma, tulajdoni_hanyad) " +
                    "values (?, ?, null, ?, ?)";
        } else if (type == OwnershipType.REAL_ESTATE_TYPE) {
            insertQuery = "insert into tulajdon (adoszam, helyrajzi_szam, ingatlan_azonosito, tulajdonba_kerules_datuma, tulajdoni_hanyad) " +
                    "values (?, ?, ?, ?, ?)";
        }

        try (Connection con = DriverManager.getConnection(url, "root", "")) {
            try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
                checkStmt.setString(1, ownership.getPlot().getPlotNumber());
                if (type == OwnershipType.REAL_ESTATE_TYPE) {
                    checkStmt.setInt(2, ownership.getRealEstate().getID());
                    checkStmt.setInt(3, ownership.getRealEstate().getID());
                } else {
                    checkStmt.setNull(2, java.sql.Types.INTEGER);
                    checkStmt.setNull(3, java.sql.Types.INTEGER);
                }

                ResultSet rs = checkStmt.executeQuery();
                double currentTotal = 0.0;
                if (rs.next()) {
                    currentTotal = rs.getDouble("t");
                }

                if (currentTotal + ownership.getPartOfOwnership() > 1.0) {
                    return false;
                }
            }

            try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                insertStmt.setInt(1, ownership.getUser().getTaxID());
                insertStmt.setString(2, ownership.getPlot().getPlotNumber());
                if (type == OwnershipType.REAL_ESTATE_TYPE) {
                    insertStmt.setInt(3, ownership.getRealEstate().getID());
                    insertStmt.setDate(4, Date.valueOf(ownership.getDateOfPurchase()));
                    insertStmt.setDouble(5, ownership.getPartOfOwnership());
                } else if (type == OwnershipType.PLOT_TYPE) {
                    insertStmt.setDate(3, Date.valueOf(ownership.getDateOfPurchase()));
                    insertStmt.setDouble(4, ownership.getPartOfOwnership());
                }

                int rowsAffected = insertStmt.executeUpdate();
                return rowsAffected > 0;
            }
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

    public int getNumOfRealEstatesByUsername(String username) {
        String query = "select count(*) from tulajdon where adoszam = ? and ingatlan_azonosito is not null";
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
        String query = "select count(*) from tulajdon where adoszam = ? and ingatlan_azonosito is null";
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
                "where t.adoszam = ? and t.ingatlan_azonosito is not null " +
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
                "where tu.adoszam = ? and tu.ingatlan_azonosito is null " +
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
        String query = "select( " +
                "ifnull((select sum(te.becsult_ertek) " +
                "from tulajdon as tu " +
                "inner join telek as te on te.helyrajzi_szam = tu.helyrajzi_szam " +
                "where tu.adoszam = ? and tu.ingatlan_azonosito is null), 0) " +
                "+ " +
                "ifnull((select sum(i.becsult_ertek) " +
                "from tulajdon as tu " +
                "inner join ingatlan as i on i.ingatlan_azonosito = tu.ingatlan_azonosito " +
                "where tu.adoszam = ? and tu.ingatlan_azonosito is not null), 0) " +
                ") as total_value";
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            int taxId = userDao.getUserByName(username).getTaxID();
            stmt.setInt(1, taxId);
            stmt.setInt(2, taxId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ObservableList<Map<String, Object>> getListOfRealEstatesByType(String username){
        String query = """
                select i.jelleg, count(*) as count
                from tulajdon t
                join ingatlan i on t.ingatlan_azonosito = i.ingatlan_azonosito
                where t.adoszam = ?
                group by i.jelleg;""";
        ObservableList<Map<String, Object>> result = FXCollections.observableArrayList();
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            int taxId = userDao.getUserByName(username).getTaxID();
            stmt.setInt(1, taxId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("jelleg", rs.getString("jelleg"));
                row.put("count", rs.getInt("count"));
                result.add(row);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Map<String, Object>> getListOfPlotsByType(String username){
        String query = """
                select te.jelleg, count(*) as count
                from tulajdon tu
                join telek te on tu.helyrajzi_szam = te.helyrajzi_szam
                where tu.adoszam = ? and tu.ingatlan_azonosito is null
                group by te.jelleg;""";
        ObservableList<Map<String, Object>> result = FXCollections.observableArrayList();
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            int taxId = userDao.getUserByName(username).getTaxID();
            stmt.setInt(1, taxId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("jelleg", rs.getString("jelleg"));
                row.put("count", rs.getInt("count"));
                result.add(row);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
