package org.skypro.skyshop.search;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.List;
import java.util.Set;



public class SearchEngine {
    private List<Searchable> list;

    public Set<Searchable> search(String query) {
        // 1. Создаем компаратор, защищенный от коллизий
        Comparator<Searchable> searchableComparator = Comparator
                // Сначала сортируем по алфавиту поискового термина
                .comparing((Searchable s) -> s.getSearchTerm(), Comparator.nullsLast(String::compareToIgnoreCase))
                // Если термины одинаковые, сортируем по системному хэш-коду, чтобы не потерять разные объекты
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
