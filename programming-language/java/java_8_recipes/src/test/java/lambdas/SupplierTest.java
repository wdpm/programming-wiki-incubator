package lambdas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SupplierTest {

    private String getErrorMessage() {
        System.out.println("Inside getErrorMessage()");
        return "Error";
    }

    @Test
    void showError() { 
        boolean x = true;
        assertTrue(x, this::getErrorMessage);
    }
}
