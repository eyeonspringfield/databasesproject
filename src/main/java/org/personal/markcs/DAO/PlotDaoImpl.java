package org.personal.markcs.DAO;

import org.personal.markcs.Model.Plot;

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
    public Plot getPlotById(int id) {
        return null;
    }

    @Override
    public List<Plot> getAllPlots() {
        return List.of();
    }
}
