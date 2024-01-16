package com.baymax.minis.test;

/**
 * test class BaseService
 *
 * @author hujiabin
 */
@SuppressWarnings("all")
public class BaseService {
    private BaseBaseService bbs;

    public BaseBaseService getBbs() {
        return bbs;
    }

    public void setBbs(BaseBaseService bbs) {
        this.bbs = bbs;
    }

    public BaseService() {
    }

    public void sayHello() {
        System.out.print("Base Service says hello");
        bbs.sayHello();
    }
}