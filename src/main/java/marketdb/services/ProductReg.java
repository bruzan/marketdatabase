package marketdb.services;

import marketdb.database.JdbcProduct;
import marketdb.models.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductReg {


    JdbcProduct jdbc = new JdbcProduct();
    Scanner userInput = new Scanner(System.in);



    public ArrayList getProducts() {

        ArrayList<Product> productList = jdbc.getProducts();

        return productList;
    }

    public ArrayList<Product> getProduct() {

        System.out.println("Enter the name of the product you wish to consult: ");
        String productName = userInput.nextLine();
        ArrayList<Product> productList = jdbc.getProductID(productName);

        return productList;
    }

    public void registerProduct() {

        System.out.println("Enter the product name: ");
        String productName = userInput.nextLine();

        System.out.println("Enter the product brand: ");
        String productBrand = userInput.nextLine();

        System.out.println("Enter the product price: ");
        float productPrice = userInput.nextFloat();

        jdbc.insertValue(productName, productBrand, productPrice);
    }

    public void updateProduct() {


        System.out.println("Enter the product id: ");
        int productId = userInput.nextInt();
        userInput.nextLine();

        System.out.println("Enter the product name: ");
        String productName = userInput.nextLine();

        System.out.println("Enter the product brand: ");
        String productBrand =userInput.nextLine();

        System.out.println("Enter the product price: ");
        float productPrice = userInput.nextFloat();

        jdbc.updateValue(productId,productName,productBrand,productPrice);

    }

    public void deleteProduct() {

        System.out.println("Enter the product id of the product you wish to remove: ");
        int productId = userInput.nextInt();
        userInput.nextLine();
        System.out.println(jdbc.getProductname(productId));
        System.out.println("Do you wish to proceed?: Y/N ");
        String reply = userInput.nextLine();
        if (reply.equals("Y")) {
            jdbc.deleteValue(productId);
        } else if (reply.equals("N")) {
            System.out.println("Operation Canceled");
        } else {
            System.out.println("Invalid option");
        }

    }

    public void terminate()  {
        try {
            jdbc.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
