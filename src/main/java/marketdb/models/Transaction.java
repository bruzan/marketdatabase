package marketdb.models;

public class Transaction {
    public int transaction_id;
    public float price;

    @Override
    public String toString() {
        return "Transaction{" +
                "transaction=" + transaction_id +
                ", price=" + price +
                '}';
    }
}
