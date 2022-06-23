package by.makei.shop.model.entity;

import java.io.Serial;

public class ProductType extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = -5295915945980814983L;
    private int id;
    private String typeName;

    public ProductType() {
    }

    public ProductType(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public ProductType(String typeName) {
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductType productType = (ProductType) o;

        if (id != productType.id) return false;
        return typeName != null ? typeName.equals(productType.typeName) : productType.typeName == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Type{");
        sb.append("id=").append(id);
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
