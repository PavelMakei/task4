package by.makei.shop.model.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Cart extends AbstractEntity {
    private Map<Integer, Integer> productIdQuantity;

    public Cart() {
        productIdQuantity = new HashMap<>();
    }

    public void addProduct(Integer productId, Integer productQuantity) {
        if (productIdQuantity.containsKey(productId)) {
            Integer savedQuantity = productIdQuantity.get(productId);
            productIdQuantity.put(productId, (productQuantity + savedQuantity));
        } else {
            productIdQuantity.put(productId, productQuantity);
        }
    }

    public Map<Integer,Integer> getProductQuantity() {
        return productIdQuantity;
    }

    public void clear(){
        productIdQuantity.clear();
    }

    public Map<Integer,Integer> removeProduct(Integer productId, Integer productQuantity){
        Integer savedQuantity = productIdQuantity.get(productId);
        productIdQuantity.put(productId,(savedQuantity-productQuantity));
        return productIdQuantity;
    }

    public int productCount(){
        AtomicInteger count = new AtomicInteger();
        productIdQuantity.forEach((k,v)-> count.addAndGet(v));
        return count.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return productIdQuantity != null ? productIdQuantity.equals(cart.productIdQuantity) : cart.productIdQuantity == null;
    }

    @Override
    public int hashCode() {
        return productIdQuantity != null ? productIdQuantity.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cart{");
        sb.append("productIdQuantity=").append(productIdQuantity);
        sb.append('}');
        return sb.toString();
    }
}
