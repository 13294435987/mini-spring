package com.baymax.minis.spring.beans.factory.config;

import java.util.*;

/**
 * 封装管理多个constructor-arg标签的操作
 *
 * @author hujiabin wrote in 2024/1/14 20:46
 */
public class ConstructorArgumentValues {

    private final List<ConstructorArgumentValue> argumentValueList = new ArrayList<>();


    public ConstructorArgumentValues() {
    }

    public void addArgumentValue(ConstructorArgumentValue argumentValue) {
        argumentValueList.add(argumentValue);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        return argumentValueList.get(index);
    }

    public int getArgumentCount() {
        return argumentValueList.size();
    }

    public boolean isEmpty() {
        return argumentValueList.isEmpty();
    }
}
