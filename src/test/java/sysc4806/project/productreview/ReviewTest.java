package sysc4806.project.productreview;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDateTime;

public class ReviewTest {
    private Review review;

    @Before
    public void setUp() {
        review = new Review();
    }

    @Test
    public void testInit(){
        assertNull(review.getReviewer());
        assertNull(review.getReviewDate());
        assertNull(review.getReviewText());
        assertNull(review.getProduct());
        assertEquals(0, review.getStarRating());
    }

    @Test
    public void testSetters(){
        Customer reviewer = new Customer();
        LocalDateTime time = LocalDateTime.now();
        Product product = new Product();

        review.setReviewer(reviewer);
        assertEquals(reviewer, review.getReviewer());

        review.setReviewDate(time);
        assertEquals(time, review.getReviewDate());

        review.setReviewText("This is a good review!");
        assertEquals("This is a good review!", review.getReviewText());

        review.setStarRating(4);
        assertEquals(4, review.getStarRating());

        review.setProduct(product);
        assertEquals(product, review.getProduct());
    }
}
