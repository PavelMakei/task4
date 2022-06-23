package by.makei.shop.model.entity;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Cart extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = -8505218436939515801L;
    public static final int MAX_QUANTITY_OF_ONE_PRODUCT_TO_BY = 10;
    private int maxQuantityOfOneProductToBy = MAX_QUANTITY_OF_ONE_PRODUCT_TO_BY;
    private final Map<Product, Integer> productQuantity;
    private int totalQuantity = 0;

    private BigDecimal totalCost = BigDecimal.ZERO;

    public Cart() {
        productQuantity = new HashMap<>();
    }

    public void putProductQuantity(@NotNull Product product, @NotNull Integer quantity) {
        this.productQuantity.put(product, quantity);
        totalProductRecount();
    }

    public Map<Product, Integer> getProductQuantity() {
        return new HashMap<>(productQuantity);
    }

    public void clear() {
        productQuantity.clear();
        totalQuantity = 0;
        totalCost = BigDecimal.ZERO;
    }

    public Map<Product, Integer> removeProduct(@NotNull Product product, @NotNull Integer quantity) {
        Integer savedQuantity = this.productQuantity.get(product);
        if (savedQuantity - quantity < 1) {
            productQuantity.remove(product);
        } else {
            this.productQuantity.put(product, (savedQuantity - quantity));
        }
        totalProductRecount();
        return this.productQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getMaxQuantityOfOneProductToBy() {
        return maxQuantityOfOneProductToBy;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    private void totalProductRecount() {
        AtomicInteger count = new AtomicInteger();
        AtomicReference<Double> sumPrice = new AtomicReference<>(0.0);
        productQuantity.forEach((k, v) -> {
            count.addAndGet(v);
            sumPrice.updateAndGet(cost -> (cost + k.getPrice() * v));
        });
        totalCost = new BigDecimal(sumPrice.get());
        totalQuantity = count.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        if (maxQuantityOfOneProductToBy != cart.maxQuantityOfOneProductToBy) return false;
        if (totalQuantity != cart.totalQuantity) return false;
        if (productQuantity != null ? !productQuantity.equals(cart.productQuantity) : cart.productQuantity != null)
            return false;
        return totalCost != null ? totalCost.equals(cart.totalCost) : cart.totalCost == null;
    }

    @Override
    public int hashCode() {
        int result = maxQuantityOfOneProductToBy;
        result = 31 * result + (productQuantity != null ? productQuantity.hashCode() : 0);
        result = 31 * result + totalQuantity;
        result = 31 * result + (totalCost != null ? totalCost.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cart{");
        sb.append("maxQuantityOfOneProductToBy=").append(maxQuantityOfOneProductToBy);
        sb.append(", productQuantity=").append(productQuantity);
        sb.append(", totalQuantity=").append(totalQuantity);
        sb.append(", totalCost=").append(totalCost);
        sb.append('}');
        return sb.toString();
    }
}
