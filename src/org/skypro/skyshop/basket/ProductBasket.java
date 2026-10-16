package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class ProductBasket {

    // Храним продукты в TreeSet. Они будут автоматически отсортированы по алфавиту
    // Важно: класс Product должен реализовывать Comparable<Product>!
    private final NavigableSet<Product> basketSet = new TreeSet<>();

    /**
     * Добавление продукта в корзину.
     */
    public void addProduct(Product product) {
        if (product == null) {
            return;
        }
        basketSet.add(product);
    }

    /**
     * Считает общую стоимость всех товаров в корзине.
     */
    public int getGrossPrice() {
        int total = 0;
        for (Product product : basketSet) {
            total += product.getPrice();
        }
        return total;
    }

    /**
     * Проверяет, пуста ли корзина.
     */
    public boolean isEmpty() {
        return basketSet.isEmpty();
    }

    /**
     * Выводит содержимое корзины на экран.
     */
    public void printBasket() {
        if (isEmpty()) {
            System.out.println("Корзина пуста");
            return;
        }

        for (Product product : basketSet) {
            System.out.println(product.getStringRepresentation());
        }
        System.out.println("Итого: " + getGrossPrice());
    }

    /**
     * Проверяет наличие продукта в корзине по его имени (регистронезависимо).
     */
    public boolean hasProduct(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        String lowerCaseName = name.toLowerCase();
        for (Product product : basketSet) {
            if (product.getName().toLowerCase().equals(lowerCaseName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Полностью очищает корзину.
     */
    public void clearBasket() {
        basketSet.clear();
    }

    /**
     * Удаляет продукты по имени и возвращает список того, что было удалено.
     */
    public List<Product> removeProductByName(String name) {
        List<Product> removedProducts = new ArrayList<>();
        if (name == null || name.isBlank()) {
            return removedProducts;
        }

        String lowerCaseName = name.toLowerCase();
        Iterator<Product> iterator = basketSet.iterator();

        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getName().toLowerCase().equals(lowerCaseName)) {
                removedProducts.add(product);
                iterator.remove(); // Безопасное удаление во время итерации
            }
        }

        return removedProducts;
    }
}
