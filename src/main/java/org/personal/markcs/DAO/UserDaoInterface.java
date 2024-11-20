package org.personal.markcs.DAO;

import org.personal.markcs.Model.User;

public interface UserDaoInterface {
    public User getUserByName(String name);
    public User getUserById(int id);
    public boolean addUser(User user);
    public boolean setOnline(String name);
    public boolean setOffline(String name);
}
