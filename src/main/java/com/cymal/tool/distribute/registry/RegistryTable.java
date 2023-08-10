package com.cymal.tool.distribute.registry;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The Class Provider a "Service Registry Table".
 */
public class RegistryTable {

    private static final String DEFAULT_NAME = "DEFAULT_NAME";
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private Map<String, Map<String, Instance>> dict = new HashMap<>();

    /**
     * Invoke this method will put a instance into "Service Registry Table".
     * @param instance Service Instance form client.
     * @return true if put success or false.
     */
    public boolean registry(Instance instance) {
        boolean rs = false;
        if (!exists(instance)) {
            ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
            try {
                if (writeLock.tryLock()) {
                    dict.put(instance.getName(), Map.of(instance.getHost(), instance));
                    rs = true;
                }
            } finally {
                writeLock.unlock();
            }
        }
        return rs;
    }

    public boolean registry(Instance [] instances) {
        for(Instance instance : instances) {

        }
    }

    public boolean registry(Collection<Instance> instances) {
        return registry(instances.toArray(new Instance[instances.size()]));
    }

    /**
     * Determine whether the instance is in the "Service Registry Table".
     * @param instance Service Instance form client.
     * @return ture if the instance(@param) is in the "Service Registry Table" or false.
     */
    private boolean exists(Instance instance) {
        if (StringUtils.isBlank(instance.getName())) {
            instance.setName(DEFAULT_NAME);
        }
        return dict.containsValue(instance);
    }

}
