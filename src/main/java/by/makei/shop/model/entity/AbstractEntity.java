package by.makei.shop.model.entity;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable{

    @Override
    public abstract int hashCode() ;

    @Override
    public abstract boolean equals(Object obj);
}
