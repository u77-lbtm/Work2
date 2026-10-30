package org.skypro.skyshop.product;

import org.skypro.skyshop.search.Searchable;
import java.util.Objects;

public final class Article implements Searchable {
    private final String title;
    private final String text;

    public Article(String title, String text) {
        // Желательно добавить базовую валидацию, как в Product
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Заголовок статьи не может быть пустым");
        }
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Текст статьи не может быть пустым");
        }
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    @Override
    public String getSearchTerm() {
        return title + " " + text;
    }

    @Override
    public String getStringRepresentation() {
        return title + " — Статья";
    }

    // Если методы getContentType() и getName() требуются по заданию,
    // но их нет в интерфейсе Searchable — уберите аннотацию @Override
    public String getContentType() {
        return "Статья";
    }

    public String getName() {
        return title;
    }

    @Override
    public String toString() {
        return title + "\n" + text;
    }

    /**
     * Контракт equals и hashCode для корректной работы HashSet в SearchEngine.
     * Статьи считаются одинаковыми, если у них совпадают заголовки.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(title, article.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
