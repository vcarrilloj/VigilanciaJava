package com.vigilancia.maestria.Json;

public class Command{
    private String code;
    private boolean value;

    public Command(String code, boolean value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
