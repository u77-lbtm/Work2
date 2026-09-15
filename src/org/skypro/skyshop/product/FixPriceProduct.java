package org.skypro.skyshop.product;

public class FixPriceProduct extends Product {
    private static final int FIXED_PRICE = 100; // Пример фиксированной цены, используйте вашу переменную

    public FixPriceProduct(String name) {
        super(name);
    }

    @Override
    public int getPrice() {
        return FIXED_PRICE;
    }

    // === РЕАЛИЗАЦИЯ МЕТОДОВ ИНТЕРФЕЙСА SEARCHABLE ===

    @Override
    public String getSearchTerm() {
        // Поисковый запрос для товара — это его имя
        return getName();
    }

    @Override
    public String getContentType() {
        // Возвращает тип контента
        return "Товар";
    }
}
