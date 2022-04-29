package by.makei.shop.model.entity;

import java.util.Arrays;

public class Product extends AbstractEntity{
    private int id;
    private int brandId;
    private int typeId;
    private String productName;
    private String description;
    private double price;
    private String colour;
    private int power;
    private String size;
    private byte[] photo;
//    private int quantity_in_stock;

   public Product(){}

    public Product(int id, int brandId, int typeId, String productName, String description, double price, String colour, int power, String size, byte[] photo) {
        this.id = id;
        this.brandId = brandId;
        this.typeId = typeId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.colour = colour;
        this.power = power;
        this.size = size;
        this.photo = photo;
    }

    public Product(int brandId, int typeId, String productName, String description, double price, String colour, int power, String size, byte[] photo) {
        this.brandId = brandId;
        this.typeId = typeId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.colour = colour;
        this.power = power;
        this.size = size;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (brandId != product.brandId) return false;
        if (typeId != product.typeId) return false;
        if (Double.compare(product.price, price) != 0) return false;
        if (power != product.power) return false;
        if (productName != null ? !productName.equals(product.productName) : product.productName != null) return false;
        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        if (colour != null ? !colour.equals(product.colour) : product.colour != null) return false;
        if (size != null ? !size.equals(product.size) : product.size != null) return false;
        return Arrays.equals(photo, product.photo);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + brandId;
        result = 31 * result + typeId;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (colour != null ? colour.hashCode() : 0);
        result = 31 * result + power;
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Product{");
        sb.append("id=").append(id);
        sb.append(", brand_id=").append(brandId);
        sb.append(", type_id=").append(typeId);
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", details='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", colour='").append(colour).append('\'');
        sb.append(", power=").append(power);
        sb.append(", size='").append(size).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
