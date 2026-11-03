package org.skypro.skyshop.search;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchEngine {

    private Set<Searchable> list = new HashSet<>();
    private int currentSize = 0;

    public SearchEngine(int capacity) {
        this.list = new HashSet<>(capacity);
    }

    public SearchEngine() {
        this.list = new HashSet<>();
    }

    public boolean add(Searchable item) {
        if (item == null) {
            System.out.println("Нельзя добавить пустой объект (null).");
            return false;
        }
        boolean isAdded = list.add(item);
        if (isAdded) {
            currentSize++;
        }
        return isAdded;
    }

    public Set<Searchable> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new java.util.TreeSet<>((o1, o2) ->
                    Integer.compare(o2.getSearchTerm().length(), o1.getSearchTerm().length()));
        }

        String lowerCaseQuery = query.toLowerCase().trim();

        return list.stream()
                .filter(item -> item != null && item.getSearchTerm() != null &&
                        item.getSearchTerm().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toCollection(() -> new java.util.TreeSet<>(
                        (o1, o2) -> {
                            int res = Integer.compare(o2.getSearchTerm().length(), o1.getSearchTerm().length());
                            return res != 0 ? res : o1.getSearchTerm().compareTo(o2.getSearchTerm());
                        }
                )));
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
