package com.cymal.tool.distribute.registry;

public enum Role {

    common_sevice("common_service"),

    qps_provider("qps_provider")

    ;

    private String text;

    Role(String text) {
        this.text = text;
    }

}
