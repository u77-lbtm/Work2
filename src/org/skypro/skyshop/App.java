package org.skypro.skyshop;

import org.skypro.skyshop.basket.ProductBasket;
import org.skypro.skyshop.product.Product;
import org.skypro.skyshop.product.SimpleProduct;
import org.skypro.skyshop.product.DiscountedProduct;
import org.skypro.skyshop.product.Article;
import org.skypro.skyshop.search.Searchable;


import org.skypro.skyshop.search.SearchEngine;

public class App {
    public static void main(String[] args) {
        // 1. Инициализация продуктов
        Product milk = new SimpleProduct("Молоко", 80);
        Product bread = new SimpleProduct("Хлеб", 40);
        Product cake = new SimpleProduct("Торт", 500);

        Product cheese = new DiscountedProduct("Сыр", 300, 10);       // Скидка 10% -> 270
        Product apple = new DiscountedProduct("Яблоко", 100, 20);     // Скидка 20% -> 80
        Product juice = new SimpleProduct("Сок", 100);

        // 2. Демонстрация работы с корзиной
        System.out.println("=== РАБОТА С КОРЗИНОЙ ===");
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

        System.out.println("\n=== ДЕМОНСТРАЦИЯ ПОИСКА ===");
        // 3. Создаем статью
        Article article = new Article("Польза молока", "Свежее молоко содержит кальций и витамины.");

        // 4. Инициализируем поисковый движок (задаем вместимость через конструктор)
        SearchEngine searchEngine = new SearchEngine(10);

        // Добавляем элементы в поисковый движок через метод add()
        searchEngine.add(milk);
        searchEngine.add(bread);
        searchEngine.add(cake);
        searchEngine.add(cheese);
        searchEngine.add(apple);
        searchEngine.add(article);

        // 5. Тестируем метод поиска search, который возвращает массив из 5 элементов
        String query = "молоко";
        System.out.println("Поиск по запросу: '" + query + "'");
        Searchable[] results = searchEngine.search(query);

        // Выводим результаты поиска на экран
        for (int i = 0; i < results.length; i++) {
            Searchable item = results[i];
            if (item != null) {
                System.out.println("Результат " + (i + 1) + ": " + item.getStringRepresentation());
            } else {
                System.out.println("Результат " + (i + 1) + ": [пусто]");
            }
        }

        // 6. Проверка метода toString у статьи (вызовется автоматически)
        System.out.println("\n=== ПРОВЕРКА TO_STRING У СТАТЬИ ===");
        System.out.println(article);
    }
}

