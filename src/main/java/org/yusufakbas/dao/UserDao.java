package org.yusufakbas.dao;

import org.yusufakbas.core.DatabaseConnection;
import org.yusufakbas.entity.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private Connection connection;

    public UserDao() {
        this.connection = DatabaseConnection.getInstance();
    }

    public Users findByLogin(String mail, String password) {
        Users users = null;
        String query = "SELECT * FROM users WHERE mail = ? AND password = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, mail);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                users = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public ArrayList<Users> findAll() {
        ArrayList<Users> users = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    public Users match(ResultSet rs) throws SQLException {
        Users users = new Users();
        users.setId(rs.getInt("id"));
        users.setName(rs.getString("name"));
        users.setEmail(rs.getString("mail"));
        users.setPassword(rs.getString("password"));
        return users;
    }

}
