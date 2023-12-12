package com.example.demo.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreProcedureRequest extends SqlRequest {
    
    private List<String> indexColumnNames = new ArrayList<>();

    private Map<String, String> pathColumnNames = new HashMap<>();


    public List<String> getIndexColumnNames() {
        return indexColumnNames;
    }

    public void setIndexColumnNames(List<String> columnNames) {
        this.indexColumnNames = columnNames;
    }

    public boolean hasIndexColumnNames() {
        return !indexColumnNames.isEmpty();
    }

    public Map<String, String> getPathColumnNames() {
        return pathColumnNames;
    }

    public void setPathColumnNames(Map<String, String> pathColumnNames) {
        this.pathColumnNames = pathColumnNames;
    }

    public boolean hasPathCoulumnNames() {
        return !pathColumnNames.isEmpty();
    }
}
