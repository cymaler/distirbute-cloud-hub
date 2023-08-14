package com.cymal.tool.distribute.registry;

import lombok.Data;

import java.util.Objects;

@Data
public class Instance {

    /**
     * 实例Id
     */
    private String id;

    /**
     * Ip
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 实例名称
     */
    private String name;

    /**
     * 服务状态
     */
    private ServiceStatus status;

    /**
     * 服务角色
     */
    private Role role;

    /**
     * 扩展信息
     */
    private String ext;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instance)) return false;
        Instance instance = (Instance) o;
        return getPort() == instance.getPort() && Objects.equals(getId(), instance.getId()) && Objects.equals(getHost(), instance.getHost()) && Objects.equals(getName(), instance.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getHost(), getPort(), getName());
    }

}
