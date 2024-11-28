package org.personal.markcs.DAO;

import org.personal.markcs.Model.Plot;
import org.personal.markcs.Model.RealEstate;
import org.personal.markcs.Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class RealEstateDaoImpl implements RealEstateDaoInterface{
    PreparedStatement stmt;
    ResultSet rs;
    String url = "jdbc:mysql://localhost:3306/ingatlanok";
    PlotDaoImpl plotDao = new PlotDaoImpl();

    @Override
    public boolean addRealEstate(RealEstate realEstate) {
        String query = "insert into ingatlan (jelleg, epites_eve, iranyitoszam, telepules, kozterulet_es_hazszam, becsult_ertek) values (?, ?, ?, ?, ?, ?)";
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setString(1, realEstate.getType());
            stmt.setInt(2, realEstate.getTimeOfConstruction());
            stmt.setInt(3, realEstate.getPostalCode());
            stmt.setString(4, realEstate.getSettlement());
            stmt.setString(5, realEstate.getStreetAndHouseNumber());
            stmt.setInt(6, realEstate.getApproxValue());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public RealEstate getRealEstateById(int id) {
        String query = "select * from ingatlan where ingatlan_azonosito = ?";
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            RealEstate realEstate = null;
            if (rs.next()) {
                realEstate = new RealEstate(
                        rs.getInt("ingatlan_azonosito"),
                        new Plot(),
                        rs.getString("jelleg"),
                        rs.getInt("epites_eve"),
                        rs.getInt("iranyitoszam"),
                        rs.getString("telepules"),
                        rs.getString("kozterulet_es_hazszam"),
                        rs.getInt("becsult_ertek")
                );
            }

            return realEstate;
        }catch(Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RealEstate> getAllRealEstates() {
        return List.of();
    }

    public int getRealEstateID(RealEstate realEstate){
        String query = "select ingatlan_azonosito from ingatlan where jelleg = ? and epites_eve = ? and iranyitoszam = ? and telepules = ? and kozterulet_es_hazszam = ? and becsult_ertek = ?";
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setString(1, realEstate.getType());
            stmt.setInt(2, realEstate.getTimeOfConstruction());
            stmt.setInt(3, realEstate.getPostalCode());
            stmt.setString(4, realEstate.getSettlement());
            stmt.setString(5, realEstate.getStreetAndHouseNumber());
            stmt.setInt(6, realEstate.getApproxValue());
            rs = stmt.executeQuery();

            if(rs.next()) {
                return rs.getInt("ingatlan_azonosito");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public boolean deleteRealEstate(RealEstate realEstate){
        String query = "delete from ingatlan where ingatlan_azonosito = ?";
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setInt(1, realEstate.getID());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
