package com.baymax.minis.spring.beans;

import java.util.*;

/**
 * 封装管理多个constructor-arg标签的操作
 *
 * @author hujiabin wrote in 2024/1/14 20:46
 */
public class ArgumentValues {

    private final List<ArgumentValue> argumentValueList = new ArrayList<>();


    public ArgumentValues() {
    }

    public void addArgumentValue(ArgumentValue argumentValue) {
        argumentValueList.add(argumentValue);
    }

    public ArgumentValue getIndexedArgumentValue(int index) {
        return argumentValueList.get(index);
    }

    public int getArgumentCount() {
        return argumentValueList.size();
    }

    public boolean isEmpty() {
        return argumentValueList.isEmpty();
    }
}
