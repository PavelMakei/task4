package by.makei.shop.entity;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable, Cloneable {

    @Override
    public abstract int hashCode() ;

    @Override
    public abstract boolean equals(Object obj);

    @Override
    protected abstract Object clone() throws CloneNotSupportedException;
}
