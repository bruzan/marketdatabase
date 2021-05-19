package marketdb.database;

import marketdb.models.Cart;
import marketdb.models.Purchase;
import marketdb.models.Transaction;

import java.sql.*;
import java.util.ArrayList;

public class JdbcPurchases {
    Cart cart = new Cart();

    public Connection con;


    public JdbcPurchases() {
        con = getConnection();
        createTable();
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

    public  void createTable()  {


        try {
            Statement stm = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS transactions" +
                    "(transaction_id int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    " total float)";
            String sql2 = "CREATE TABLE IF NOT EXISTS purchases" +
                    "(purchase_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,"+
                    "transaction_id int, product_id int, quantity int, productPrice float, allProductPrice float," +
                    "FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id),"+
                    "FOREIGN KEY (product_id) REFERENCES products(id))";

            stm.execute(sql);
            stm.execute(sql2);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertTransaction(float total) {

        String query = "INSERT INTO transactions (total) VALUES (?)";

        try {
            PreparedStatement preparedStm = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStm.setFloat(1, total);


            preparedStm.execute();

            ResultSet generatedKeys = preparedStm.getGeneratedKeys();
            if (generatedKeys.next()) {

                int transaction_id = generatedKeys.getInt(1);
                return transaction_id;
            } else {
                System.out.println("Error");
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void insertPurchases(int transaction_id, int product_id, int quantity, float productPrice, float allProductPrice) {

        String query = "INSERT INTO purchases (transaction_id, product_id, quantity, productPrice, allProductPrice) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement preparedStm = con.prepareStatement(query);

            preparedStm.setInt(1, transaction_id);
            preparedStm.setInt(2, product_id);
            preparedStm.setInt(3, quantity);
            preparedStm.setFloat(4, productPrice);
            preparedStm.setFloat(5, allProductPrice);
            preparedStm.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<Transaction> consultTransactionTable() {

        ArrayList<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions";

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.transaction_id = rs.getInt("transaction_id");
                transaction.price = rs.getFloat("total");

                transactions.add(transaction);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;

    }

    public ArrayList<Purchase> consultPurchaseTable(int id) {

        ArrayList<Purchase> purchases = new ArrayList<>();
        String query = "SELECT * FROM purchases WHERE transaction_id = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Purchase purchase = new Purchase();
                purchase.purchase_id = rs.getInt("purchase_id");
                purchase.transaction_id = rs.getInt("transaction_id");
                purchase.quantity = rs.getInt("quantity");
                purchase.price = rs.getFloat("productPrice");
                purchase.allPrice = rs.getFloat("allProductPrice");

                purchases.add(purchase);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchases;
    }

}

