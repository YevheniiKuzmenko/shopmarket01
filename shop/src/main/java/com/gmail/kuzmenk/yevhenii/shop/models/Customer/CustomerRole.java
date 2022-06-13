package com.gmail.kuzmenk.yevhenii.shop.models.Customer;

public enum CustomerRole {
    ADMIN, User;

    @Override
    public String toString(){
        return "ROLE_"+ name();
    }
}
