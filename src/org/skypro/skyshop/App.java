package org.skypro.skyshop;

import org.skypro.skyshop.basket.ProductBasket;
import org.skypro.skyshop.product.Product;
import org.skypro.skyshop.product.SimpleProduct;
import org.skypro.skyshop.product.DiscountedProduct;
import org.skypro.skyshop.product.Article;
import org.skypro.skyshop.search.Searchable;
import org.skypro.skyshop.search.SearchEngine;
import org.skypro.skyshop.search.BestResultNotFound;

public class App {
    public static void main(String[] args) {
        // 1. Инициализация продуктов
        Product milk = new SimpleProduct("Молоко", 80);
        Product bread = new SimpleProduct("Хлеб", 40);
        Product cake = new SimpleProduct("Торт", 500);

        Product cheese = new DiscountedProduct("Сыр", 300, 10);       // Скидка 10% -> 270
        Product apple = new DiscountedProduct("Яблоко", 100, 20);     // Скидка 20% -> 80
        Product juice = new SimpleProduct("Сок", 100);
        Product phone = new DiscountedProduct("Orean", 5000, 250);

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
        Article article = new Article("Польза молока", "Свежее молоко содержит кальций и витамины. Молоко полезно пить каждый день.");

        // 4. Инициализируем поисковый движок
        SearchEngine searchEngine = new SearchEngine(10);

        // Добавляем элементы в поисковый движок
        searchEngine.add(milk);
        searchEngine.add(bread);
        searchEngine.add(cake);
        searchEngine.add(cheese);
        searchEngine.add(apple);
        searchEngine.add(article);
        searchEngine.add(phone);

        // 5. Тестируем базовый метод поиска search (возвращает массив)
        String query = "молоко";
        System.out.println("Поиск по запросу: '" + query + "'");
        Searchable[] results = searchEngine.search(query);

        for (int i = 0; i < results.length; i++) {
            Searchable item = results[i];
            if (item != null) {
                System.out.println("Результат " + (i + 1) + ": " + item.getStringRepresentation());
            } else {
                System.out.println("Результат " + (i + 1) + ": [пусто]");
            }
        }

        // 6. Проверка метода toString у статьи
        System.out.println("\n=== ПРОВЕРКА TO_STRING У СТАТЬИ ===");
        System.out.println(article);

        // 7. Тестирование поиска лучшего результата (findBestResult) с обработкой исключений
        System.out.println("\n=== ТЕСТИРОВАНИЕ НАХОЖДЕНИЯ ЛУЧШЕГО РЕЗУЛЬТАТА ===");

        // ТЕСТ А: Ищем существующее слово "молоко"
        // Оно есть в названии продукта "Молоко" (1 раз) и в статье (2 раза). Должна победить статья.
        System.out.print("Тест 1 (Поиск 'молоко'): ");
        try {
            Searchable best = searchEngine.findBestResult("молоко");
            System.out.println("УСПЕХ -> Найдено: " + best.getStringRepresentation());
        } catch (BestResultNotFound e) {
            System.out.println("ОШИБКА (Не должно было случиться): " + e.getMessage());
        }

        // Сценарий 2: Поиск слова, которого вообще нет в базе данных
        System.out.print("Тест 2 (Поиск 'java'): ");
        try {
            Searchable best = searchEngine.findBestResult("java");
            System.out.println("ОШИБКА -> Метод вернул результат вместо исключения: " + best.getStringRepresentation());
        } catch (BestResultNotFound e) {
            System.out.println("УСПЕХ -> Исключение перехвачено. Сообщение: " + e.getMessage());
        }

        // Сценарий 3: Поиск с пустым запросом (передача пустой строки)
        System.out.print("Тест 3 (Поиск пустой строки ''): ");
        try {
            searchEngine.findBestResult("");
            System.out.println("ОШИБКА -> Метод не отреагировал на пустую строку!");
        } catch (BestResultNotFound e) {
            System.out.println("УСПЕХ -> Пустая строка вызвала исключение. Сообщение: " + e.getMessage());
        }

        // Сценарий 4: Поиск по строке, состоящей только из пробелов
        System.out.print("Тест 4 (Поиск пробелов '   '): ");
        try {
            searchEngine.findBestResult("   ");
            System.out.println("ОШИБКА -> Метод не отреагировал на пробелы!");
        } catch (BestResultNotFound e) {
            System.out.println("УСПЕХ -> Пробелы вызвали исключение. Сообщение: " + e.getMessage());
        }

        // Сценарий 5: Поиск null-запроса
        System.out.print("Тест 5 (Поиск null): ");
        try {
            searchEngine.findBestResult(null);
            System.out.println("ОШИБКА -> Метод не выбросил исключение при null!");
        } catch (BestResultNotFound e) {
            System.out.println("УСПЕХ -> null-запрос вызвал исключение. Сообщение: " + e.getMessage());
        }
    }
}
