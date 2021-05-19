package marketdb.models;

public class Purchase {
    public int purchase_id;
    public int transaction_id;
    public int quantity;
    public float price;
    public float allPrice;

    @Override
    public String toString() {
        return "Purchase{" +
                "purchase_id=" + purchase_id +
                ", transaction_id=" + transaction_id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", allPrice=" + allPrice +
                '}';
    }
}
