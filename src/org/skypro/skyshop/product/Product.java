package org.skypro.skyshop.product;
import org.skypro.skyshop.search.Searchable;

public abstract class Product implements Searchable {

    private final String name;

    public Product(String name) {
        String temporaryName;
        try {
            if (name.isBlank()) {
                throw new IllegalArgumentException("Имя продукта не может состоять только из пробелов");
            }
            temporaryName = name;
            System.out.println("Данные корректны: " + name);

        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Имя продукта не может быть null", e);
        }


        this.name = temporaryName;
    }



    @Override
    public String getName() {
        return name;
    }

    public abstract int getPrice();

    public boolean isSpecial() {
        return false;
    }
}

