package org.skypro.skyshop.search;

import java.util.LinkedList;
import java.util.List;

public class SearchEngine {

    // Внутренний класс узла связного списка базы данных движка
    private static class Node {
        Searchable item;
        Node next;

        Node(Searchable item) {
            this.item = item;
            this.next = null;
        }
    }

    private Node head = null;
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

        Node newNode = new Node(item);
        newNode.next = head;
        head = newNode;
        currentSize++;
    }

    /**
     * Ищет и возвращает ВСЕ подходящие элементы без ограничения по количеству.
     * Использует LinkedList для накопления результатов.
     */
    public List<Searchable> search(String query) {
        // Создаем динамический связный список для результатов
        List<Searchable> results = new LinkedList<>();

        if (query == null || query.isBlank()) {
            return results;
        }

        String lowerCaseQuery = query.toLowerCase();

        // Итерируемся по цепочке узлов от начала до конца
        Node current = head;
        while (current != null) {
            Searchable item = current.item;

            // Если нашли совпадение в терме — добавляем без лимитов
            if (item.getSearchTerm().toLowerCase().contains(lowerCaseQuery)) {
                results.add(item);
            }
            current = current.next;
        }
        return results;
    }

    /**
     * Находит объект с наибольшим количеством вхождений поискового запроса.
     */
    public Searchable findBestResult(String query) throws BestResultNotFound {
        if (query == null || query.isBlank()) {
            throw new BestResultNotFound("Пустой поисковый запрос.");
        }

        Searchable bestMatch = null;
        int maxOccurrences = 0;

        String lowerCaseQuery = query.toLowerCase();

        Node current = head;
        while (current != null) {
            Searchable item = current.item;

            int occurrences = countOccurrences(item.getSearchTerm().toLowerCase(), lowerCaseQuery);

            if (occurrences > maxOccurrences) {
                maxOccurrences = occurrences;
                bestMatch = item;
            }
            current = current.next;
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
