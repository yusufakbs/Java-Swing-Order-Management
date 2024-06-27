package org.yusufakbas.view;

import org.yusufakbas.controller.BasketController;
import org.yusufakbas.controller.CartController;
import org.yusufakbas.controller.CustomerController;
import org.yusufakbas.controller.ProductController;
import org.yusufakbas.core.Helper;
import org.yusufakbas.core.Item;
import org.yusufakbas.entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DashboardUI extends JFrame {
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JTabbedPane tab_menu;
    private JScrollPane scrl_customer;
    private JTable tbl_customer;
    private JPanel pnl_customer_filter;
    private JTextField fld_filter_customer_name;
    private JComboBox cmb_f_customer_type;
    private JButton btn_customer_filter;
    private JButton btn_customer_new;
    private JLabel lbl_filter_customer_name;
    private JLabel lbl_filter_customer_type;
    private JButton btn_customer_filter_reset;
    private JPanel pnl_customer;
    private JPanel pnl_product;
    private JScrollPane scrl_product;
    private JTable tbl_product;
    private JPanel pnl_product_filter;
    private JTextField fld_f_product_name;
    private JTextField fld_f_product_code;
    private JComboBox<Item> cmb_f_product_stock;
    private JButton btn_product_filter;
    private JButton btn_product_clear;
    private JButton btn_product_new;
    private JLabel lbl_f_product_name;
    private JLabel lbl_f_product_stock;
    private JLabel lbl_f_product_code;
    private JPanel pnl_basket;
    private JPanel pnl_basket_top;
    private JScrollPane scrl_basket;
    private JComboBox<Item> cmb_basket_customer;
    private JButton btn_basket_reset;
    private JButton btn_basket_new;
    private JLabel lbl_basket_price;
    private JLabel lbl_basket_pcs;
    private JTable tbl_basket;
    private JScrollPane scrl_cart;
    private JTable tbl_cart;

    private Users users;

    private CustomerController customerController;
    private ProductController productController;
    private BasketController basketController;
    private CartController cartController;

    private DefaultTableModel tmdl_customer = new DefaultTableModel();
    private DefaultTableModel tmdl_product = new DefaultTableModel();
    private DefaultTableModel tmdl_basket = new DefaultTableModel();
    private DefaultTableModel tmdl_cart = new DefaultTableModel();

    private JPopupMenu popup_customer = new JPopupMenu();
    private JPopupMenu popup_product = new JPopupMenu();


    public DashboardUI(Users users) {
        this.users = users;
        this.customerController = new CustomerController();
        this.productController = new ProductController();
        this.basketController = new BasketController();
        this.cartController = new CartController();

        if (users == null) {
            Helper.showMsg("error");
            dispose();
        }

        this.add(container);
        this.setTitle("Customer Management System");
        this.setSize(1000, 500);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);
        this.lbl_welcome.setText("Welcome " + this.users.getName());
        this.btn_logout.addActionListener(e -> {
            dispose();
            LoginUI loginUI = new LoginUI();
        });

        //CUSTOMER TAB
        loadCustomerTable(null);
        loadCustomerPopUpMenu();
        loadCustomerButtonEvent();
        this.cmb_f_customer_type.setModel(new DefaultComboBoxModel<>(Customer.TYPE.values()));
        this.cmb_f_customer_type.setSelectedItem(null);
        filterCustomerButtonEvent();
        filterResetButtonEvent();

        //Product TAB
        loadProductTable(null);
        loadProductPopUpMenu();
        loadProductButtonEvent();
        this.cmb_f_product_stock.addItem(new Item(1, "In Stock"));
        this.cmb_f_product_stock.addItem(new Item(2, "Out Stock"));
        this.cmb_f_product_stock.setSelectedItem(null);

        //Basket TAB
        loadBasketTable();
        loadBasketButtonEvent();
        loadBasketCustomerCombo();

        //Cart TAB
        loadCartTable();
    }

    private void loadCartTable() {
        Object[] columnBasket = {"Id", "Customer Name", "Product Name", "Price", "Order Date", "Note"};
        ArrayList<Cart> carts = this.cartController.findAll();

        // Table reset
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_cart.getModel();
        clearModel.setRowCount(0);

        this.tmdl_cart.setColumnIdentifiers(columnBasket);


        for (Cart cart : carts) {
            Object[] rowObject = {cart.getId(), cart.getCustomer().getName(), cart.getProduct().getName(), cart.getPrice(), cart.getDate(), cart.getNote()};
            this.tmdl_cart.addRow(rowObject);

        }

        this.tbl_cart.setModel(this.tmdl_cart);
        this.tbl_cart.getTableHeader().setReorderingAllowed(false);
        this.tbl_cart.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.tbl_cart.setEnabled(false);
    }

    private void loadBasketCustomerCombo() {
        ArrayList<Customer> customers = this.customerController.findAll();
        this.cmb_basket_customer.removeAllItems();
        for (Customer customer : customers) {
            int comboKey = customer.getId();
            String comboValue = customer.getName();
            this.cmb_basket_customer.addItem(new Item(comboKey, comboValue));

        }

        cmb_basket_customer.setSelectedItem(null);
    }

    private void loadBasketButtonEvent() {

        this.btn_basket_reset.addActionListener(e -> {
            if (this.basketController.clear()) {
                Helper.showMsg("done");
                loadBasketTable();
            } else {
                Helper.showMsg("error");
            }
        });

        this.btn_basket_new.addActionListener(e -> {
            Item selectedCustomer = (Item) this.cmb_basket_customer.getSelectedItem();
            if (selectedCustomer == null) {
                Helper.showMsg("Please select a valid customer");
            } else {
                Customer customer = this.customerController.findCustomerById(selectedCustomer.getKey());
                ArrayList<Basket> baskets = this.basketController.findAll();
                if (customer.getId() == 0) {
                    Helper.showMsg("Customer not found");
                } else if (baskets.isEmpty()) {
                    Helper.showMsg("Please add product to the basket");
                } else {
                    CartUI cartUI = new CartUI(customer);
                    cartUI.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            loadBasketTable();
                            loadProductTable(null);
                            loadCartTable();
                        }
                    });
                }
            }
        });

    }

    private void loadBasketTable() {
        Object[] columnBasket = {"Id", "Product Name", "Product Code", "Price", "Stock"};
        ArrayList<Basket> baskets = this.basketController.findAll();

        // Table reset
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_basket.getModel();
        clearModel.setRowCount(0);

        this.tmdl_basket.setColumnIdentifiers(columnBasket);

        int totalPrice = 0;
        for (Basket basket : baskets) {
            Object[] rowObject = {basket.getId(), basket.getProduct().getName(), basket.getProduct().getCode(), basket.getProduct().getPrice(), basket.getProduct().getStock(),};
            this.tmdl_basket.addRow(rowObject);
            totalPrice += basket.getProduct().getPrice();
        }

        this.lbl_basket_price.setText(String.valueOf(totalPrice) + " USD");
        this.lbl_basket_pcs.setText(String.valueOf(baskets.size()) + " pcs");
        this.tbl_basket.setModel(this.tmdl_basket);
        this.tbl_basket.getTableHeader().setReorderingAllowed(false);
        this.tbl_basket.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.tbl_basket.setEnabled(false);

    }

    private void loadProductButtonEvent() {
        btn_product_new.addActionListener(e -> {
            ProductUI productUI = new ProductUI(new Product());
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                }
            });
        });

        btn_product_filter.addActionListener(e -> {
            ArrayList<Product> filteredProducts = this.productController.filterProduct(this.fld_f_product_name.getText(), this.fld_f_product_code.getText(), (Item) this.cmb_f_product_stock.getSelectedItem());
            loadProductTable(filteredProducts);

        });


        btn_product_clear.addActionListener(e -> {
            this.fld_f_product_code.setText(null);
            this.fld_f_product_name.setText(null);
            this.cmb_f_product_stock.setSelectedItem(null);
            loadProductTable(null);
        });

    }

    private void loadProductPopUpMenu() {
        this.tbl_product.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_product.rowAtPoint(e.getPoint());
                tbl_product.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.popup_product.add("Add to Cart").addActionListener(e -> {
            int selectedId = Integer.parseInt(this.tbl_product.getValueAt(this.tbl_product.getSelectedRow(), 0).toString());
            Product basketProduct = this.productController.findById(selectedId);
            if (basketProduct.getStock() <= 0) {
                Helper.showMsg("This product is not in stock!");
            } else {
                Basket basket = new Basket(basketProduct.getId());
                if (this.basketController.saveBasket(basket)) {
                    Helper.showMsg("done");
                    loadBasketTable();
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        this.popup_product.add("Update").addActionListener(e -> {
            int selectedId = Integer.parseInt(this.tbl_product.getValueAt(this.tbl_product.getSelectedRow(), 0).toString());
            ProductUI productUI = new ProductUI(this.productController.findById(selectedId));
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                    loadBasketTable();
                }
            });
        });

        this.popup_product.add("Delete").addActionListener(e -> {
            int selectedId = Integer.parseInt(this.tbl_product.getValueAt(this.tbl_product.getSelectedRow(), 0).toString());
            if (Helper.confirm("sure")) {
                if (this.productController.deleteProduct(selectedId)) {
                    Helper.showMsg("done");
                    loadProductTable(null);
                    loadBasketTable();
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_product.setComponentPopupMenu(popup_product);
    }

    private void filterCustomerButtonEvent() {
        btn_customer_filter.addActionListener(e -> {
            ArrayList<Customer> filteredCustomers = this.customerController.filterCustomers(this.fld_filter_customer_name.getText(), (Customer.TYPE) this.cmb_f_customer_type.getSelectedItem());
            loadCustomerTable(filteredCustomers);
        });
    }

    private void filterResetButtonEvent() {
        btn_customer_filter_reset.addActionListener(e -> {
            loadCustomerTable(null);
            this.fld_filter_customer_name.setText(null);
            this.cmb_f_customer_type.setSelectedItem(null);
        });
    }

    private void loadCustomerButtonEvent() {
        btn_customer_new.addActionListener(e -> {
            CustomerUI customerUI = new CustomerUI(new Customer());
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    loadCustomerTable(null);
                    loadBasketCustomerCombo();

                }
            });
        });
    }

    private void loadCustomerPopUpMenu() {
        this.tbl_customer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_customer.rowAtPoint(e.getPoint());
                tbl_customer.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });


        this.popup_customer.add("Update").addActionListener(e -> {
            int selectedId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0).toString());
            CustomerUI customerUI = new CustomerUI(this.customerController.findCustomerById(selectedId));
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    loadCustomerTable(null);
                    loadBasketCustomerCombo();
                }
            });

        });

        this.popup_customer.add("Delete").addActionListener(e -> {
            int selectedId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0).toString());
            if (Helper.confirm("sure")) {
                if (this.customerController.deleteCustomer(selectedId)) {
                    Helper.showMsg("done");
                    loadCustomerTable(null);
                    loadBasketCustomerCombo();
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_customer.setComponentPopupMenu(this.popup_customer);
    }

    private void loadCustomerTable(ArrayList<Customer> customers) {
        Object[] columnCustomer = {"ID", "Customer Name", "Customer Type", "Phone", "E-mail", "Address"};
        this.tmdl_customer.setColumnIdentifiers(columnCustomer);
        if (customers == null) {
            customers = this.customerController.findAll();
        }

        // Table reset
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_customer.getModel();
        clearModel.setRowCount(0);

        for (Customer customer : customers) {
            Object[] rowCustomer = {customer.getId(), customer.getName(), customer.getType(), customer.getPhone(), customer.getEmail(), customer.getAddress()};
            this.tmdl_customer.addRow(rowCustomer);
        }

        this.tbl_customer.setModel(this.tmdl_customer);
        this.tbl_customer.getTableHeader().setReorderingAllowed(false);
        this.tbl_customer.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.tbl_customer.setEnabled(false);
    }

    private void loadProductTable(ArrayList<Product> products) {
        Object[] columnProduct = {"ID", "Product Name", "Product Code", "Price", "Stock"};

        if (products == null) {
            products = this.productController.findAll();
        }

        //Table reset
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_product.getModel();
        clearModel.setRowCount(0);

        this.tmdl_product.setColumnIdentifiers(columnProduct);
        for (Product product : products) {
            Object[] rowObject = {product.getId(), product.getName(), product.getCode(), product.getPrice(), product.getStock()};
            this.tmdl_product.addRow(rowObject);
        }
        this.tbl_product.setModel(this.tmdl_product);
        this.tbl_product.getTableHeader().setReorderingAllowed(false);
        this.tbl_product.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.tbl_product.setEnabled(false);

    }

}
