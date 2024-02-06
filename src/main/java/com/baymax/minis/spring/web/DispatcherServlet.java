package com.baymax.minis.spring.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 来解析请求中的路径与业务类 Bean 中方法的映射关系，调用 Bean 的相应方法，返回给 response
 *
 * @author hujiabin wrote in 2024/1/30 16:12
 */
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private WebApplicationContext webApplicationContext;

    /**
     * servlet.xml的配置地址
     */
    private String sContextConfigLocation;

    /**
     * 用于存储所有需要扫描的 package路径列表
     */
    private List<String> packageNames = new ArrayList<>();

    /**
     * 用于存储controller的名称与对象的映射关系
     */
    private final Map<String, Object> controllerObjs = new HashMap<>();

    /**
     * 用于存储controller的名称列表
     */
    private List<String> controllerNames = new ArrayList<>();

    /**
     * 用于存储controller和类名称的映射关系
     */
    private final Map<String, Class<?>> controllerClasses = new HashMap<>();

    /**
     * 用于保存 @RequestMapping 注解的uri名称列表
     *
     * @see RequestMapping
     */
    private final List<String> urlMappingNames = new ArrayList<>();

    /**
     * 保存uri名称与对象映射关系
     */
    private final Map<String, Object> mappingObjs = new HashMap<>();

    /**
     * 保存uri名称与方法名的映射关系
     */
    private final Map<String, Method> mappingMethods = new HashMap<>();

    public DispatcherServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.webApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");

        URL xmlPath = null;

        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);

        refresh();
    }

    protected void refresh() {
        initController();
        initMapping();
    }

    protected void initController() {
        this.controllerNames = scanPackages(packageNames);

        for (String controllerName : this.controllerNames) {
            Object obj;
            Class<?> clz = null;

            try {
                clz = Class.forName(controllerName);
                this.controllerClasses.put(controllerName, clz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                obj = Objects.requireNonNull(clz).newInstance();
                this.controllerObjs.put(controllerName, obj);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> scanPackages(List<String> packages) {
        return packages.stream()
                .flatMap(packageName -> scanPackage(packageName).stream())
                .collect(Collectors.toList());
    }

    private List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        URL url = getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File dir = new File(Objects.requireNonNull(url).getFile());
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                scanPackage(packageName + "." + file.getName());
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }

    protected void initMapping() {
        for (String controllerName : controllerNames) {
            Class<?> clazz = controllerClasses.get(controllerName);
            Object obj = controllerObjs.get(controllerName);
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    String urlMapping = method.getAnnotation(RequestMapping.class).value();
                    urlMappingNames.add(urlMapping);
                    mappingObjs.put(urlMapping, obj);
                    mappingMethods.put(urlMapping, method);
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sPath = request.getServletPath();
        System.out.println(sPath);
        if (!this.urlMappingNames.contains(sPath)) {
            return;
        }

        Object obj;
        Object objResult = null;
        try {
            Method method = this.mappingMethods.get(sPath);
            obj = this.mappingObjs.get(sPath);
            objResult = method.invoke(obj);
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        response.getWriter().append(Objects.requireNonNull(objResult).toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
