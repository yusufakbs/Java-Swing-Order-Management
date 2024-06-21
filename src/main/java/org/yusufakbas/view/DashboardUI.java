package org.yusufakbas.view;

import org.yusufakbas.business.CustomerController;
import org.yusufakbas.core.Helper;
import org.yusufakbas.entity.Customer;
import org.yusufakbas.entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
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
    private JComboBox cmb_f_customer_type;
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

        //CUSTOMER TAB
        loadCustomerTable(null);
        loadCustomerPopUpMenu();
        loadCustomerButtonEvent();

        this.cmb_f_customer_type.setModel(new DefaultComboBoxModel<>(Customer.TYPE.values()));
        this.cmb_f_customer_type.setSelectedItem(null);

        filterCustomerButtonEvent();
        filterResetButtonEvent();
    }

    private void filterCustomerButtonEvent() {
        btn_customer_filter.addActionListener(e -> {
            ArrayList<Customer> filteredCustomers = this.customerController.filterCustomers(this.fld_filter_customer_name.getText(), (Customer.TYPE) this.cmb_f_customer_type.getSelectedItem());
            loadCustomerTable(filteredCustomers);
        });
    }

    private void filterResetButtonEvent() {
        btn_customer_filter_reset.addActionListener(e -> {
            loadCustomerTable(null);
            this.fld_filter_customer_name.setText(null);
            this.cmb_f_customer_type.setSelectedItem(null);
        });
    }

    private void loadCustomerButtonEvent() {
        btn_customer_new.addActionListener(e -> {
            CustomerUI customerUI = new CustomerUI(new Customer());
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    loadCustomerTable(null);
                }
            });
        });
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
            CustomerUI customerUI = new CustomerUI(this.customerController.findCustomerById(selectedId));
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    loadCustomerTable(null);
                }
            });

        });

        this.popup_customer.add("Delete").addActionListener(e -> {
            int selectedId = Integer.parseInt(tb_customer.getValueAt(tb_customer.getSelectedRow(), 0).toString());
            if (Helper.confirm("sure")) {
                if (this.customerController.deleteCustomer(selectedId)) {
                    Helper.showMsg("done");
                    loadCustomerTable(null);
                } else {
                    Helper.showMsg("error");
                }
            }
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
