package org.yusufakbas.view;

import org.yusufakbas.controller.CustomerController;
import org.yusufakbas.core.Helper;
import org.yusufakbas.entity.Customer;

import javax.swing.*;
import java.awt.*;

public class CustomerUI extends JFrame {
    private JPanel container;
    private JLabel lbl_title;
    private JLabel lbl_name;
    private JTextField fld_customer_name;
    private JLabel lbl_type;
    private JComboBox<Customer.TYPE> cmb_customer_type;
    private JLabel lbl_customer_phone;
    private JTextField fld_customer_phone;
    private JLabel lbl_customer_email;
    private JTextField fld_customer_email;
    private JLabel lbl_customer_address;
    private JTextArea tarea_customer_address;
    private JButton btn_customer_save;
    private Customer customer;
    private CustomerController customerController;

    public CustomerUI(Customer customer) {
        this.customer = customer;
        this.customerController = new CustomerController();

        this.add(container);
        this.setTitle("Customer Add/Edit");
        this.setSize(300, 500);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        this.cmb_customer_type.setModel(new DefaultComboBoxModel<>(Customer.TYPE.values()));

        if (this.customer.getId() == 0) {
            this.lbl_title.setText("Add Customer");
        } else {
            this.lbl_title.setText("Edit Customer");
            this.fld_customer_name.setText(this.customer.getName());
            this.fld_customer_phone.setText(this.customer.getPhone());
            this.fld_customer_email.setText(this.customer.getEmail());
            this.cmb_customer_type.setSelectedItem(this.customer.getType());
            this.tarea_customer_address.setText(this.customer.getAddress());
        }

        btn_customer_save.addActionListener(e -> {
            JTextField[] checkList = {this.fld_customer_name, this.fld_customer_phone};
            if(Helper.isFieldListEmpty(checkList)){
                Helper.showMsg("fill");
            }else if(!Helper.isFieldEmpty(this.fld_customer_email) && !Helper.isEmailValid(this.fld_customer_email.getText())){
                Helper.showMsg("Pleate enter a valid email");
            }else {
                boolean result = false;
                this.customer.setName(this.fld_customer_name.getText());
                this.customer.setPhone(this.fld_customer_phone.getText());
                this.customer.setType((Customer.TYPE) this.cmb_customer_type.getSelectedItem());
                this.customer.setAddress(this.tarea_customer_address.getText());
                this.customer.setEmail(this.fld_customer_email.getText());
                if(this.customer.getId() == 0){
                    result = this.customerController.saveCustomer(this.customer);
                }else {
                    result = this.customerController.updateCustomer(this.customer);
                }
                if(result){
                    Helper.showMsg("Done");
                }else {
                    Helper.showMsg("error");
                }
            }
        });
    }

}
