package org.yusufakbas.controller;

import org.yusufakbas.core.Helper;
import org.yusufakbas.core.Item;
import org.yusufakbas.dao.ProductDao;
import org.yusufakbas.entity.Product;

import java.util.ArrayList;

public class ProductController {
    private final ProductDao productDao = new ProductDao();

    public ArrayList<Product> findAll() {
        return this.productDao.findAll();
    }

    public boolean saveProduct(Product product) {
        return this.productDao.save(product);
    }

    public Product getById(int id) {
        return this.productDao.getById(id);
    }

    public boolean updateProduct(Product product) {
        if (this.getById(product.getId()) == null) {
            Helper.showMsg(product.getId() + " not found");
            return false;
        }
        return this.productDao.update(product);
    }

    public boolean deleteProduct(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + " not found");
            return false;
        }
        return this.productDao.delete(id);
    }

    public ArrayList<Product> filterProduct(String name, String code, Item isStock) {
        String query = "SELECT * FROM product";
        ArrayList<String> whereList = new ArrayList<>();
        if (name.length() > 0) {
            whereList.add("name LIKE '%" + name + "%'");
        }
        if (code.length() > 0) {
            whereList.add("code LIKE '%" + code + "%'");
        }
        if (isStock != null) {
            if (isStock.getKey() == 1) {
                whereList.add("stock > 0");
            } else {
                whereList.add("stock <= 0");
            }
        }
        if (whereList.size() > 0) {
            String whereQuery = String.join(" AND ", whereList);
            query += " WHERE " + whereQuery;
        }
        return this.productDao.productsQuery(query);
    }

}
