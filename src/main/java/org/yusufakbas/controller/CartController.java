package org.yusufakbas.controller;

import org.yusufakbas.dao.CartDao;
import org.yusufakbas.entity.Cart;

import java.util.ArrayList;

public class CartController {
    private final CartDao cartDao = new CartDao();

    public boolean save(Cart cart) {
        return this.cartDao.save(cart);
    }

    public ArrayList<Cart> findAll() {
        return this.cartDao.findAll();
    }

}
