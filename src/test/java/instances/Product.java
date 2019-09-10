package instances;


public class Product implements Comparable {
    public String name = "";
    String quantity = "";

    @Override
    public int compareTo(Object o) {
        return toString().compareTo(o.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return this.toString().equalsIgnoreCase(product.toString());
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
