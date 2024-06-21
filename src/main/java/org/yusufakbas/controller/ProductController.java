package org.yusufakbas.controller;

import org.yusufakbas.core.Helper;
import org.yusufakbas.dao.ProductDao;
import org.yusufakbas.entity.Product;

import java.util.ArrayList;

public class ProductController {
    private final ProductDao productDao = new ProductDao();

    public ArrayList<Product> findAll() {
        return this.productDao.findAll();
    }

    public boolean saveProduct(Product product) {
        return this.productDao.saveProduct(product);
    }

    public Product findById(int id) {
        return this.productDao.findProductById(id);
    }

    public boolean updateProduct(Product product) {
        if (this.findById(product.getId()) == null) {
            Helper.showMsg(product.getId() + " not found");
            return false;
        }
        return this.productDao.updateProduct(product);
    }

    public boolean deleteProduct(int id) {
        if (this.findById(id) == null) {
            Helper.showMsg(id + " not found");
            return false;
        }
        return this.productDao.deleteProduct(id);
    }

}
