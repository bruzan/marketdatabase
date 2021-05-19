package marketdb.database;

import marketdb.models.Product;

import java.sql.*;
import java.util.ArrayList;

public class JdbcProduct {
    public Connection con;

    public JdbcProduct() {
        con = getConnection();
        CreateTable();
    }

    private Connection getConnection() {

        String url = "jdbc:mysql://localhost:3306/market_db";
        String user = "root";
        String password = "root";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public  void CreateTable()  {


        try {
            Statement stm = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS products" +
                    "(id int NOT NULL AUTO_INCREMENT," +
                    "name varchar(30)," +
                    "brand varchar(30),"+
                    "price float, PRIMARY KEY(id))";

            stm.execute(sql);
            System.out.println("Created table in market database");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertValue(String productName,String productBrand,float productPrice) {


        try {
            String sql = "INSERT INTO products (name,brand,price) VALUES (?,?,?)";

            PreparedStatement preparedStm = con.prepareStatement(sql);
            preparedStm.setString(1,productName);
            preparedStm.setString(2,productBrand);
            preparedStm.setFloat(3,productPrice);

            preparedStm.execute();


            System.out.println("Product registered");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteValue(int productID) {


        try {
            Statement smt = con.createStatement();
            String sql = "DELETE FROM products WHERE id = " + productID;
            smt.executeUpdate(sql);
            System.out.println("Record deleted.");

            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateValue(int productId, String productName,String productBrand, float price) {


        try {
            String sql = "UPDATE products SET name = ?, brand = ?, price = ? WHERE id = ?";
            PreparedStatement prepareSmt = con.prepareStatement(sql);
            prepareSmt.setString(1, productName);
            prepareSmt.setString(2, productBrand);
            prepareSmt.setFloat(3, price);
            prepareSmt.setInt(4,productId);

            prepareSmt.executeUpdate();
            System.out.println("Product updated");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Product> getProducts() {

        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Product product = new Product();
                product.id = rs.getInt("id");
                product.name = rs.getString("name");
                product.brand = rs.getString("brand");
                product.price = rs.getFloat("price");

                products.add(product);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public ArrayList<Product> getProductID(String productName)  {

        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name = ? ";
        try {
            PreparedStatement preparedSmt = con.prepareStatement(sql);
            preparedSmt.setString(1,productName);
            ResultSet rs = preparedSmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.id = rs.getInt("id");
                product.name = rs.getString("name");
                product.brand = rs.getString("brand");
                product.price = rs.getFloat("price");

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;


    }
    public Product getProductname(int productID)  {

        String sql = "SELECT * FROM products WHERE id = ? ";
        try {
            PreparedStatement preparedSmt = con.prepareStatement(sql);
            preparedSmt.setInt(1,productID);
            ResultSet rs = preparedSmt.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.id = rs.getInt("id");
                product.name = rs.getString("name");
                product.brand = rs.getString("brand");
                product.price = rs.getFloat("price");

                return product;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }





    public void terminate() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
