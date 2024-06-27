package org.yusufakbas.controller;

import org.yusufakbas.dao.BasketDao;
import org.yusufakbas.entity.Basket;


import java.util.ArrayList;

public class BasketController {
    private final BasketDao basketDao = new BasketDao();

    public boolean save(Basket basket) {
        return basketDao.save(basket);
    }

    public ArrayList<Basket> findAll() {
        return this.basketDao.findAll();
    }

    public boolean clear() {
        return this.basketDao.clear();
    }

}
