package org.skypro.skyshop.product;

public class DiscountedProduct extends Product {
    private final int basePrice;
    private final int discount; // Скидка в процентах

    public DiscountedProduct(String name, int basePrice, int discount) {
        super(name);
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Базовая цена должна быть больше 0");
        }
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Скидка должна быть в диапазоне от 0 до 100%");
        }
        this.basePrice = basePrice;
        this.discount = discount;
    }

    @Override
    public int getPrice() {
        return basePrice - (basePrice * discount / 100);
    }

    @Override
    public boolean isSpecial() {
        return true; // Так как товар со скидкой
    }

    // === РЕАЛИЗАЦИЯ МЕТОДОВ ИНТЕРФЕЙСА SEARCHABLE ===

    @Override
    public String getSearchTerm() {
        // Поисковый запрос для товара — это его имя (унаследованное от Product)
        return getName();
    }

    @Override
    public String getContentType() {
        // Возвращает тип контента в соответствии с требованиями
        return "Товар";
    }

    @Override
    public String toString() {
        return getName() + ": цена " + getPrice() + " руб. (скидка " + discount + "%)";
    }
}
