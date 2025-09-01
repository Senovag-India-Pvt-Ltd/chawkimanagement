package com.sericulture.controller;


import com.sericulture.helper.Util;
import com.sericulture.model.api.ChowkiManagementByIdDTO;
import com.sericulture.model.api.requests.AddChowkiRequest;
import com.sericulture.model.api.requests.AddSaleDisposalRequest;
import com.sericulture.model.api.response.AddChowkiResponse;
import com.sericulture.model.api.response.CommonChowkiResponse;
import com.sericulture.model.api.ChowkiManagementResponse;
import com.sericulture.model.api.requests.UpdateChowkiRequest;
import com.sericulture.service.ChowkiManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
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

    @PostMapping("/add-sale-disposal-dfl-info")
    public AddChowkiResponse insertSaleAndDisposalDFlDetails(@Valid @RequestBody AddSaleDisposalRequest addSaleDisposalRequest) {
        return chowkiManagementService.insertSaleAndDisposalDFlDetails(addSaleDisposalRequest);
    }


    @PostMapping("/update-info")
    public CommonChowkiResponse updateData(@Valid @RequestBody UpdateChowkiRequest updateChowkiRequest) {
        return chowkiManagementService.updateData(updateChowkiRequest);
    }

    @PostMapping("/update-dfl-info")
    public CommonChowkiResponse updateDFLData(@Valid @RequestBody UpdateChowkiRequest updateChowkiRequest) {
        return chowkiManagementService.updateDFLData(updateChowkiRequest);
    }

    @PostMapping("/update-sale-disposal-dfl-info")
    public CommonChowkiResponse updateSaleDFLData(@Valid @RequestBody UpdateChowkiRequest updateChowkiRequest) {
        return chowkiManagementService.updateSaleDFLData(updateChowkiRequest);
    }

    @GetMapping("/get-info")
    public List<ChowkiManagementByIdDTO> getAllChowkiManagement() {
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

    @GetMapping("/get-sale-disposal-by-fruits-id/{fruitsId}")
    public List<ChowkiManagementResponse> getSaleAndDisposalDetailsByFruitsId(@PathVariable String fruitsId) {
        return chowkiManagementService.getSaleAndDisposalDetailsByFruitsId(fruitsId);
    }

    @GetMapping("/get-sale-disposal-rsso-by-fruits-id/{fruitsId}")
    public List<ChowkiManagementResponse> getSaleAndDisposalDetailsForRssoByFruitsId(@PathVariable String fruitsId) {
        return chowkiManagementService.getSaleAndDisposalDetailsForRssoByFruitsId(fruitsId);
    }

    @GetMapping("/getInspectioninfoForFarmer/{farmerId}")
    public List<ChowkiManagementResponse> InspectioninfoForFarmer(@PathVariable Long farmerId) {
        return chowkiManagementService.getInspectioninfoForFarmer(farmerId);
    }

    @GetMapping("/getInspectioninfoForFarmerFromSaleDisposalOfDFls/{fruitsId}")
    public List<ChowkiManagementResponse> getInspectioninfoForFarmerFromSaleDisposalOfDFls(@PathVariable String fruitsId) {
        return chowkiManagementService.getInspectioninfoForFarmerFromSaleDisposalOfDFls(fruitsId);
    }

    @GetMapping("/getInspectioninfoForCocoonTrack/{fruitsId}")
    public List<ChowkiManagementResponse> InspectioninfoForCocoonTrack(@PathVariable String fruitsId) {
        return chowkiManagementService.getInspectioninfoForCocoonTrack(fruitsId);
    }

    @DeleteMapping("/delete-info/{id}")
    public CommonChowkiResponse deleteDataById(@PathVariable Integer id) {
        return chowkiManagementService.deleteById(id);
    }

    @GetMapping("/getInspectioninfoForCocoonSaleTrack/{fruitsId}")
    public List<ChowkiManagementResponse> getInspectioninfoForCocoonSaleTrack(@PathVariable String fruitsId) {
        return chowkiManagementService.getInspectioninfoForCocoonSaleTrack(fruitsId);
    }

    @GetMapping("/getFarmerDetailsFromSaleDisposalOfDFlsByTsc/{tscMasterId}")
    public List<ChowkiManagementResponse> getFarmerDetailsFromSaleDisposalOfDFlsByTsc(@PathVariable Long tscMasterId) {
        return chowkiManagementService.getFarmerDetailsFromSaleDisposalOfDFlsByTsc(tscMasterId);
    }

    @GetMapping("/getFarmerDetailsFromSaleDisposalOfDFlsRssoByTsc/{tscMasterId}")
    public List<ChowkiManagementResponse> getFarmerDetailsFromSaleDisposalOfDFlsRssoByTsc(@PathVariable Long tscMasterId) {
        return chowkiManagementService.getFarmerDetailsFromSaleDisposalOfDFlsRssoByTsc(tscMasterId);
    }

    @GetMapping("/getFarmerDetailsFromChowkiManagementByTsc/{tscMasterId}")
    public List<ChowkiManagementResponse> getFarmerDetailsFromChowkiManagementByTsc(@PathVariable Long tscMasterId) {
        return chowkiManagementService.getFarmerDetailsFromChowkiManagementByTsc(tscMasterId);
    }

    @PostMapping("/getVerifiedDFLDetails")
    public ResponseEntity<?> getVerifiedDFLDetails(
            @RequestParam(required = false) Long raceId,
            @RequestParam(required = false) Long tscId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "50") int pageSize) {
        return chowkiManagementService.getVerifiedDFLDetails(raceId, tscId, pageNumber, pageSize);
    }

    @PostMapping("/getVerifiedDFLReport")
    public ResponseEntity<?> getVerifiedDFLReport(
            @RequestParam(required = false) Long raceId,
            @RequestParam(required = false) Long tscId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "50") int pageSize) {
        try {
            FileInputStream fileInputStream = chowkiManagementService.getVerifiedDFLReport(raceId, tscId, pageNumber, pageSize);

            InputStreamResource resource = new InputStreamResource(fileInputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=verified_dfl_report" + Util.getISTLocalDate() + ".xlsx");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok().headers(headers).body(resource);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(
                    ex.getMessage().getBytes(StandardCharsets.UTF_8),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}

