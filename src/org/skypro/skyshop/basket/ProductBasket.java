package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Comparator;

public class ProductBasket {

    // 1. Поле объявлено в корне класса и все методы используют ОДНО имя: basket
    private final Set<Product> basket = new TreeSet<>(
            Comparator.comparingInt((Product p) -> p.getName().length())
                    .reversed()
                    .thenComparing(Product::getName)
    );

    /**
     * Добавление продукта в корзину.
     */
    public void addProduct(Product product) {
        if (product != null) {
            basket.add(product);
        }
    }

    /**
     * Считает общую стоимость всех товаров в корзине.
     */
    public int getGrossPrice () {
        int total = 0;
        for (Product product : basket) {
            total += product.getPrice();
        }
        return total;
    }

    /**
     * Проверяет, пуста ли корзина.
     */
    public boolean isEmpty () {
        return basket.isEmpty();
    }

    /**
     * Выводит содержимое корзины на экран.
     */
    public void printBasket () {
        if (isEmpty()) {
            System.out.println("Корзина пуста");
            return;
        }

        for (Product product : basket) {
            System.out.println(product.getStringRepresentation());
        }
        // Убрали дублирующий вывод "Итого", так как в App.java сумма пишется отдельно
    }

    /**
     * Проверяет наличие продукта в корзине по его имени (регистронезависимо).
     */
    public boolean hasProduct (String name){
        if (name == null || name.isBlank()) {
            return false;
        }
        String lowerCaseName = name.toLowerCase();
        for (Product product : basket) {
            if (product.getName().toLowerCase().equals(lowerCaseName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Полностью очищает корзину.
     */
    public void clearBasket () {
        basket.clear();
    }

    /**
     * Удаляет продукты по имени и возвращает список того, что было удалено.
     */
    public List<Product> removeProductByName (String name){
        List<Product> removedProducts = new ArrayList<>();
        if (name == null || name.isBlank()) {
            return removedProducts;
        }

        String lowerCaseName = name.toLowerCase();
        Iterator<Product> iterator = basket.iterator();

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
