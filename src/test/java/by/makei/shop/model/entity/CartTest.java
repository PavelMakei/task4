package by.makei.shop.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    private static final Integer PRODUCT_ID3 = 3;
    private static final Integer PRODUCT_ID5 = 5;
    private static final Integer PRODUCT_QUANTITY = 1;


    Cart cart;
    @BeforeEach
    void init (){
        cart = new Cart();
        cart.addProduct(PRODUCT_ID3, PRODUCT_QUANTITY);
        cart.addProduct(PRODUCT_ID3, PRODUCT_QUANTITY);
        cart.addProduct(PRODUCT_ID5, PRODUCT_QUANTITY);
    }


    @Test
    void getProductQuantityTest() {
        Map<Integer,Integer> productIdQuantity = cart.getProductQuantity();
        int expected = 2;
        int actual = productIdQuantity.size();
        assertEquals(expected, actual);

    }

    @Test
    void clearTest() {
        int expected = 0;
        cart.clear();
        int actual = cart.productCount();
        assertEquals(expected, actual);
    }

    @Test
    void productCountTest() {
        int expected = 3;
        int actual = cart.productCount();
        assertEquals(expected, actual);
    }
}