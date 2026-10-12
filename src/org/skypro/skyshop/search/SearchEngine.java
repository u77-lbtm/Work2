package org.skypro.skyshop.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SearchEngine {
    // Карта хранит ключи (имена) и списки подходящих объектов Searchable
    private final TreeMap<String, List<Searchable>> treeMap = new TreeMap<>();
    private int currentSize = 0;

    // Конструктор с параметром capacity для полной совместимости с App.java
    public SearchEngine(int capacity) {
    }

    // Дополнительный пустой конструктор
    public SearchEngine() {
    }

    public void add(Searchable item) {
        if (item == null) {
            System.out.println("Нельзя добавить пустой объект (null).");
            return;
        }

        String key = item.getSearchTerm();

        // Если такого ключа еще нет, создаем для него новый ArrayList
        if (!treeMap.containsKey(key)) {
            treeMap.put(key, new ArrayList<>());
        }

        // Добавляем сам объект в список, привязанный к этому ключу
        treeMap.get(key).add(item);
        currentSize++;
    }

    public List<Searchable> search(String query) {
        List<Searchable> results = new LinkedList<>();

        if (query == null || query.isBlank()) {
            // Если запрос пустой, распечатываем всю базу данных в консоль
            for (Map.Entry<String, List<Searchable>> m : treeMap.entrySet()) {
                System.out.println(m.getKey() + " " + m.getValue());
            }
            return results;
        }

        String lowerCaseQuery = query.toLowerCase();

        // Ищем частичные совпадения в ключах TreeMap
        for (Map.Entry<String, List<Searchable>> m : treeMap.entrySet()) {
            if (m.getKey().toLowerCase().contains(lowerCaseQuery)) {
                // Добавляем все объекты из списка этого ключа в результаты
                results.addAll(m.getValue());
            }
        }
        return results; // Скобка метода search теперь закрывается здесь корректно!
    }

    public Searchable findBestResult(String query) throws BestResultNotFound {
        if (query == null || query.isBlank()) {
            throw new BestResultNotFound("Пустой поисковый запрос.");
        }

        Searchable bestMatch = null;
        int maxOccurrences = 0;
        String lowerCaseQuery = query.toLowerCase();

        // Итерируемся по значениям (спискам объектов) внутри TreeMap
        for (List<Searchable> itemsList : treeMap.values()) {
            for (Searchable item : itemsList) {
                int occurrences = countOccurrences(item.getSearchTerm().toLowerCase(), lowerCaseQuery);

                if (occurrences > maxOccurrences) {
                    maxOccurrences = occurrences;
                    bestMatch = item;
                }
            }
        }

        if (bestMatch == null || maxOccurrences == 0) {
            throw new BestResultNotFound("Ни один элемент не соответствует запросу: " + query);
        }

        return bestMatch;
    }

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


