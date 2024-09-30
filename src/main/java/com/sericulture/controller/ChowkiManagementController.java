package com.sericulture.controller;


import com.sericulture.model.api.ChowkiManagementByIdDTO;
import com.sericulture.model.api.requests.AddChowkiRequest;
import com.sericulture.model.api.response.AddChowkiResponse;
import com.sericulture.model.api.response.CommonChowkiResponse;
import com.sericulture.model.api.ChowkiManagementResponse;
import com.sericulture.model.api.requests.UpdateChowkiRequest;
import com.sericulture.service.ChowkiManagementService;
import jakarta.validation.Valid;
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

    @PostMapping("/add-dfl-info")
    public AddChowkiResponse insertDFLData(@Valid @RequestBody AddChowkiRequest addChowkiRequest) {
        return chowkiManagementService.insertDFLData(addChowkiRequest);
    }


    @PostMapping("/update-info")
    public CommonChowkiResponse updateData(@Valid @RequestBody UpdateChowkiRequest updateChowkiRequest) {
        return chowkiManagementService.updateData(updateChowkiRequest);
    }

    @GetMapping("/get-info")
    public List<ChowkiManagementResponse> getAllChowkiManagement() {
        return chowkiManagementService.findAll();
    }

    @GetMapping("/get-info-by-id/{chowki_id}")
    public Optional<ChowkiManagementByIdDTO> getByChowkiId(@PathVariable Integer chowki_id) {
        return chowkiManagementService.getById(chowki_id);
    }

    @GetMapping("/get-chowki-details-by-farmer-id/{farmerId}")
    public List<ChowkiManagementResponse> getChowkiDetailsByFarmerId(@PathVariable Long farmerId) {
        return chowkiManagementService.getChowkiDetailsByFarmerId(farmerId);
    }

    @GetMapping("/getInspectioninfoForFarmer/{farmerId}")
    public List<ChowkiManagementResponse> InspectioninfoForFarmer(@PathVariable Long farmerId) {
        return chowkiManagementService.getInspectioninfoForFarmer(farmerId);
    }

    @DeleteMapping("/delete-info/{id}")
    public CommonChowkiResponse deleteDataById(@PathVariable Integer id) {
        return chowkiManagementService.deleteById(id);
    }
}

