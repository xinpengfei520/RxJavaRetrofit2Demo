package com.xpf.rxjavaretrofit2demo.bean;

import java.util.List;

/**
 * Created by x-sir on 2019-05-25 :)
 * Function:
 */
public class User {

    public String name;
    public List<Address> addressList;

    public static class Address {
        public String city;
        public String street;
    }
}
