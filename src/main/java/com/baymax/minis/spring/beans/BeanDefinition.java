package com.baymax.minis.spring.beans;

/**
 * bean定义
 *
 * @author hujiabin wrote in 2024/1/12 18:38
 */
public class BeanDefinition {

    /**
     * 单例
     */
    private static final String SCOPE_SINGLETON = "singleton";

    /**
     * 原型
     */
    private static final String SCOPE_PROTOTYPE = "prototype";

    /**
     * 表示 Bean 要不要在加载的时候初始化
     */
    private boolean lazyInit = true;

    /**
     * 对应bean标签的构造器参数属性
     */
    private ArgumentValues constructorArgumentValues;

    /**
     * 对应bean标签的property标签属性
     */
    private PropertyValues propertyValues;

    /**
     * 初始化方法
     */
    private String initMethodName;

    /**
     * 依赖的bean
     */
    private String[] dependsOn;

    private volatile Object beanClass;

    private String scope = SCOPE_SINGLETON;


    private String id;

    private String className;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public boolean hasBeanClass() {
        return this.beanClass instanceof Class;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public Class<?> getBeanClass() {

        return (Class<?>) this.beanClass;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return this.scope;
    }

    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }

    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public boolean isLazyInit() {
        return this.lazyInit;
    }

    public void setDependsOn(String... dependsOn) {
        this.dependsOn = dependsOn;
    }

    public String[] getDependsOn() {
        return this.dependsOn;
    }

    public void setConstructorArgumentValues(ArgumentValues constructorArgumentValues) {
        this.constructorArgumentValues =
                (constructorArgumentValues != null ? constructorArgumentValues : new ArgumentValues());
    }

    public ArgumentValues getConstructorArgumentValues() {
        return this.constructorArgumentValues;
    }

    public boolean hasConstructorArgumentValues() {
        return !constructorArgumentValues.isEmpty();
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = (propertyValues != null ? propertyValues : new PropertyValues());
    }

    public PropertyValues getPropertyValues() {
        return this.propertyValues;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getInitMethodName() {
        return this.initMethodName;
    }
}
