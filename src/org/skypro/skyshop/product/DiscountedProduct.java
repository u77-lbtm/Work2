package org.skypro.skyshop.product;


public class DiscountedProduct extends Product {

    private final int basePrice;
    private final int discount;


    public DiscountedProduct(String name, int basePrice, int discount) {
        super(name); // Передаем имя в абстрактный класс Product

        if (basePrice <= 0) {
            throw new IllegalArgumentException("Базовая цена должна быть больше нуля");
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
        return true;
    }


    public int getDiscount() {
        return discount;
    }

    public int getBasePrice() {
        return basePrice;
    }


    @Override
    public String toString() {
        return this.getName() + ": " + this.getPrice() + " (" + this.discount + "%)";
    }
}
