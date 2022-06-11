package by.makei.shop.model.entity;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class Order extends AbstractEntity {
    private int id;
    private int userId;
    private String address;
    private String phone;
    private String details;
    private Date openDate;
    private Date closeDate;
    private Status status;
    private Map<Integer, Integer> prodIdQuantity;

    public Order() {
    }

    public Order(int userId, String address, String phone, String details, Date openDate, Date closeDate, Status status, Map<Integer, Integer> prodIdQuantity) {
        this.userId = userId;
        this.address = address;
        this.phone = phone;
        this.details = details;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.status = status;
        this.prodIdQuantity = prodIdQuantity;
    }

    public Order(int id, int userId, String address, String phone, String details, Date openDate, Date closeDate, Status status, Map<Integer, Integer> prodIdQuantity) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.phone = phone;
        this.details = details;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.status = status;
        this.prodIdQuantity = prodIdQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    @Nullable
    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Map<Integer, Integer> getProdIdQuantity() {
        return prodIdQuantity == null ? Collections.emptyMap() : prodIdQuantity;
    }

    public void setProdIdQuantity(Map<Integer, Integer> prodIdQuantity) {
        this.prodIdQuantity = prodIdQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (userId != order.userId) return false;
        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        if (phone != null ? !phone.equals(order.phone) : order.phone != null) return false;
        if (details != null ? !details.equals(order.details) : order.details != null) return false;
        if (openDate != null ? !openDate.equals(order.openDate) : order.openDate != null) return false;
        if (closeDate != null ? !closeDate.equals(order.closeDate) : order.closeDate != null) return false;
        if (status != order.status) return false;
        return prodIdQuantity != null ? prodIdQuantity.equals(order.prodIdQuantity) : order.prodIdQuantity == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (openDate != null ? openDate.hashCode() : 0);
        result = 31 * result + (closeDate != null ? closeDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (prodIdQuantity != null ? prodIdQuantity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Order{");
        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", address='").append(address).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", details='").append(details).append('\'');
        sb.append(", openDate=").append(openDate);
        sb.append(", closeDate=").append(closeDate);
        sb.append(", status=").append(status);
        sb.append(", prodIdQuantity=").append(prodIdQuantity);
        sb.append('}');
        return sb.toString();
    }

    public enum Status {
        PAID,
        DELIVERED,
        CANCELED;
    }
}
