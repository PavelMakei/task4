package by.makei.shop.model.entity;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable, Cloneable {

    @Override
    public abstract int hashCode() ;

    @Override
    public abstract boolean equals(Object obj);

   //TODO if clone need?
//    @Override
//    protected abstract Object clone() throws CloneNotSupportedException;
}
