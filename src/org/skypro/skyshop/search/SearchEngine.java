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
            System.out.println("Ошибка: База данных поиского движка переполнена!");
            return;
        }
        database[currentSize] = item;
        currentSize++;
    }

    public Searchable[] search(String query) {
        Searchable[] results = new Searchable[5];

        if (query == null || query.isBlank()) {
            return results;
        }

        String lowerCaseQuery = query.toLowerCase();
        int foundCount = 0;

        for (int i = 0; i < currentSize; i++) {
            Searchable item = database[i];

            if (item.getSearchTerm().toLowerCase().contains(lowerCaseQuery)) {
                results[foundCount] = item;
                foundCount++;

                if (foundCount == 5) {
                    break;
                }
            }
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

        // Приводим запрос к нижнему регистру один раз перед циклом
        String lowerCaseQuery = query.toLowerCase();

        for (int i = 0; i < currentSize; i++) {
            Searchable item = database[i];

            // Приводим поисковый терм элемента к нижнему регистру для регистронезависимого подсчета
            int occurrences = countOccurrences(item.getSearchTerm().toLowerCase(), lowerCaseQuery);

            if (occurrences > maxOccurrences) {
                maxOccurrences = occurrences;
                bestMatch = item;
            }
        }

        // Если совпадений не найдено (или maxOccurrences остался равен 0)
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
}
