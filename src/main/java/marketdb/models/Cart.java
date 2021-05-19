package marketdb.models;

public class Cart {
    public int id;
    public String name;
    public int quantity;
    public float price;

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
