package org.yusufakbas.controller;

import org.yusufakbas.core.Helper;
import org.yusufakbas.dao.UserDao;
import org.yusufakbas.entity.Users;

public class UserController {

    private final UserDao userDAO = new UserDao();

    public Users findByLogin(String email, String password) {
        if (!Helper.isEmailValid(email)) return null;
        return this.userDAO.findByLogin(email, password);
    }
}
