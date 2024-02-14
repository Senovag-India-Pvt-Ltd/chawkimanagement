package com.sericulture.controller;


import com.sericulture.model.api.AddChowkiRequest;
import com.sericulture.model.api.AddChowkiResponse;
import com.sericulture.model.dto.ChowkiManagementDTO;
import com.sericulture.service.ChowkiManagementService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/chowkimanagement")
public class ChowkiManagementController {

    @Autowired
    private ChowkiManagementService chowkiManagementService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        response.put("validationErrors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/add-info")
    public AddChowkiResponse insertData(@Valid @RequestBody AddChowkiRequest addChowkiRequest) {
        return chowkiManagementService.insertData(addChowkiRequest);
    }

    @PostMapping("/update-info")
    public AddChowkiResponse updateData(@Valid @RequestBody ChowkiManagementDTO chowkiManagement) {
        return chowkiManagementService.updateData(chowkiManagement);
    }

    @GetMapping("/get-info")
    public List<ChowkiManagementDTO> getAllChowkiManagement() {
        return chowkiManagementService.findAll();
    }

    @GetMapping("/get-info-by-id/{chowki_id}")
    public Optional<ChowkiManagementDTO> getByChowkiId(@PathVariable Integer chowki_id) {
        return chowkiManagementService.getById(chowki_id);
    }

    @DeleteMapping("/delete-info/{id}")
    public AddChowkiResponse deleteDataById(@PathVariable Integer id) {
        return chowkiManagementService.deleteById(id);
    }
}

