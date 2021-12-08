package com.alibaba.jvm.sandbox.repeater.plugin.domain;


/**
 * {@link InvokeType } 定义一种调用类型
 * <p>
 *
 * @author zhaoyb1990
 */
public class InvokeType implements java.io.Serializable {

    public static final InvokeType HTTP = new InvokeType("http");

    public static final InvokeType JAVA = new InvokeType("java");

    public static final InvokeType MYBATIS = new InvokeType("mybatis");

    public static final InvokeType IBATIS = new InvokeType("ibatis");

    public static final InvokeType REDIS = new InvokeType("redis");

    public static final InvokeType DUBBO = new InvokeType("dubbo");

    public static final InvokeType HIBERNATE = new InvokeType("hibernate");

    public static final InvokeType JPA = new InvokeType("jpa");

    public static final InvokeType SOCKETIO = new InvokeType("socketio");

    public static final InvokeType OKHTTP = new InvokeType("okhttp");

    public static final InvokeType APACHE_HTTP_CLIENT = new InvokeType("apache-http-client");

    public static final InvokeType GUAVA_CACHE = new InvokeType("guava-cache");

    public static final InvokeType EH_CACHE = new InvokeType("eh-cache");

    public static final InvokeType CAFFEINE_CACHE = new InvokeType("caffeine-cache");

    public static final InvokeType MYBATIS_PLUS = new InvokeType("mybatis-plus");

    public static final InvokeType OPENFEIGN = new InvokeType("openfeign");

    private final String name;

    public InvokeType(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvokeType that = (InvokeType) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }

    @Override
    public String toString() {
        return "InvokeType{" +
                "name='" + name + '\'' +
                '}';
    }
}