package com.cymal.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DchServiceConfigCenter {
    private static DchServiceConfigCenter configCenter;
    private static final Map<String, DchServiceConfig> SERVICE_LIST = new ConcurrentHashMap<>();

    private DchServiceConfigCenter() {
    }

    public static DchServiceConfigCenter getInstance() {
        if (configCenter == null) {
            synchronized (DchServiceConfigCenter.class) {
                if (configCenter == null) {
                    configCenter = new DchServiceConfigCenter();
                }
            }
        }
        return configCenter;
    }

    public DchServiceConfig getDchServiceConfig(String interfaceName) {
        return SERVICE_LIST.get(interfaceName);
    }

    public void registryDchService(String interfaceName, DchServiceConfig dchServiceConfig) {
        registryDchService(interfaceName, new DchServiceConfig[]{dchServiceConfig});
    }

    public void registryDchService(String interfaceName, DchServiceConfig ... dchServiceConfigs) {
        Arrays.stream(dchServiceConfigs).forEach(dchServiceConfig -> {
            SERVICE_LIST.put(interfaceName, dchServiceConfig);
        });
    }

    public void registryDchService(String interfaceName, Collection<DchServiceConfig> dchServiceConfigs) {
        registryDchService(interfaceName, dchServiceConfigs.toArray(new DchServiceConfig[dchServiceConfigs.size()]));
    }

}
