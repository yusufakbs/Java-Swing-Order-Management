package org.yusufakbas.view;

import org.yusufakbas.business.UserController;
import org.yusufakbas.core.Helper;
import org.yusufakbas.entity.User;

import javax.swing.*;
import java.awt.*;


public class LoginUI extends JFrame {
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_title;
    private JPanel pnl_bottom;
    private JTextField fld_email;
    private JButton btn_login;
    private JLabel lbl_email;
    private JLabel lbl_password;
    private JPasswordField fld_password;
    private UserController userController;

    public LoginUI() {
        this.add(container);
        this.userController = new UserController();
        this.setTitle("Customer Management System");
        this.setSize(400, 400);
        centerFrame();

        this.btn_login.addActionListener(e -> {
            JTextField[] checkList = {this.fld_email, this.fld_password};
            if (!Helper.isEmailValid(this.fld_email.getText())) {
                Helper.showMsg("Please enter a valid e-mail address");
            } else if (Helper.isFieldListEmpty(checkList)) {
                Helper.showMsg("fill");
            } else {
                User user = this.userController.findByLogin(this.fld_email.getText(), this.fld_password.getText());
                if (user == null) {
                    Helper.showMsg("User not found");
                } else {
                    this.dispose();
                    DashboardUI dashboardUI = new DashboardUI(user);
                }
            }
        });

    }

    private void centerFrame() {
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);
    }

}
