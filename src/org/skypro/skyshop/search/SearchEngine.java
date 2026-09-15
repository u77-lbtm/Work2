package org.skypro.skyshop.search;

public class SearchEngine {

    private final Searchable[] database;
    private int currentSize = 0;

    public SearchEngine(int capacity) {
        this.database = new Searchable[capacity];
    }

    public void add(Searchable item) {
        if (item == null) {
            System.out.println("Нельзя добавить пустой объект (null).");
            return;
        }
        if (currentSize >= database.length) {
            System.out.println("Ошибка: База данных поискового движка переполнена!");
            return;
        }
        database[currentSize] = item;
        currentSize++;
    }

    public Searchable[] search(String query) {
        Searchable[] results = new Searchable[5];

        // Если запрос пустой, сразу возвращаем пустой массив из null
        if (query == null || query.isBlank()) {
            return results;
        }

        String lowerCaseQuery = query.toLowerCase();
        int foundCount = 0;

        // Перебираем только заполненные ячейки нашего массива database
        for (int i = 0; i < currentSize; i++) {
            Searchable item = database[i];

            // Используем универсальный метод getSearchTerm() для поиска по товарам и статьям
            if (item.getSearchTerm().toLowerCase().contains(lowerCaseQuery)) {
                results[foundCount] = item;
                foundCount++;

                // Если нашли 5 элементов — прекращаем поиск
                if (foundCount == 5) {
                    break;
                }
            }
        }

        return results;
    }
}



