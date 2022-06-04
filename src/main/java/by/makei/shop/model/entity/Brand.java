package by.makei.shop.model.entity;

import java.io.Serializable;

public class Brand extends AbstractEntity {
    private static final long serialVersionUID = -1205732830733869711L;
    private int id;
    private String brandName;

    public Brand (){};

    public Brand(int id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    public Brand(String brandName) {
        this.brandName = brandName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand brand = (Brand) o;

        if (id != brand.id) return false;
        return brandName != null ? brandName.equals(brand.brandName) : brand.brandName == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Brand{");
        sb.append("id=").append(id);
        sb.append(", brandName='").append(brandName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
