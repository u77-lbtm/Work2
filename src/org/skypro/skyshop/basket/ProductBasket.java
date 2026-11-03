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

    // Инициализируем TreeMap. Компаратор теперь сортирует КЛЮЧИ (названия продуктов по длине и алфавиту)
    private final Map<String, List<Product>> basket = new TreeMap<>(
            Comparator.comparingInt(String::length)
                    .reversed()
                    .thenComparing(Comparator.naturalOrder())
    );

    /**
     * Добавление продукта в корзину.
     */
    public void addProduct(Product product) {
        if (product != null && product.getName() != null) {
            // Метод computeIfAbsent создает новый List, если такого ключа еще нет, и добавляет продукт
            basket.computeIfAbsent(product.getName(), k -> new ArrayList<>()).add(product);
        }
    }

    /**
     * Считает общую стоимость всех товаров в корзине.
     */
    public int getGrossPrice() {
        return basket.values().stream()
                .flatMap(Collection::stream) // Сглаживаем Stream<List<Product>> в Stream<Product>
                .mapToInt(Product::getPrice) // Получаем поток цен (int)
                .sum();                      // Суммируем все цены
    }

    /**
     * Проверяет, пуста ли корзина.
     */
    public boolean isEmpty() {
        return basket.isEmpty();
    }

    /**
     * Выводит содержимое корзины на экран.
     */
    public void printBasket() {
        if (isEmpty()) {
            System.out.println("Корзина пуста");
            return;
        }

        // Вместо цикла используем плоский стрим и forEach
        basket.values().stream()
                .flatMap(Collection::stream)
                .forEach(product -> System.out.println(product.getStringRepresentation()));
    }

    /**
     * Проверяет наличие продукта в корзине по его имени (регистронезависимо).
     */
    public boolean hasProduct(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        String lowerCaseName = name.toLowerCase().trim();

        // Заменяем цикл на терминальную операцию anyMatch
        return basket.values().stream()
                .flatMap(Collection::stream)
                .anyMatch(product -> product.getName().toLowerCase().equals(lowerCaseName));
    }

    /**
     * Полностью очищает корзину.
     */
    public void clearBasket() {
        basket.clear();
    }

    /**
     * Удаляет продукты по имени и возвращает список того, что было удалено.
     */
    public List<Product> removeProductByName(String name) {
        if (name == null || name.isBlank()) {
            return new ArrayList<>();
        }
        String lowerCaseName = name.toLowerCase().trim();

        // 1. Сначала находим и сохраняем удаляемые элементы через Stream API
        List<Product> removedProducts = basket.values().stream()
                .flatMap(Collection::stream)
                .filter(product -> product.getName().toLowerCase().equals(lowerCaseName))
                .collect(Collectors.toList());

        // 2. Затем безопасно удаляем соответствующие ключи из Map без итераторов
        basket.keySet().removeIf(key -> key.toLowerCase().equals(lowerCaseName));

        return removedProducts;
    }

    /**
     * Возвращает количество товаров в корзине.
     */
    public long getSpecialCount() {
        return basket.values().stream()
                .flatMap(Collection::stream)
                .count();
    }
}
