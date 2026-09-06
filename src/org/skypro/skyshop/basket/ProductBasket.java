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


    public void printBasket() {
        boolean isEmpty = true;

        for (Product product : basket) {
            if (product != null) {
                System.out.println(product.getName() + ": " + product.getPrice());
                isEmpty = false;
            }
        }

        if (isEmpty) {
            System.out.println("в корзине пусто");
        } else {
            System.out.println("Итого: " + getTotalPrice());
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
