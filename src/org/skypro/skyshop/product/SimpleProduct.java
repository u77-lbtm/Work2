
package org.skypro.skyshop.product;

public class SimpleProduct extends Product {

    private final int price;


    public SimpleProduct(String name, int price) {
        super(name);
        try {
            if (price <= 0) {
                throw new IllegalArgumentException("Цена должна быть больше нуля");
            }
            this.price = price;
            System.out.println("Данные корректны: " + name);

        } catch (IllegalArgumentException ex) {
            System.err.println("Ошибка при создании продукта: " + ex.getMessage());
            throw ex;
        }
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
