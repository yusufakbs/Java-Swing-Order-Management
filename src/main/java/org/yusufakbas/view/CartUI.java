package org.yusufakbas.view;

import org.yusufakbas.controller.BasketController;
import org.yusufakbas.controller.CartController;
import org.yusufakbas.controller.ProductController;
import org.yusufakbas.core.Helper;
import org.yusufakbas.entity.Basket;
import org.yusufakbas.entity.Cart;
import org.yusufakbas.entity.Customer;
import org.yusufakbas.entity.Product;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CartUI extends JFrame {
    private JPanel container;
    private JLabel lbl_title;
    private JLabel lbl_customer_name;
    private JTextField fld_cart_date;
    private JTextArea tarea_cart_note;
    private JButton btn_cart;
    private Customer customer;
    private BasketController basketController;
    private CartController cartController;
    private ProductController productController;

    public CartUI(Customer customer) {
        this.customer = customer;
        this.basketController = new BasketController();
        this.cartController = new CartController();
        this.productController = new ProductController();

        this.add(container);
        this.setTitle("Product Add/Edit");
        this.setSize(300, 350);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        if (customer.getId() == 0) {
            Helper.showMsg("Please select a valid customer!");
            dispose();
        }

        ArrayList<Basket> baskets = this.basketController.findAll();
        if (baskets.size() == 0) {
            Helper.showMsg("No baskets found!");
            dispose();
        }

        this.lbl_customer_name.setText("Customer :" + this.customer.getName());

        btn_cart.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.fld_cart_date)) {
                Helper.showMsg("fill");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                for (Basket basket : baskets) {
                    if (basket.getProduct().getStock() <= 0) continue;
                    Cart cart = new Cart();
                    cart.setCustomerId(this.customer.getId());
                    cart.setProductId(basket.getProductId());
                    cart.setPrice(basket.getProduct().getPrice());
                    cart.setDate(LocalDate.parse(this.fld_cart_date.getText(), formatter));
                    cart.setNote(this.tarea_cart_note.getText());
                    this.cartController.save(cart);

                    Product unStockProduct = basket.getProduct();
                    unStockProduct.setStock(unStockProduct.getStock() - 1);
                    this.productController.updateProduct(unStockProduct);

                }
                this.basketController.clear();
                Helper.showMsg("done");
                dispose();
            }
        });
    }

    private void createUIComponents() throws ParseException {
        // TODO: place custom component creation code here
        this.fld_cart_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fld_cart_date.setText(formatter.format(LocalDate.now()));
    }

}
