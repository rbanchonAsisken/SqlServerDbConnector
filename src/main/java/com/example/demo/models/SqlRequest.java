package com.example.demo.models;

import jakarta.validation.constraints.NotEmpty;

import java.util.HashMap;
import java.util.Map;

public class SqlRequest {

    @NotEmpty
    private String sql;

    private Map<String, ?> parameters = new HashMap<>();

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Map<String, ?> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, ?> parameters) {
        this.parameters = parameters;
    }

}
