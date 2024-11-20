package org.personal.markcs.Model;

public class Session {
    private static String name;
    private static boolean isAdmin;
    private static boolean isAuthenticated;

    private Session(){}

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Session.name = name;
    }

    public static boolean isAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        Session.isAdmin = isAdmin;
    }

    public static boolean isAuthenticated() {
        return isAuthenticated;
    }

    public static void setIsAuthenticated(boolean isAuthenticated) {
        Session.isAuthenticated = isAuthenticated;
    }
}
