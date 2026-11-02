

package org.skypro.skyshop.search;
// Пустой конструктор по умолчанию

public interface Searchable {
    String getSearchTerm();

    String getContentType();

    String getName();

    default String getStringRepresentation() {
        return getName() + " — " + getContentType();
    }
}



