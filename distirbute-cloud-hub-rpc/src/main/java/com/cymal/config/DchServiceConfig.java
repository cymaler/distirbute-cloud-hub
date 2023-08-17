package com.cymal.config;

public class DchServiceConfig<T> {

    private Class<T> interfaceProvider;

    private Object ref;

    private String group = "default";

    public Class<T> getInterfaceProvider() {
        return interfaceProvider;
    }

    public void setInterfaceProvider(Class<T> interfaceProvider) {
        this.interfaceProvider = interfaceProvider;
    }

    public Object getRef() {
        return ref;
    }

    public void setRef(Object ref) {
        this.ref = ref;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
