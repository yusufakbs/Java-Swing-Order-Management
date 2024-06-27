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

    public boolean save(Customer customer) {
        return this.customerDAO.save(customer);
    }

    public Customer getById(int id) {
        return this.customerDAO.getById(id);
    }

    public boolean update(Customer customer) {
        if (this.getById(customer.getId()) == null) {
            Helper.showMsg(customer.getId() + " not found");
            return false;
        } else {
            return this.customerDAO.update(customer);
        }
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + " not found");
            return false;
        }
        return this.customerDAO.delete(id);
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
