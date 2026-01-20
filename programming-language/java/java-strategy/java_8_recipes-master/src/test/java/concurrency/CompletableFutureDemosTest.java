package concurrency;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CompletableFutureDemosTest {
    private CompletableFutureDemos demo = new CompletableFutureDemos();

    @Test
    public void testRemote() throws Exception {
        Product product = demo.getProduct(1).get();
        assertEquals(1, product.getId());
    }

    @Test
    public void testLocal() throws Exception {
        demo.getProduct(1).get();
        Product product = demo.getProduct(1).get();
        assertEquals(1, product.getId());
    }

    @Test(expected = ExecutionException.class)
    public void testException() throws Exception {
        demo.getProduct(666).get();
    }

    @Test
    public void testExceptionWithCause() throws Exception {
        try {
            demo.getProduct(666).get();
            fail("Houston, we have a problem...");
        } catch (ExecutionException e) {
            assertEquals(ExecutionException.class, e.getClass());
            assertEquals(RuntimeException.class, e.getCause().getClass());
        }
    }

    @Test
    public void getProductAsync() throws Exception {
        Product product = demo.getProductAsync(1).get();
        assertEquals(1, product.getId());
    }
}