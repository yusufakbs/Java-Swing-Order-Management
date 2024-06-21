package org.yusufakbas.controller;

import org.yusufakbas.core.Helper;
import org.yusufakbas.dao.CustomerDao;
import org.yusufakbas.entity.Customer;

import java.util.ArrayList;

public class CustomerController {
    private final CustomerDao customerDAO = new CustomerDao();

    public ArrayList<Customer> findAll() {
        return this.customerDAO.findAll();
    }

    public boolean saveCustomer(Customer customer) {
        return this.customerDAO.saveCustomer(customer);
    }

    public Customer findCustomerById(int id) {
        return this.customerDAO.findCustomerById(id);
    }

    public boolean updateCustomer(Customer customer) {
        if (this.findCustomerById(customer.getId()) == null) {
            Helper.showMsg(customer.getId() + " not found");
            return false;
        } else {
            return this.customerDAO.updateCustomer(customer);
        }
    }

    public boolean deleteCustomer(int id) {
        if (this.findCustomerById(id) == null) {
            Helper.showMsg(id + " not found");
            return false;
        }
        return this.customerDAO.deleteCustomer(id);
    }


    public ArrayList<Customer> filterCustomers(String name, Customer.TYPE type) {
        String query = "SELECT * FROM customer";
        ArrayList<String> whereList = new ArrayList<>();
        if (name.length() > 0) {
            whereList.add("name LIKE '%" + name + "%'");
        }
        if (type != null) {
            whereList.add("type = '" + type.toString() + "'");
        }
        if (whereList.size() > 0) {
            query += " WHERE " + String.join(" AND ", whereList);
        }
        return this.customerDAO.customerQuery(query);
    }

}
