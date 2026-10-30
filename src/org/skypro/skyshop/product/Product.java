package org.skypro.skyshop.product;

import org.skypro.skyshop.search.Searchable;
import java.util.Objects;

public abstract class Product implements Searchable {

    private final String name;

    public Product(String name) {
        // Проверка на корректность данных
        if (name == null) {
            throw new IllegalArgumentException("Имя продукта не может быть null");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Имя продукта не может состоять только из пробелов");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Возвращает цену в целых числах
    public abstract int getPrice();

    public boolean isSpecial() {
        return false;
    }

    /**
     * Реализация метода из интерфейса Searchable.
     * Поисковым термом для любого продукта является его имя.
     */
    @Override
    public String getSearchTerm() {
        return name;
    }

    /**
     * Контракт equals и hashCode для корректной работы HashSet в SearchEngine и ProductBasket.
     * Продукты считаются одинаковыми, если у них совпадают имена.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
