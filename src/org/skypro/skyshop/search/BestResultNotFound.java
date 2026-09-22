package org.skypro.skyshop.search;
import java.util.Optional;
import java.util.List;


// Наследуемся от Exception, чтобы оно было проверяемым (checked)
public class BestResultNotFound extends Exception {
    public BestResultNotFound(String message) {
        super(message);
    }
}





