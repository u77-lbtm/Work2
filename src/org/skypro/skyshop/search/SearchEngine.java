package org.skypro.skyshop.search;

public class SearchEngine {

    // Класс узла для создания связного списка
    private static class Node {
        Searchable item;
        Node next;

        Node(Searchable item) {
            this.item = item;
            this.next = null;
        }
    }

    // ИЗМЕНЕНИЕ: Вместо массива database храним указатель на начало списка
    private Node head = null;
    private int currentSize = 0;

    // Конструктор теперь может быть пустым, так как список динамический.
    // Если вам обязательно нужно сохранить старую сигнатуру для совместимости,
    // параметр capacity можно просто проигнорировать.
    public SearchEngine() {
    }

    public void add(Searchable item) {
        if (item == null) {
            System.out.println("Нельзя добавить пустой объект (null).");
            return;
        }

        // ИЗМЕНЕНИЕ: Логика проверки переполнения (currentSize >= database.length) удалена,
        // так как связный список не имеет фиксированного лимита.

        Node newNode = new Node(item);
        newNode.next = head; // Новый узел указывает на текущее начало списка
        head = newNode;      // Новый узел становится началом списка
        currentSize++;
    }

    public Searchable[] search(String query) {
        Searchable[] results = new Searchable[5];

        if (query == null || query.isBlank()) {
            return results;
        }

        String lowerCaseQuery = query.toLowerCase();
        int foundCount = 0;

        // ИЗМЕНЕНИЕ: Итерируемся по связному списку вместо массива
        Node current = head;
        while (current != null) {
            Searchable item = current.item;

            if (item.getSearchTerm().toLowerCase().contains(lowerCaseQuery)) {
                results[foundCount] = item;
                foundCount++;

                if (foundCount == 5) {
                    break;
                }
            }
            current = current.next; // Переходим к следующему элементу
        }
        return results;
    }

    /**
     * Находит объект с наибольшим количеством вхождений поискового запроса (регистронезависимо).
     */
    public Searchable findBestResult(String query) throws BestResultNotFound {
        if (query == null || query.isBlank()) {
            throw new BestResultNotFound("Пустой поисковый запрос.");
        }

        Searchable bestMatch = null;
        int maxOccurrences = 0;

        String lowerCaseQuery = query.toLowerCase();

        // ИЗМЕНЕНИЕ: Итерируемся по связному списку вместо массива
        Node current = head;
        while (current != null) {
            Searchable item = current.item;

            int occurrences = countOccurrences(item.getSearchTerm().toLowerCase(), lowerCaseQuery);

            if (occurrences > maxOccurrences) {
                maxOccurrences = occurrences;
                bestMatch = item;
            }
            current = current.next; // Переходим к следующему элементу
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

    // Вспомогательный метод, если вам нужно знать текущее количество элементов
    public int getCurrentSize() {
        return currentSize;
    }
}
