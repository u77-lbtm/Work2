package org.skypro.skyshop.basket;
import org.skypro.skyshop.product.Product;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class ProductBasket {


    private final LinkedList<Product> basket = new LinkedList<>();

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

        for (Product product : basket) {
            total += product.getPrice();
        }
        return total;
    }

    public void printBasket() {
        if (basket.isEmpty()) {
            System.out.println("Корзина пуста");
            return;
        }

        for (Product product : basket) {
            System.out.println(product.getStringRepresentation());
        }
        System.out.println("Итого: " + getTotalPrice());
    }

    public boolean hasProduct(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }

        String lowerCaseName = name.toLowerCase();
        for (Product product : basket) {
            if (product.getName().toLowerCase().equals(lowerCaseName)) {
                return true;
            }
        }
        return false;
    }

    public void clearBasket() {
        basket.clear();
    }

    public List<Product> removeProductByName(String name) {
        List<Product> removedProducts = new LinkedList<>();
        if (name == null || name.isBlank()) {
            return removedProducts;
        }
        String lowerCaseName = name.toLowerCase();
        Iterator<Product> iterator = basket.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getName().toLowerCase().equals(lowerCaseName)) {
                removedProducts.add(product);
                iterator.remove();
            }
        }
        return removedProducts;
    }
}


