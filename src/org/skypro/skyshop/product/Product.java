package org.skypro.skyshop.product;
import org.skypro.skyshop.search.Searchable;

public abstract class Product implements Searchable {

    private final String name;

    public Product(String name) {
        // Сначала проверяем на null, чтобы не поймать NullPointerException при вызове isBlank()
        if (name == null) {
            throw new IllegalArgumentException("Имя продукта не может быть null");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Имя продукта не может состоять только из пробелов");
        }

        this.name = name;
        System.out.println("Данные корректны: " + name);
    }

    @Override
    public String getName() {
        return name;
    }

    // Возвращает цену в целых числах (например, в рублях)
    public abstract int getPrice();

    public boolean isSpecial() {
        return false;
    }
}
