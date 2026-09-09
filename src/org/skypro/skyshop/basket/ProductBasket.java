package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;

public class ProductBasket {

    private final Product[] basket = new Product[5];

    public void addProduct(Product product) {
        if (product == null) {
            return;
        }

        for (int i = 0; i < basket.length; i++) {
            if (basket[i] == null) {
                basket[i] = product;
                return;
            }
        }

        System.out.println("Невозможно добавить продукт");
    }

    public int getTotalPrice() {
        int total = 0;
        for (Product product : basket) {
            if (product != null) {
                total += product.getPrice();
            }
        }
        return total;
    }

    // Выделенный метод для подсчета специальных товаров
    public int getSpecialProductsCount() {
        int count = 0;
        for (Product product : basket) {
            if (product != null && product.isSpecial()) {
                count++;
            }
        }
        return count;
    }

    public void printBasket() {
        boolean isEmpty = true;

        for (Product product : basket) {
            if (product == null) {
                continue;
            }

            isEmpty = false;

            // Здесь автоматически вызовется переопределенный toString() каждого продукта
            System.out.println(product);
        }

        if (isEmpty) {
            System.out.println("в корзине пусто");
        } else {
            System.out.println("Итого: " + getTotalPrice());
            // Передаем результат выделенного метода БЕЗ использования instanceof
            System.out.println("Специальных товаров: " + getSpecialProductsCount());
        }
    }

    public boolean hasProduct(String name) {
        if (name == null) {
            return false;
        }
        for (Product product : basket) {
            if (product != null && product.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void clearBasket() {
        for (int i = 0; i < basket.length; i++) {
            basket[i] = null;
        }
    }
}

