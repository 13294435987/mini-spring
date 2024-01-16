package com.baymax.minis.spring.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SimpleBeanFactory
 *
 * @author hujiabin wrote in 2024/1/13 22:23
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    /**
     * 存储bean的名称
     */
    private final List<String> beanDefinitionNames = new ArrayList<>();

    /**
     * 存储bean的名称和对应的实例化对象
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * 存储早期为完全实例化的bean（未填充属性），为了解决循环依赖问题
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    public SimpleBeanFactory() {

    }

    /**
     * 启动加载所有的bean对象
     */
    public void refresh() {
        beanDefinitionNames.forEach(beanName -> {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先创建获取bean的实例
        Object singleton = getSingleton(beanName);
        // 不存在 则创建
        if (singleton == null) {
            singleton = earlySingletonObjects.get(beanName);
            if (singleton == null) {
                System.out.println("get bean null -------------- " + beanName);
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (null == beanDefinition) {
                    throw new BeansException("No bean with name " + beanName);
                }
                singleton = createBean(beanDefinition);
                registerBean(beanName, singleton);
                // BeanPostprocessor
                // step 1 : postProcessBeforeInitialization
                // step 2 : afterPropertiesSet
                // step 3 : init-method
                // step 4 : postProcessAfterInitialization。
            }
        }
        if (singleton == null) {
            throw new BeansException("bean is null.");
        }
        return singleton;
    }


    @Override
    public boolean containsBean(String name) {
        return containsSingleton(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return beanDefinitionMap.get(name).getClass();
    }

    /**
     * 注册bean
     *
     * @param beanName bean的名称
     * @param obj      对象
     */
    public void registerBean(String beanName, Object obj) {
        registerSingleton(beanName, obj);

        // BeanPostprocessor
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        beanDefinitionMap.put(name, bd);
        beanDefinitionNames.add(name);
        if (!bd.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        beanDefinitionMap.remove(name);
        beanDefinitionNames.remove(name);
        removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return beanDefinitionMap.containsKey(name);
    }

    private Object createBean(BeanDefinition bd) {
        Class<?> clz = null;
        // 创建早期的bean对象
        Object obj = doCreateBean(bd);
        earlySingletonObjects.put(bd.getId(), obj);
        try {
            clz = Class.forName(bd.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 属性填充
        handleProperties(bd, clz, obj);
        return obj;
    }

    private Object doCreateBean(BeanDefinition bd) {
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try {
            clz = Class.forName(bd.getClassName());
            // 处理构造器参数
            ArgumentValues argumentValues = bd.getConstructorArgumentValues();
            if (!argumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
                Object[] paramValues = new Object[argumentValues.getArgumentCount()];
                for (int i = 0; i < argumentValues.getArgumentCount(); i++) {
                    ArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);
                    // 对于不同类型的属性分别处理
                    if ("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    } else if ("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                    } else if ("int".equals(argumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                    } else {
                        // 默认String
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    }
                }
                // 按照特定的构造器创建实例
                try {
                    con = clz.getConstructor(paramTypes);
                    obj = con.newInstance(paramValues);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalArgumentException |
                         SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                // 如果没有参数直接创建实例
                obj = clz.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(bd.getId() + " bean created. " + bd.getClassName() + " : " + Objects.requireNonNull(obj).toString());
        return obj;
    }

    private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) {
        System.out.println("handle properties for bean : " + bd.getId());
        // 处理属性
        PropertyValues propertyValues = bd.getPropertyValues();
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                // 对每一个属性分数据类型进行处理
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pName = propertyValue.getName();
                String pType = propertyValue.getType();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.getIsRef();
                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];
                if (!isRef) {
                    // 针对不同类型的处理
                    if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                        paramTypes[0] = String.class;
                    } else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                        paramTypes[0] = Integer.class;
                    } else if ("int".equals(pType)) {
                        paramTypes[0] = int.class;
                    } else {
                        // 默认String
                        paramTypes[0] = String.class;
                    }
                    paramValues[0] = pValue;
                } else {
                    // 如果有引用的bean， 创建依赖的bean
                    try {
                        paramTypes[0] = Class.forName(pType);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        paramValues[0] = getBean((String) pValue);
                    } catch (BeansException e) {
                        e.printStackTrace();
                    }
                }
                // 按照setXxxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                Method method = null;
                try {
                    method = Objects.requireNonNull(clz).getMethod(methodName, paramTypes);
                } catch (NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
                try {
                    Objects.requireNonNull(method).invoke(obj, paramValues);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
