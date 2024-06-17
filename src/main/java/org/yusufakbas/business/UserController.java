package org.yusufakbas.business;

import org.yusufakbas.core.Helper;
import org.yusufakbas.dao.UserDAO;
import org.yusufakbas.entity.User;

public class UserController {
    private final UserDAO userDAO = new UserDAO();

    public User findByLogin(String email, String password) {
        if (!Helper.isEmailValid(email)) return null;
        return this.userDAO.findByLogin(email, password);
    }
}
