package com.cymal.tool.distribute.hash;

import lombok.Data;

import java.util.Collection;
import java.util.Map;

/**
 * The provide a 'Hash Circle' to resole 'Consistent Hash Algorithm'
 */
public class ConsistentHashCircle {

    /**
     * Max Hash Num.
     */
    private final int MOD = 2 << 31 - 1;

    /**
     * Real Hash Circle
     */
    private final Node[] hashCircle;

    /**
     * Virtual
     */
    private Map<String, Integer> virtualHashCircle;

    /**
     * Constructor.
     */
    public ConsistentHashCircle() {
        hashCircle = new Node[0];
    }

    public ConsistentHashCircle(String[] ips) {
        hashCircle = new Node[ips.length];
    }

    public ConsistentHashCircle(Collection<String> ips) {
        hashCircle = ips.toArray(new Node[ips.size()]);
    }


    @Data
    class Node {

        /**
         * Ip地址.
         */
        private int ip;

        private int locate;

        public Node(String ip) {
            if (checkIp(ip)) {
                this.ip = Integer.parseInt(ip.replaceAll(".", ""));
                this.locate = generatelocate();
            } else {
                throw new IllegalArgumentException("IP arg is Illegal.");
            }
        }

        private boolean checkIp(String ip) {
            String pattern = "^((\\d|([1-9]\\d)|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}(\\d|([1-9]\\d)|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))$";
            return ip.matches(pattern);
        }

        private int generatelocate() {
            int locate = ip % MOD;
            if (virtualHashCircle.get(locate) != null) {
                return reLocate(locate);
            }
            return locate;
        }

        private int reLocate(int locate) {
            return 0;
        }

    }


}
