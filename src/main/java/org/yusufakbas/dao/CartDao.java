package org.yusufakbas.dao;

import org.yusufakbas.core.DatabaseConnection;
import org.yusufakbas.entity.Cart;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CartDao {
    private Connection connection;
    private ProductDao productDao;
    private CustomerDao customerDao;

    public CartDao() {
        this.connection = DatabaseConnection.getInstance();
        this.productDao = new ProductDao();
        this.customerDao = new CustomerDao();
    }

    public ArrayList<Cart> findAll() {
        ArrayList<Cart> carts = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM cart");
            while (rs.next()) {
                carts.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carts;
    }

    public boolean save(Cart cart) {
        String query = "INSERT INTO cart (customer_id, product_id, price, date, note) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, cart.getCustomerId());
            preparedStatement.setInt(2, cart.getProductId());
            preparedStatement.setDouble(3, cart.getPrice());
            preparedStatement.setDate(4, Date.valueOf(cart.getDate()));
            preparedStatement.setString(5, cart.getNote());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }


    public Cart match(ResultSet rs) throws SQLException {
        Cart cart = new Cart();
        cart.setId(rs.getInt("id"));
        cart.setPrice(rs.getInt("price"));
        cart.setCustomerId(rs.getInt("customer_id"));
        cart.setProductId(rs.getInt("product_id"));
        cart.setNote(rs.getString("note"));
        cart.setDate(LocalDate.parse(rs.getString("date")));
        cart.setCustomer(this.customerDao.getById(cart.getCustomerId()));
        cart.setProduct(this.productDao.getById(cart.getProductId()));
        return cart;
    }

}
