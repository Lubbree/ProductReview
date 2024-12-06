package sysc4806.project.productreview;

import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DistanceTest {

    @Test
    public void noFollowing() {
        Long c1Id = 1L;

        Customer c1 = new Customer();
        c1.setUserId(c1Id);
        Map<Long, Integer> map = ReviewController.distance(c1);
        assertTrue(!map.isEmpty());
        assertTrue(map.containsKey(c1Id));
        assertEquals(1, map.size());
    }

    @Test
    public void manyFollowing() {
        Long c1Id = 1L;
        Long c2Id = 2L;
        Long c3Id = 3L;

        Customer c1 = new Customer();
        c1.setUserId(c1Id);

        Customer c2 = new Customer();
        c2.setUserId(c2Id);
        c2.addFollowing(c1);

        Customer c3 = new Customer();
        c3.setUserId(c3Id);
        c3.addFollowing(c2);

        Map<Long, Integer> map = ReviewController.distance(c1);
        assertTrue(!map.isEmpty());
        assertTrue(map.containsKey(c1Id));
        assertEquals(0, map.get(c1Id));
        assertTrue(map.containsKey(c2Id));
        assertEquals(1, map.get(c2Id));
        assertTrue(map.containsKey(c3Id));
        assertEquals(2, map.get(c3Id));
        assertEquals(3, map.size());
    }

    @Test
    public void notAllFollowing() {
        Long c1Id = 1L;
        Long c2Id = 2L;
        Long c3Id = 3L;
        Long c4Id = 4L;

        Customer c1 = new Customer();
        c1.setUserId(c1Id);

        Customer c2 = new Customer();
        c2.setUserId(c2Id);
        c2.addFollowing(c1);

        Customer c3 = new Customer();
        c3.setUserId(c3Id);
        c3.addFollowing(c2);

        Customer c4 = new Customer();
        c4.setUserId(c4Id);

        Map<Long, Integer> map = ReviewController.distance(c1);
        assertTrue(!map.isEmpty());
        assertTrue(map.containsKey(c1Id));
        assertEquals(0, map.get(c1Id));
        assertTrue(map.containsKey(c2Id));
        assertEquals(1, map.get(c2Id));
        assertTrue(map.containsKey(c3Id));
        assertEquals(2, map.get(c3Id));
        assertFalse(map.containsKey(c4Id));
        assertEquals(3, map.size());
    }
}
