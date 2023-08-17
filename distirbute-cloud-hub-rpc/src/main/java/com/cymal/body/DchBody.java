package com.cymal.body;

/**
 * The class describute 'distirbute-cloud-hub-rpc' request body.
 */
public class DchBody {

    /**
     * uuid.
     */
    private Long uuid;

    /**
     * the name of interface.
     */
    private String interfaceName;

    /**
     * the name of method.
     */
    private String methodName;

    /**
     * the types of paramters.
     */
    private Class<?> [] paramtersType;

    /**
     * the values of paramters.
     */
    private Object [] paramtersValue;

    /**
     * the type of return.
     */
    private Class<?> returnType;


    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamtersType() {
        return paramtersType;
    }

    public void setParamtersType(Class<?>[] paramtersType) {
        this.paramtersType = paramtersType;
    }

    public Object[] getParamtersValue() {
        return paramtersValue;
    }

    public void setParamtersValue(Object[] paramtersValue) {
        this.paramtersValue = paramtersValue;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

}
