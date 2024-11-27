package org.personal.markcs.DAO;

import org.personal.markcs.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDaoInterface {
    PreparedStatement stmt;
    ResultSet rs;
    String url = "jdbc:mysql://localhost:3306/ingatlanok";

    @Override
    public User getUserByName(String name) {
        String query = "select * from felhasznalo where nev = ?";
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setString(1, name);

            rs = stmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(
                        rs.getInt("azonosito"),
                        rs.getString("nev"),
                        rs.getString("jelszo"),
                        rs.getInt("adoszam"),
                        rs.getString("lakcim"),
                        rs.getString("telefonszam"),
                        rs.getDate("szuletesi_datum").toLocalDate(),
                        rs.getString("anyja_neve"),
                        rs.getBoolean("bejelentkezve"),
                        rs.getDate("utolso_belepes_idopontja").toLocalDate(),
                        rs.getString("szerepkor")
                );
            }
            con.close();
            return user;
        }catch( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByTaxId(int id) {
        String query = "select * from felhasznalo where adoszam = ?";
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(
                        rs.getInt("azonosito"),
                        rs.getString("nev"),
                        rs.getString("jelszo"),
                        rs.getInt("adoszam"),
                        rs.getString("lakcim"),
                        rs.getString("telefonszam"),
                        rs.getDate("szuletesi_datum").toLocalDate(),
                        rs.getString("anyja_neve"),
                        rs.getBoolean("bejelentkezve"),
                        rs.getDate("utolso_belepes_idopontja").toLocalDate(),
                        rs.getString("szerepkor")
                );
            }
            con.close();
            return user;
        }catch( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String query = "select * from felhasznalo";
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("azonosito"),
                        rs.getString("nev"),
                        rs.getString("jelszo"),
                        rs.getInt("adoszam"),
                        rs.getString("lakcim"),
                        rs.getString("telefonszam"),
                        rs.getDate("szuletesi_datum").toLocalDate(),
                        rs.getString("anyja_neve"),
                        rs.getBoolean("bejelentkezve"),
                        rs.getDate("utolso_belepes_idopontja").toLocalDate(),
                        rs.getString("szerepkor")
                );
                users.add(user);
            }
            return users;
        }catch(Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        String query = "insert into felhasznalo (nev, jelszo, adoszam, lakcim, telefonszam, szuletesi_datum, anyja_neve, " +
                        "bejelentkezve, utolso_belepes_idopontja, szerepkor) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getTaxID());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getPhone());
            stmt.setDate(6, Date.valueOf(user.getBirthdate()));
            stmt.setString(7, user.getMothersMaidenName());
            stmt.setBoolean(8, user.isLoggedIn());
            stmt.setDate(9, Date.valueOf(user.getLastLogin()));
            stmt.setString(10, user.getRole());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        String query = "update felhasznalo set nev = ?, " +
                "jelszo = ?, " +
                "adoszam = ?, " +
                "lakcim = ?, " +
                "telefonszam = ?, " +
                "szuletesi_datum = ?, " +
                "anyja_neve = ? " +
                "where nev = ?";
        try{
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getTaxID());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getPhone());
            stmt.setDate(6, Date.valueOf(user.getBirthdate()));
            stmt.setString(7, user.getMothersMaidenName());
            stmt.setString(8, user.getName());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        }catch(Exception e ) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean setOnline(String name) {
        String query = "update felhasznalo set bejelentkezve = true, utolso_belepes_idopontja = current_date where nev = ?";
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setString(1, name);

            int rowsAffected = stmt.executeUpdate();

            con.close();
            return rowsAffected > 0;

        }catch( Exception e ) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean setOffline(String name) {
        String query = "update felhasznalo set bejelentkezve = false where nev = ?";
        try {
            Connection con = DriverManager.getConnection(url, "root", "");
            stmt = con.prepareStatement(query);
            stmt.setString(1, name);

            int rowsAffected = stmt.executeUpdate();

            con.close();
            return rowsAffected > 0;

        }catch( Exception e ) {
            e.printStackTrace();
        }
        return false;
    }
}
