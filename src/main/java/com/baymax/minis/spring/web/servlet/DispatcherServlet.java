package com.baymax.minis.spring.web.servlet;

import com.baymax.minis.spring.beans.BeansException;
import com.baymax.minis.spring.web.AnnotationConfigWebApplicationContext;
import com.baymax.minis.spring.web.RequestMapping;
import com.baymax.minis.spring.web.WebApplicationContext;
import com.baymax.minis.spring.web.XmlScanComponentHelper;

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

    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";

    private WebApplicationContext webApplicationContext;

    /**
     * 新增 parentApplicationContext 的目的是，把 Listener 启动的上下文和 DispatcherServlet</p>
     * 启动的上下文两者区分开来。按照时序关系，Listener 启动在前，对应的上下文我们把它叫作</p>
     * parentApplicationContext。
     */
    private WebApplicationContext parentApplication;

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

    private HandlerMapping handlerMapping;

    private HandlerAdapter handlerAdapter;

    public DispatcherServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.parentApplication = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");

        URL xmlPath = null;

        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);

        this.webApplicationContext = new AnnotationConfigWebApplicationContext(sContextConfigLocation, this.parentApplication);

        refresh();
    }

    protected void refresh() {
        initController();
        initHandlerMappings(this.webApplicationContext);
        initHandlerAdapters(this.webApplicationContext);
        initViewResolvers(this.webApplicationContext);
    }

    protected void initHandlerMappings(WebApplicationContext wac) {
        this.handlerMapping = new RequestMappingHandlerMapping(wac);

    }

    protected void initHandlerAdapters(WebApplicationContext wac) {
        this.handlerAdapter = new RequestMappingHandlerAdapter(wac);

    }

    protected void initViewResolvers(WebApplicationContext wac) {

    }

    protected void initController() {
        this.controllerNames = Arrays.asList(this.webApplicationContext.getBeanDefinitionNames());

        for (String controllerName : this.controllerNames) {
            try {
                this.controllerClasses.put(controllerName, Class.forName(controllerName));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                this.controllerObjs.put(controllerName, this.webApplicationContext.getBean(controllerName));
                System.out.println("controller : " + controllerName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);
        try {
            doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerMethod handlerMethod = this.handlerMapping.getHandler(request);
        if (handlerMethod == null) {
            return;
        }

        HandlerAdapter ha = this.handlerAdapter;
        ha.handle(request, response, handlerMethod);
    }

}
