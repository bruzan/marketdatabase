package marketdb.models;

public class Product {
    public int id;
    public String name;
    public String brand;
    public float price;


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}

