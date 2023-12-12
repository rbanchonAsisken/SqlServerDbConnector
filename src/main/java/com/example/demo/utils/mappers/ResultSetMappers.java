package com.example.demo.utils.mappers;

import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResultSetMappers {

    public static @NotNull Map<String, Object> autoMap(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        Map<String, Object> row = new LinkedHashMap<>();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            Object columnValue = rs.getObject(i);
            row.put(columnName, columnValue);
        }
        return row;
    }

    public static @NotNull Map<String, Object> mapColumnWithList(ResultSet rs, @NotNull List<String> columnNames) throws SQLException {
        Map<String, Object> row = new LinkedHashMap<>();
        int index = 1;
        for (String column : columnNames) {
            var value = rs.getObject(index);
            row.put(column, value);
            index++;
        }
        return row;
    }

    public static Map<String, Object> mapColumnsWithPath(ResultSet rs, @NotNull Map<String, String> columnNames) throws SQLException {
        Map<String, Object> row = new LinkedHashMap<>();

        for (var path : columnNames.entrySet()) {
            var value = rs.getObject(path.getKey());
            row.put(path.getValue(), value);
        }

        return row;
    }
}
