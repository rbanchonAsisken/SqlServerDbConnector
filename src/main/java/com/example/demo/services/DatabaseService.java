package com.example.demo.services;

import com.example.demo.models.SqlRequest;
import com.example.demo.models.StoreProcedureRequest;
import com.example.demo.utils.mappers.ResultSetMappers;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio para interactuar con la base de datos.
 */
@Service
public class DatabaseService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    private final DataSource dataSource;

    public DatabaseService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private CallableStatement prepareCallableStatement(@NotNull Connection conn, @NotNull StoreProcedureRequest request) throws SQLException {
        SqlParameterSource namedParameters = new MapSqlParameterSource(request.getParameters());
        ParsedSql parsedSqlObj = NamedParameterUtils.parseSqlStatement(request.getSql());
        String parsedSql = NamedParameterUtils.substituteNamedParameters(parsedSqlObj, namedParameters);
        Object[] values = NamedParameterUtils.buildValueArray(parsedSqlObj, namedParameters, null);
        CallableStatement callableStatement = conn.prepareCall(parsedSql);
        for (int i = 0; i < values.length; i++) {
            callableStatement.setObject(i + 1, values[i]);
        }
        return callableStatement;
    }

    public List<Map<String, Object>> callStoreProcedureReturnList(@NotNull StoreProcedureRequest request) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); CallableStatement callableStatement = prepareCallableStatement(conn, request); ResultSet rs = callableStatement.executeQuery()) {
            while (rs.next()) {
                if (request.hasIndexColumnNames()) {
                    results.add(ResultSetMappers.mapColumnWithList(rs, request.getIndexColumnNames()));
                } else if (request.hasPathCoulumnNames()) {
                    results.add(ResultSetMappers.mapColumnsWithPath(rs, request.getPathColumnNames()));
                } else {
                    results.add(ResultSetMappers.autoMap(rs));
                }
            }
        } catch (SQLException e) {
            var message = "Error al procesar: " + request.getSql();
            logger.error(message, e);
            throw new SQLException(message, e);
        }
        return results;
    }


    public Map<String, Object> callStoreProcedureReturnOne(@NotNull StoreProcedureRequest request) throws SQLException {

        try (Connection conn = dataSource.getConnection(); CallableStatement callableStatement = prepareCallableStatement(conn, request); ResultSet rs = callableStatement.executeQuery()) {
            while (rs.next()) {
                if (request.hasIndexColumnNames()) {
                    return ResultSetMappers.mapColumnWithList(rs, request.getIndexColumnNames());
                } else if (request.hasPathCoulumnNames()) {
                    return ResultSetMappers.mapColumnsWithPath(rs, request.getPathColumnNames());
                } else {
                    return ResultSetMappers.autoMap(rs);
                }
            }
        } catch (SQLException e) {
            var message = "Error al procesar: " + request.getSql();
            logger.error(message, e);
            throw new SQLException(message, e);
        }

        return null;
    }


    public List<Map<String, Object>> callSelectList(@NotNull SqlRequest request) {
        var namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        var params = new MapSqlParameterSource(request.getParameters());
        return namedParameterJdbcTemplate.queryForList(request.getSql(), params);
    }


    public Map<String, Object> callSelectOne(@NotNull SqlRequest request) {
        var namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        var params = new MapSqlParameterSource(request.getParameters());
        return namedParameterJdbcTemplate.queryForMap(request.getSql(), params);
    }

    private PreparedStatement prepareStatement(@NotNull Connection conn, @NotNull StoreProcedureRequest request) throws SQLException {
        SqlParameterSource namedParameters = new MapSqlParameterSource(request.getParameters());
        ParsedSql parsedSqlObj = NamedParameterUtils.parseSqlStatement(request.getSql());

        String parsedSql = NamedParameterUtils.substituteNamedParameters(parsedSqlObj, namedParameters);

        Object[] values = NamedParameterUtils.buildValueArray(parsedSqlObj, namedParameters, null);

        PreparedStatement stmt = conn.prepareStatement(parsedSql);

        for (int i = 0; i < values.length; i++) {
            stmt.setObject(i + 1, values[i]);
        }

        return stmt;
    }


    public Map<String, Object> callSqlOperationOne(@NotNull StoreProcedureRequest request) throws SQLException {

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = prepareStatement(conn, request);) {
            stmt.execute();
            try (var rs = stmt.getResultSet()) {
                if (rs != null) {
                    while (rs.next()) {
                        if (request.hasIndexColumnNames()) {
                            return ResultSetMappers.mapColumnWithList(rs, request.getIndexColumnNames());
                        } else if (request.hasPathCoulumnNames()) {
                            return ResultSetMappers.mapColumnsWithPath(rs, request.getPathColumnNames());
                        } else {
                            return ResultSetMappers.autoMap(rs);
                        }
                    }
                }
                Map<String, Object> result = new HashMap<>();
                result.put("transaccion", true);
                return result;
            }
        } catch (SQLException e) {
            var message = "Error al procesar: " + request.getSql();
            logger.error(message, e);
            throw new SQLException(message, e);
        }
    }

}
