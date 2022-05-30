package by.makei.shop.model.entity;

import by.makei.shop.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    private static final int ID_ARRAY[] = {3,3,5};
    private static final Integer PRODUCT_QUANTITY = 1;
    private Product product;

    Cart cart;
    @BeforeEach
    void init () throws ServiceException {
        cart = new Cart();
        for (int id: ID_ARRAY) {
            product = new Product();
            product.setId(id);
            cart.setProductQuantity(product, PRODUCT_QUANTITY);
        }
    }


    @Test
    void getProductQuantityTest() {
        Map<Product,Integer> productIdQuantity = cart.getProductQuantity();
        int expected = 2;
        int actual = productIdQuantity.size();
        assertEquals(expected, actual);
    }

    @Test
    void clearTest() {
        int expected = 0;
        cart.clear();
        int actual = cart.getTotalQuantity();
        assertEquals(expected, actual);
    }

    @Test
    void productCountTest() {
        int expected = 2;
        int actual = cart.getTotalQuantity();
        assertEquals(expected, actual);
    }
}