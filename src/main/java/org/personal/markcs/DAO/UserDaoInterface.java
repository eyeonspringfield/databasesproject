package org.personal.markcs.DAO;

import org.personal.markcs.Model.User;

import java.util.List;

public interface UserDaoInterface {
    public User getUserByName(String name);
    public User getUserByTaxId(int id);
    public List<User> getAllUsers();
    public boolean addUser(User user);
    public boolean updateUser(User user);
    public boolean setOnline(String name);
    public boolean setOffline(String name);
}
