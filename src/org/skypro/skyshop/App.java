package org.skypro.skyshop;

import org.skypro.skyshop.basket.ProductBasket;
import org.skypro.skyshop.product.Product;
import org.skypro.skyshop.product.SimpleProduct;
import org.skypro.skyshop.product.DiscountedProduct;

public class App {
    public static void main(String[] args) {
        Product milk = new SimpleProduct("Молоко", 80);
        Product bread = new SimpleProduct("Хлеб", 40);
        Product cake = new SimpleProduct("Торт", 500);

        Product cheese = new DiscountedProduct("Сыр", 300, 10);       // Скидка 10% -> Цена станет 270
        Product apple = new DiscountedProduct("Яблоко", 100, 20);     // Скидка 20% -> Цена станет 80
        Product juice = new SimpleProduct("Сок", 100);
        ProductBasket myBasket = new ProductBasket();
        myBasket.addProduct(milk);
        myBasket.addProduct(bread);
        myBasket.addProduct(cheese);
        myBasket.addProduct(apple);
        myBasket.addProduct(juice);
        myBasket.addProduct(cake);
        myBasket.printBasket();
        System.out.println("Общая стоимость: " + myBasket.getTotalPrice());
        System.out.println("Есть ли 'Хлеб'? " + myBasket.hasProduct("Хлеб"));
        System.out.println("Есть ли 'Торт'? " + myBasket.hasProduct("Торт"));
        myBasket.clearBasket();
        System.out.println("Корзина очищена.");
        myBasket.printBasket();
        System.out.println("Стоимость пустой корзины: " + myBasket.getTotalPrice());
        System.out.println("Есть ли 'Молоко' в пустой корзине? " + myBasket.hasProduct("Молоко"));
    }
}
