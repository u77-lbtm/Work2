
package org.skypro.skyshop.search;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SearchEngine {

    private  Set<Searchable> list = new HashSet<>();
    private int currentSize=0;

    // Конструктор с параметром capacity для полной совместимости с App.java
    public SearchEngine(int capacity) {
        this.list = new HashSet<>(capacity);
    }

    // Дополнительный пустой конструктор
    public SearchEngine() {
        this.list = new HashSet<>();
    }

    /**
     * Метод добавления объекта.
     */
    public boolean add(Searchable item) {
        if (item == null) {
            System.out.println("Нельзя добавить пустой объект (null).");
            return list.add(item);
        }



        // Если такого ключа еще нет, значит это новый уникальный элемент


        // Добавляем или обновляем объект
        return list.add(item);
    }

    /**
     * Метод поиска. Возвращает Set<Searchable> и формирует результат через.
     * getSearchTerm() используется для проверки, подходит ли объект под поисковый запрос.
     */
    public Set<Searchable> search(String query) {
        Set<Searchable> results = new HashSet<>();

        if (query == null || query.trim().isEmpty()) {

            return results;
        }

        String lowerCaseQuery = query.toLowerCase();

        List<Searchable> matchedList = new ArrayList<>();
        matchedList.sort(new Comparator<Searchable>() {
            @Override
            public int compare(Searchable o1, Searchable o2) {
                int len1 = o1.getSearchTerm() != null ? o1.getSearchTerm().length() : 0;
                int len2 = o2.getSearchTerm() != null ? o2.getSearchTerm().length() : 0;

                // Сортировка по убыванию длины (от большего к меньшему)
                int lengthCompare = Integer.compare(len2, len1);

                if (lengthCompare != 0) {
                    return lengthCompare;
                }

                String name1 = o1.getName() != null ? o1.getName() : "";
                String name2 = o2.getName() != null ? o2.getName() : "";
                return name1.compareTo(name2);
            }
        });
        return new LinkedHashSet<>(matchedList);
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
        for (Searchable item : list) {
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
