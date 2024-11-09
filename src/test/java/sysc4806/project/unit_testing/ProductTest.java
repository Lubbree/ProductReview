package sysc4806.project.unit_testing;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import sysc4806.project.productreview.Product;
import sysc4806.project.productreview.Review;

import java.util.ArrayList;
import java.util.List;


public class ProductTest {
    private Product product;
    private Review testReview1;
    private Review testReview2;
    private List<Review> testReviews;

    @Before
    public void setUp(){
        product = new Product();
        testReview1 = new Review();
        testReview2 = new Review();
    }

    @Test
    public void testInit(){
        assertNull(product.getName());
        assertNull(product.getCategory());
        assertEquals(new ArrayList<>(), product.getReviews());
    }

    @Test
    public void testSetters(){
        product.setProductId("001");
        assertEquals("001", product.getProductId());

        product.setName("Wooden Table");
        assertEquals("Wooden Table", product.getName());

        product.setCategory("Furniture");
        assertEquals("Furniture", product.getCategory());

        testReviews = new ArrayList<Review>();
        testReviews.add(testReview1);
        testReviews.add(testReview2);
        product.setReviews(testReviews);
        assertEquals(testReviews, product.getReviews());
    }

    @Test
    public void testAddReview(){
        product.addReview(testReview1);
        assertEquals(testReview1, product.getReviews().get(0));

        product.addReview(testReview2);
        assertEquals(testReview2, product.getReviews().get(1));
    }

    @Test
    public void testRemoveReview(){
        product.addReview(testReview1);
        assertEquals(testReview1, product.getReviews().get(0));

        product.addReview(testReview2);
        assertEquals(testReview2, product.getReviews().get(1));

        product.removeReview(testReview1);
        assertEquals(testReview2, product.getReviews().get(0));

        product.removeReview(testReview2);
        assertEquals(new ArrayList<>(), product.getReviews());
    }
}
