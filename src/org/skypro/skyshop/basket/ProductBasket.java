package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;
import java.util.*;

public class ProductBasket {

    // Ключ: Название товара (String), Значение: Список продуктов с таким названием (List<Product>)
    // Это позволяет хранить несколько одинаковых товаров (например, 2 пачки молока)
    private final Map<String, List<Product>> basketMap = new HashMap<>();

    /**
     * Добавление продукта в корзину.
     * Используем computeIfAbsent для создания списка при первом добавлении товара.
     */
    public void addProduct(Product product) {
        if (product == null) {
            return;
        }
        // Переводим имя в нижний регистр для единообразия поиска
        String key = product.getName().toLowerCase();

        // Тот самый паттерн: берем список или создаем новый, если товара еще не было, и добавляем
        basketMap.computeIfAbsent(key, k -> new ArrayList<>()).add(product);
    }

    /**
     * Считает общую стоимость всех товаров в корзине.
     */
    public int getGrossPrice() {
        int total = 0;
        // Проходим по всем спискам продуктов в нашей Map
        for (List<Product> products : basketMap.values()) {
            for (Product product : products) {
                total += product.getPrice();
            }
        }
        return total;
    }

    /**
     * Проверяет, пуста ли корзина.
     */
    private boolean isEmpty() {
        return basketMap.isEmpty();
    }

    /**
     * Выводит содержимое корзины на экран.
     */
    public void printBasket() {
        if (isEmpty()) {
            System.out.println("Корзина пуста");
            return;
        }

        // Пробегаемся по всем добавленным продуктам
        for (List<Product> products : basketMap.values()) {
            for (Product product : products) {
                // Вызываем строковое представление продукта (из вашего задания SkyPro)
                System.out.println(product.getStringRepresentation());
            }
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
        // Поскольку при добавлении мы переводили ключ в lowerCase, проверка занимает O(1)
        return basketMap.containsKey(name.toLowerCase());
    }

    /**
     * Полностью очищает корзину.
     */
    public void clearBasket() {
        basketMap.clear();
    }

    /**
     * Удаляет продукты по имени и возвращает список того, что было удалено.
     */
    public List<Product> removeProductByName(String name) {
        List<Product> removedProducts = new LinkedList<>();
        if (name == null || name.isBlank()) {
            return removedProducts;
        }

        String key = name.toLowerCase();
        // Если такой товар вообще был в карте — забираем весь список и удаляем его из Map
        if (basketMap.containsKey(key)) {
            removedProducts.addAll(basketMap.remove(key));
        }

        return removedProducts;
    }
}
