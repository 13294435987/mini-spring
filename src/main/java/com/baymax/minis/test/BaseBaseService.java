package com.baymax.minis.test;

/**
 * test class BaseBaseService
 *
 * @author hujiabin
 */
@SuppressWarnings("all")
public class BaseBaseService {
    private AServiceImpl as;

    public AServiceImpl getAs() {
        return as;
    }

    public void setAs(AServiceImpl as) {
        this.as = as;
    }

    public BaseBaseService() {
    }

    public void sayHello() {
        System.out.println("Base Base Service says hello");
    }
}