package org.yusufakbas;

import org.yusufakbas.controller.UserController;
import org.yusufakbas.core.DatabaseInitializer;
import org.yusufakbas.core.Helper;
import org.yusufakbas.entity.Users;
import org.yusufakbas.view.DashboardUI;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();
        Helper.setTheme();
//        LoginUI loginUI = new LoginUI();
        UserController userController = new UserController();
        Users users = userController.findByLogin("john.doe@example.com","password123");
        DashboardUI dashboardUI = new DashboardUI(users);
    }
}