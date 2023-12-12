package com.example.demo.controller;

import com.example.demo.models.SqlRequest;
import com.example.demo.models.StoreProcedureRequest;
import com.example.demo.services.DatabaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/db")
public class DataBaseController {

    private final DatabaseService databaseService;

    @Autowired
    public DataBaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @PostMapping("/sp/list")
    public ResponseEntity<List<Map<String, Object>>> callStoreProcedureList(@Valid @RequestBody StoreProcedureRequest request) throws SQLException {
        return ResponseEntity.ok(databaseService.callStoreProcedureReturnList(request));
    }

    @PostMapping("/sp/one")
    public ResponseEntity<Map<String, Object>> callStoreProcedureOne(@Valid @RequestBody StoreProcedureRequest request) throws SQLException {
        return ResponseEntity.ok(databaseService.callStoreProcedureReturnOne(request));
    }

    @PostMapping("/select/list")
    public ResponseEntity<List<Map<String, Object>>> callSelectList(@Valid @RequestBody SqlRequest request) {
        return ResponseEntity.ok(databaseService.callSelectList(request));
    }

    @PostMapping("/select/one")
    public ResponseEntity<Map<String, Object>> callSelectOne(@Valid @RequestBody SqlRequest request) {
        return ResponseEntity.ok(databaseService.callSelectOne(request));
    }

    @PostMapping("/sql-op/one")
    public ResponseEntity<Map<String, Object>> callSqlOperationOne(@Valid @RequestBody StoreProcedureRequest request) throws SQLException {
        return ResponseEntity.ok(databaseService.callSqlOperationOne(request));
    }

}
