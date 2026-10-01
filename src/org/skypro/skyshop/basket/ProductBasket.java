package org.skypro.skyshop.basket;
import org.skypro.skyshop.product.Product;

public class ProductBasket {


    private final MyLinkedList<Product> basket = new MyLinkedList<>();

    public void addProduct(Product product) {
        if (product == null) {
            System.out.println("Нельзя добавить пустой продукт (null).");
            return;
        }
        basket.add(product);
        System.out.println("Успешно");
    }

    public int getTotalPrice() {
        int total = 0;

        for (Product product : basket.toList()) {
            total += product.getPrice();
        }
        return total;
    }

    public void printBasket() {
        if (basket.isEmpty()) {
            System.out.println("Корзина пуста");
            return;
        }

        for (Product product : basket.toList()) {
            System.out.println(product.getStringRepresentation());
        }
        System.out.println("Итого: " + getTotalPrice());
    }

    public boolean hasProduct(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }

        String lowerCaseName = name.toLowerCase();
        for (Product product : basket.toList()) {
            if (product.getName().toLowerCase().equals(lowerCaseName)) {
                return true;
            }
        }
        return false;
    }

    public void clearBasket() {
        basket.clear();
    }
}


