package org.yusufakbas;

import org.yusufakbas.business.UserController;
import org.yusufakbas.core.DatabaseInitializer;
import org.yusufakbas.core.Helper;
import org.yusufakbas.entity.User;
import org.yusufakbas.view.DashboardUI;
import org.yusufakbas.view.LoginUI;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();
        Helper.setTheme();
//        LoginUI loginUI = new LoginUI();
        UserController userController = new UserController();
        User user = userController.findByLogin("test@test.com","P4ssword");
        DashboardUI dashboardUI = new DashboardUI(user);
    }
}