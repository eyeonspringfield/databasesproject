package org.personal.markcs.DAO;

import org.personal.markcs.Model.Plot;
import org.personal.markcs.Model.User;

import java.sql.*;
import java.util.List;

public class PlotDaoImpl implements PlotDaoInterface{
    PreparedStatement stmt;
    ResultSet rs;
    String url = "jdbc:mysql://localhost:3306/ingatlanok";

    @Override
    public boolean addPlot(Plot plot) {
        String query = "insert into telek (helyrajzi_szam, jelleg, meret, becsult_ertek) values (?, ?, ?, ?)";
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setString(1, plot.getPlotNumber());
            stmt.setString(2, plot.getType());
            stmt.setInt(3, plot.getSize());
            stmt.setInt(4, plot.getApproxValue());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Plot getPlotByPlotNumber(String id) {
        String query = "select * from telek where helyrajzi_szam = ?";
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setString(1, id);
            rs = stmt.executeQuery();

            Plot plot = null;
            if (rs.next()) {
                plot = new Plot(
                        rs.getString("helyrajzi_szam"),
                        rs.getString("jelleg"),
                        rs.getInt("meret"),
                        rs.getInt("becsult_ertek")
                );
            }

            return plot;
        }catch(Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Plot> getAllPlots() {
        return List.of();
    }

    public boolean deletePlot(Plot plot) {
        String query = "delete from telek where helyrajzi_szam = ?";
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setString(1, plot.getPlotNumber());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePlot(Plot plot) {
        String query = "update telek set jelleg = ?, " +
                "meret = ?, " +
                "becsult_ertek = ? " +
                "where helyrajzi_szam = ?";
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setString(1, plot.getType());
            stmt.setInt(2, plot.getSize());
            stmt.setInt(3, plot.getApproxValue());
            stmt.setString(4, plot.getPlotNumber());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
