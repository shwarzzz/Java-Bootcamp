package edu.school21.models;

public class Product {
    public static final String ENTITY_TABLE_NAME = "product";
    private Long id;
    private String name;
    private Double price;

    public Product(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        return (id == other.getId() || (id != null && id.equals(other.getId())))
                && (name == other.getName() ||
                (name != null && name.equals(other.getName())))
                && (price == other.getPrice() ||
                (price != null && price.equals(other.getPrice())));
    }

    @Override
    public int hashCode() {
        int result = 1;
        final int FACTOR = 11;
        result = result * FACTOR + (id == null ? 0 : id.hashCode());
        result = result * FACTOR + (name == null ? 0 : name.hashCode());
        result = result * FACTOR + (price == null ? 0 : price.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "{id = " + id + ", name = \"" + name +
                "\", price = " + price + "}";
    }
}
