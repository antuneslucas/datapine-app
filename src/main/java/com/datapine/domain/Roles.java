package com.datapine.domain;

public enum Roles {

    ADMIN("ADMIN"),
    USER("USER");

    private final String name;

    Roles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
