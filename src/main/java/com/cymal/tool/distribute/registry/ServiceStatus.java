package com.cymal.tool.distribute.registry;

public enum ServiceStatus {

    health("health"),

    waiting("waiting"),

    fail("fail");

    private String text;

    ServiceStatus(String text) {
        this.text = text;
    }

}
