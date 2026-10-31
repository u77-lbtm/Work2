package org.skypro.skyshop.search;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.List;
import java.util.Set;



public class SearchEngine {
    private List<Searchable> list;

    public Set<Searchable> search(String query) {
        Comparator<Searchable> searchableComparator = Comparator
                // 1. По длине имени от большего к меньшему (.reversed())
                .comparingInt((Searchable s) -> s.getName() != null ? s.getName().length() : 0).reversed()
                // 2. Если длина равна, сравниваем сами строки по алфавиту
                .thenComparing(Searchable::getName, Comparator.nullsLast(String::compareTo))
                // 3. Защита от дубликатов: если имена полностью идентичны, разделяем по хэш-коду
                .thenComparingInt(System::identityHashCode);
        // 2. Инициализируем TreeSet с созданным компаратором
        Set<Searchable> results = new TreeSet<>(searchableComparator);

        if (query == null || query.trim().isEmpty()) {
            return results;
        }
        String lowerCaseQuery = query.toLowerCase().trim();
        for (Searchable item : list) {
            if (item != null && item.getSearchTerm() != null) {
                String itemTerm = item.getSearchTerm().toLowerCase();
                if (itemTerm.contains(lowerCaseQuery)) {
                    results.add(item);
                }
            }
        }
        return results;
    }

    public Searchable findBestResult(String query) throws BestResultNotFound {
        if (query == null || query.trim().isEmpty()) {
            throw new BestResultNotFound("Поисковый запрос не может быть пустым.");
        }

        String lowerCaseQuery = query.toLowerCase().trim();
        Searchable bestItem = null;
        int maxCount = 0;

        for (Searchable item : list) {
            if (item != null && item.getSearchTerm() != null) {
                String itemTerm = item.getSearchTerm().toLowerCase();
                int count = countOccurrences(itemTerm, lowerCaseQuery);

                if (count > maxCount) {
                    maxCount = count;
                    bestItem = item;
                }
            }
        }

        if (bestItem == null) {
            throw new BestResultNotFound("По запросу '" + query + "' ничего не найдено.");
        }

        return bestItem;
    }

    private int countOccurrences(String text, String target) {
        if (target.isEmpty()) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(target, index)) != -1) {
            count++;
            index += target.length();
        }
        return count;
    }
}
