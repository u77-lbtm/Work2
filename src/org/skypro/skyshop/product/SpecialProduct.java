package org.skypro.skyshop.product;

public class SpecialProduct extends Product {

    private final int basePrice;


    public SpecialProduct(String name, int basePrice) {
        super(name);

        if (basePrice <= 0) {
            throw new IllegalArgumentException("Цена должна быть больше нуля");
        }
        this.basePrice = basePrice;
    }

    // Обязательно реализуем абстрактный метод получения цены
    @Override
    public int getPrice() {
        return this.basePrice;
    }

    // Переопределяем признак специального товара
    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return this.getName() + ": Специальный товар " + this.getPrice();
    }
}
