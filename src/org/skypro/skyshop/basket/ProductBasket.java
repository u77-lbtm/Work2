package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ProductBasket {

    private final Map<String, List<Product>> basket = new TreeMap<>(
            Comparator.comparingInt(String::length)
                    .reversed()
                    .thenComparing(Comparator.naturalOrder())
    );

    public void addProduct(Product product) {
        if (product != null && product.getName() != null) {
            basket.computeIfAbsent(product.getName(), k -> new ArrayList<>()).add(product);
        }
    }

    public int getGrossPrice() {
        return basket.values().stream()
                .flatMap(Collection::stream)
                .mapToInt(Product::getPrice)
                .sum();
    }

    public boolean isEmpty() {
        return basket.isEmpty();
    }

    public void printBasket() {
        if (isEmpty()) {
            System.out.println("Корзина пуста");
            return;
        }
        basket.values().stream()
                .flatMap(Collection::stream)
                .forEach(product -> System.out.println(product.getStringRepresentation()));
    }

    public boolean hasProduct(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        String lowerCaseName = name.toLowerCase().trim();
        return basket.values().stream()
                .flatMap(Collection::stream)
                .anyMatch(product -> product.getName().toLowerCase().equals(lowerCaseName));
    }

    public void clearBasket() {
        basket.clear();
    }

    public List<Product> removeProductByName(String name) {
        if (name == null || name.isBlank()) {
            return new ArrayList<>();
        }
        String lowerCaseName = name.toLowerCase().trim();

        List<Product> removedProducts = basket.values().stream()
                .flatMap(Collection::stream)
                .filter(product -> product.getName().toLowerCase().equals(lowerCaseName))
                .collect(Collectors.toList());

        basket.keySet().removeIf(key -> key.toLowerCase().equals(lowerCaseName));
        return removedProducts;
    }

    /**
     * Возвращает количество специальных товаров в корзине.
     */
    public long getSpecialCount() {
        return basket.values().stream()
                .flatMap(Collection::stream)
                .filter(Product::isSpecial) // Оставляем только товары со специальным статусом
                .count();
    }

}
