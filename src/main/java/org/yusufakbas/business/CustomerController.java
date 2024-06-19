package org.yusufakbas.business;

import org.yusufakbas.core.Helper;
import org.yusufakbas.dao.CustomerDAO;
import org.yusufakbas.entity.Customer;

import java.util.ArrayList;

public class CustomerController {
    private final CustomerDAO customerDAO = new CustomerDAO();

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
        if(this.findCustomerById(id) == null) {
            Helper.showMsg(id + " not found");
            return false;
        }
        return this.customerDAO.deleteCustomer(id);
    }

}
