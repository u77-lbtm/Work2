package org.skypro.skyshop.product;

public class SimpleProduct extends Product {

    private final int price;


    public SimpleProduct(String name, int price) {
        super(name);
        if (price <= 0) {
            throw new IllegalArgumentException("Цена должна быть больше нуля");
        }
        this.price = price;
    }


    @Override
    public int getPrice() {
        return this.price;
    }
    @Override
    public String getSearchTerm() {
        return getName();
    }

    @Override
    public String getContentType() {
        return "Товар";
    }
    @Override
    public String toString() {
        return getName() + ": цена " + getPrice() + " руб.";
    }


}
