package org.yusufakbas;

import org.yusufakbas.core.DatabaseConnection;
import org.yusufakbas.core.Helper;
import org.yusufakbas.view.LoginUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Helper.setTheme();
        LoginUI loginUI = new LoginUI();
    }
}