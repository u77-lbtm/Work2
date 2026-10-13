package org.skypro.skyshop.search;

import java.util.Map;
import java.util.TreeMap;

public class SearchEngine {
    // Карта хранит ключи (имена) и одиночные подходящие объекты Searchable
    private final TreeMap<String, Searchable> treeMap = new TreeMap<>();
    private int currentSize = 0;

    // Конструктор с параметром capacity для полной совместимости с App.java
    public SearchEngine(int capacity) {
    }

    // Дополнительный пустой конструктор
    public SearchEngine() {
    }

    /**
     * Метод добавления объекта.
     */
    public void add(Searchable item) {
        if (item == null) {
            System.out.println("Нельзя добавить пустой объект (null).");
            return;
        }

        String key = item.getName();

        // Если такого ключа еще нет, значит это новый уникальный элемент
        if (!treeMap.containsKey(key)) {
            currentSize++;
        }

        // Добавляем или обновляем объект в карте
        treeMap.put(key, item);
    }

    /**
     * Метод поиска. Возвращает Map<String, Searchable> и формирует результат через TreeMap.
     * getSearchTerm() используется для проверки, подходит ли объект под поисковый запрос.
     * getName() используется как ключ в результирующем TreeMap.
     */
    public Map<String, Searchable> search(String query) {
        // Формируем результат именно через TreeMap
        Map<String, Searchable> results = new TreeMap<>();

        if (query == null || query.isBlank()) {
            // Если запрос пустой, распечатываем всю базу данных в консоль
            for (Map.Entry<String, Searchable> m : treeMap.entrySet()) {
                System.out.println(m.getKey() + " " + m.getValue());
            }
            return results;
        }

        String lowerCaseQuery = query.toLowerCase();

        // Итерируемся напрямую по объектам Searchable
        for (Searchable item : treeMap.values()) {
            // Проверяем соответствие тексту запроса через getSearchTerm()
            if (item.getSearchTerm().toLowerCase().contains(lowerCaseQuery)) {
                // Используем item.getName() как ключ в результирующем TreeMap
                results.put(item.getName(), item);
            }
        }
        return results;
    }

    /**
     * Поиск самого подходящего результата по количеству вхождений.
     */
    public Searchable findBestResult(String query) throws BestResultNotFound {
        if (query == null || query.isBlank()) {
            throw new BestResultNotFound("Пустой поисковый запрос.");
        }

        Searchable bestMatch = null;
        int maxOccurrences = 0;
        String lowerCaseQuery = query.toLowerCase();

        // Обходим одиночные объекты в карте
        for (Searchable item : treeMap.values()) {
            int occurrences = countOccurrences(item.getSearchTerm().toLowerCase(), lowerCaseQuery);

            if (occurrences > maxOccurrences) {
                maxOccurrences = occurrences;
                bestMatch = item;
            }
        }

        if (bestMatch == null || maxOccurrences == 0) {
            throw new BestResultNotFound("Ни один элемент не соответствует запросу: " + query);
        }

        return bestMatch;
    }

    /**
     * Вспомогательный метод для подсчета вхождений подстроки.
     */
    private int countOccurrences(String str, String target) {
        if (str == null || target == null || target.isEmpty()) {
            return 0;
        }
        int count = 0;
        int index = 0;
        int substringIndex = str.indexOf(target, index);

        while (substringIndex != -1) {
            count++;
            index = substringIndex + target.length();
            substringIndex = str.indexOf(target, index);
        }
        return count;
    }

    public int getCurrentSize() {
        return currentSize;
    }
}
