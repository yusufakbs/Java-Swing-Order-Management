package org.yusufakbas.business;

import org.yusufakbas.dao.CustomerDAO;
import org.yusufakbas.entity.Customer;

import java.util.ArrayList;

public class CustomerController {
    private final CustomerDAO customerDAO = new CustomerDAO();

    public ArrayList<Customer> findAll() {
        return this.customerDAO.findAll();
    }
}
