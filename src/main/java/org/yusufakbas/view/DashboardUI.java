package org.yusufakbas.view;

import org.yusufakbas.business.CustomerController;
import org.yusufakbas.core.Helper;
import org.yusufakbas.entity.Customer;
import org.yusufakbas.entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DashboardUI extends JFrame {
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JTabbedPane pnl_customer;
    private JScrollPane scrl_customer;
    private JTable tb_customer;
    private JPanel pnl_customer_filter;
    private JTextField fld_filter_customer_name;
    private JComboBox cmb_customer_type;
    private JButton btn_customer_filter;
    private JButton btn_customer_new;
    private JLabel lbl_filter_customer_name;
    private JLabel lbl_filter_customer_type;
    private JButton btn_customer_filter_reset;
    private User user;
    private CustomerController customerController;
    private DefaultTableModel tmdl_customer = new DefaultTableModel();
    private JPopupMenu popup_customer = new JPopupMenu();


    public DashboardUI(User user) {
        this.user = user;
        this.customerController = new CustomerController();
        if (user == null) {
            Helper.showMsg("error");
            dispose();
        }


        this.add(container);
        this.setTitle("Customer Management System");
        this.setSize(1000, 500);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        this.lbl_welcome.setText("Welcome " + this.user.getName());
        this.btn_logout.addActionListener(e -> {
            dispose();
            LoginUI loginUI = new LoginUI();
        });

        loadCustomerTable(null);
        loadCustomerPopUpMenu();

    }

    private void loadCustomerPopUpMenu() {
        this.tb_customer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tb_customer.rowAtPoint(e.getPoint());
                tb_customer.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });
        this.popup_customer.add("Update").addActionListener(e -> {
            int selectedId = Integer.parseInt(tb_customer.getValueAt(tb_customer.getSelectedRow(), 0).toString());
            System.out.println(selectedId);
        });
        this.popup_customer.add("Delete").addActionListener(e -> {
            System.out.println("Delete clicked");
        });
        this.tb_customer.setComponentPopupMenu(this.popup_customer);
    }

    private void loadCustomerTable(ArrayList<Customer> customers) {
        Object[] columnCustomer = {"ID", "Customer Name", "Customer Type", "Phone", "E-mail", "Address"};
        this.tmdl_customer.setColumnIdentifiers(columnCustomer);
        if (customers == null) {
            customers = this.customerController.findAll();
        }

        // Table reset
        DefaultTableModel clearModel = (DefaultTableModel) this.tb_customer.getModel();
        clearModel.setRowCount(0);

        for (Customer customer : customers) {
            Object[] rowCustomer = {customer.getId(), customer.getName(), customer.getType(), customer.getPhone(), customer.getEmail(), customer.getAddress()};
            this.tmdl_customer.addRow(rowCustomer);
        }

        this.tb_customer.setModel(this.tmdl_customer);
        this.tb_customer.getTableHeader().setReorderingAllowed(false);
        this.tb_customer.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.tb_customer.setEnabled(false);
    }


}
