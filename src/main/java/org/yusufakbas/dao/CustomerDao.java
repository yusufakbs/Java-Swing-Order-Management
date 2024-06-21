package org.yusufakbas.dao;

import org.yusufakbas.core.DatabaseConnection;
import org.yusufakbas.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDao {
    private Connection connection;

    public CustomerDao() {
        this.connection = DatabaseConnection.getInstance();
    }

    public ArrayList<Customer> findAll() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                customers.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public boolean saveCustomer(Customer customer) {
        String query = "INSERT INTO customer (name, type, phone, mail, address) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getType().toString());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getAddress());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public Customer findCustomerById(int id) {
        Customer customer = null;
        String query = "SELECT * FROM customer WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = this.match(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customer;
    }

    public boolean updateCustomer(Customer customer) {
        String query = "UPDATE customer SET name = ?, type = ?, phone = ?, mail = ?, address = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getType().toString());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getAddress());
            preparedStatement.setInt(6, customer.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean deleteCustomer(int id) {
        String query = "DELETE FROM customer WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Customer> customerQuery(String query) {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                customers.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;

    }

    public Customer match(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("mail"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        customer.setType(Customer.TYPE.valueOf(rs.getString("type")));
        return customer;
    }

}
