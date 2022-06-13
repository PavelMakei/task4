package by.makei.shop.model.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private static Map<Integer,Integer> productIdQuantity;
private static Order order;

    @BeforeAll
    @Test
    public static void init(){
        order = new Order();
        productIdQuantity = new HashMap<>();
        productIdQuantity.put(1,1);
        productIdQuantity.put(2,2);
        productIdQuantity.put(3,3);
    }

    @Test
    void getProdIdQuantityShouldReturnTheSameSizeAndValueTest() {
        order.setProdIdQuantity(productIdQuantity);
        int actualValue = order.getProdIdQuantity().get(2);
        int expectedValue = 2;
        int actualSize = order.getProdIdQuantity().size();
        int expectedSize = 3;
        assertEquals(expectedValue,actualValue);
        assertEquals(expectedSize,actualSize);
    }

    @Test
    void setProdIdQuantityShouldReturnCopyMapTest() {
        order.setProdIdQuantity(productIdQuantity);
        Map<Integer, Integer> actual = order.getProdIdQuantity();
        Map<Integer, Integer> expected = productIdQuantity;
        assertNotSame(expected, actual);
        assertEquals(expected,actual);
    }
}