package org.yusufakbas.dao;

import org.yusufakbas.core.DatabaseConnection;
import org.yusufakbas.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        this.connection = DatabaseConnection.getInstance();
    }

    public User findByLogin(String mail, String password) {
        User user = null;
        String query = "SELECT * FROM users WHERE mail = ? AND password = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, mail);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public ArrayList<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM user");
            while (rs.next()) {
                users.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    public User match(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("mail"));
        user.setPassword(rs.getString("password"));
        return user;
    }

}
