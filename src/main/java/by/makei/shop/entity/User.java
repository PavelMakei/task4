package by.makei.shop.entity;

import java.math.BigDecimal;
import java.util.Date;

public class User extends AbstractEntity{
    private int id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private String phone;
    private String accessLevel; //TODO enum
    private Date date;
    private BigDecimal amount; //TODO double?


    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return null;
    }
}
