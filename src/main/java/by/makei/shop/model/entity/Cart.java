package by.makei.shop.model.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Cart extends AbstractEntity {
    private static final long serialVersionUID = -8505218436939515801L;
    public static final int MAX_QUANTITY_OF_ONE_PRODUCT_TO_BY = 10;
    private int maxQuantityOfOneProductToBy = MAX_QUANTITY_OF_ONE_PRODUCT_TO_BY;
    private Map<Product, Integer> productQuantity;
    private int totalQuantity = 0;

    public Cart() {
        productQuantity = new HashMap<>();
    }

    public void setProductQuantity(Product product, Integer productQuantity) {
            this.productQuantity.put(product, productQuantity);
            totalProductRecount();
    }

    public Map<Product,Integer> getProductQuantity() {
        return productQuantity;
    }

    public void clear(){
        productQuantity.clear();
        totalQuantity = 0;
    }

    public Map<Product,Integer> removeProduct(Product product, Integer productQuantity){
        Integer savedQuantity = this.productQuantity.get(product);
        this.productQuantity.put(product,(savedQuantity - productQuantity));
        totalProductRecount();
        return this.productQuantity;
    }

    private void totalProductRecount(){
        AtomicInteger count = new AtomicInteger();
        productQuantity.forEach((k, v)-> count.addAndGet(v));
        totalQuantity = count.get();
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getMaxQuantityOfOneProductToBy() {
        return maxQuantityOfOneProductToBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return productQuantity != null ? productQuantity.equals(cart.productQuantity) : cart.productQuantity == null;
    }

    @Override
    public int hashCode() {
        return productQuantity != null ? productQuantity.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cart{");
        sb.append("productIdQuantity=").append(productQuantity);
        sb.append('}');
        return sb.toString();
    }
}
