package lk.ijse.elitedrivingschool.controller;

public class DashboardController {

    private static String currentRole;

    public static void setCurrentRole(String role) {
        currentRole = role;
    }
    public static String getCurrentRole() {
        return currentRole;
    }
}
