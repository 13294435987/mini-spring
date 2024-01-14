package com.baymax.minis.spring.util;

import java.util.Iterator;

/**
 * 迭代器类型转换工具类
 *
 * @author hujiabin wrote in 2024/1/14 17:33
 */
public final class IteratorCastUtil {

    private IteratorCastUtil() {
    }

    /**
     * 转换迭代器类型
     *
     * @param iterator 迭代器
     * @param clazz    目标类型
     */
    public static <T> Iterator<T> getTypedIterator(Iterator<?> iterator, Class<T> clazz) {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return clazz.cast(iterator.next());
            }
        };
    }
}
