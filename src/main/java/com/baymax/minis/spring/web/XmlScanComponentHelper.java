package com.baymax.minis.spring.web;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 用来解析新定义的servlet.xml 标签结构
 *
 * @author hujiabin wrote in 2024/1/30 15:53
 */
public class XmlScanComponentHelper {

    /**
     * 获取配置的包扫描路径
     *
     * @param xmlPath xmlPath
     * @return list
     */
    public static List<String> getNodeValue(URL xmlPath) {
        List<String> packages = new ArrayList<>();
        SAXReader saxReader = new SAXReader();
        Document document = null;

        try {
            document = saxReader.read(xmlPath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element root = Objects.requireNonNull(document).getRootElement();
        Iterator<?> it = root.elementIterator();

        while (it.hasNext()) {
            Element element = (Element) it.next();
            // 解析出配置的 包扫描路径
            packages.add(element.attributeValue("base-package"));
        }

        return packages;
    }
}
