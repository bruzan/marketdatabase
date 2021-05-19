package marketdb.services;

import marketdb.database.JdbcProduct;
import marketdb.database.JdbcPurchases;
import marketdb.models.Cart;
import marketdb.models.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Register {

    Scanner userInput = new Scanner(System.in);
    JdbcProduct jdbcProduct = new JdbcProduct();
    JdbcPurchases jdbcPurchases = new JdbcPurchases();

    public void addCart() {

        ArrayList<Cart> productsInCart = new ArrayList<>();

        float totalPrice = 0;

        boolean run = true;

        while (run) {

            System.out.println("Enter the product id you wish to add: (0 to stop)");
            int productId = userInput.nextInt();
            userInput.nextLine();
            if (productId == 0) {
                System.out.println(productsInCart);
                System.out.println(productsInCart.get(1));
                System.out.println("Total price = " + totalPrice);
                break;
            }
            else {
                System.out.println("Enter the quantity of the product: (0 to stop)");
                int prodQuantity = userInput.nextInt();
                userInput.nextLine();
                if (prodQuantity == 0) {
                    System.out.println(productsInCart);
                    System.out.println(productsInCart.get(1));
                    System.out.println("Total price = " + totalPrice);
                    break;
                } else {
                    Product product = jdbcProduct.getProductname(productId);
                    Cart cart = new Cart();
                    cart.id = product.id;
                    cart.name = product.name;
                    cart.price = product.price*prodQuantity;
                    cart.quantity = prodQuantity;

                    totalPrice = totalPrice + (prodQuantity*product.price);
                    productsInCart.add(cart);

                    System.out.println(productsInCart);
                    System.out.println("Total price = " + totalPrice);
                }
            }
        }
        int transaction_id = jdbcPurchases.insertTransaction(totalPrice);


        for (int i = 0; i < productsInCart.size(); i++) {
            int product_id = productsInCart.get(i).id;
            int quantity = productsInCart.get(i).quantity;
            float productPrice = productsInCart.get(i).price / quantity;
            float allProductprice = productsInCart.get(i).price;

            jdbcPurchases.insertPurchases(transaction_id, product_id, quantity, productPrice, allProductprice);
        }
    }
    public void transactionTable() {
        System.out.println(jdbcPurchases.consultTransactionTable());
    }
    public void purchaseTable() {
        System.out.println("Enter the transaction id: ");
        int transaction_id = userInput.nextInt();
        System.out.println(jdbcPurchases.consultPurchaseTable(transaction_id));
    }
    public void terminate() {
        try {
            jdbcPurchases.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}