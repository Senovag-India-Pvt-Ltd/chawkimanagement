package com.sericulture.controller;

import com.sericulture.model.api.ChowkiManagementResponse;
import com.sericulture.model.api.requests.AddChowkiRequest;
import com.sericulture.model.api.requests.CropInspectionRequest;
import com.sericulture.model.api.response.AddChowkiResponse;
import com.sericulture.model.api.response.CropInspectionResponse;
import com.sericulture.service.ChowkiManagementService;
import com.sericulture.service.CropInspectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/cropInspection")
public class CropInspectionController {

    @Autowired
    private CropInspectionService cropInspectionService;

    @PostMapping("/add-crop-inspection-info")
    public AddChowkiResponse insertData(@Valid @RequestBody CropInspectionRequest cropInspectionRequest) {
        return cropInspectionService.insertData(cropInspectionRequest);
    }

    @GetMapping("/getInspectionDetails/{chowkiId}")
    public List<CropInspectionResponse> getInspectionDetails(@PathVariable Long chowkiId) {
        return cropInspectionService.getInspectionDetails(chowkiId);
    }

    @PostMapping("/add-fitness-info")
    public AddChowkiResponse insertFitnessData(@Valid @RequestBody CropInspectionRequest cropInspectionRequest) {
        return cropInspectionService.insertFitnessData(cropInspectionRequest);
    }

    @GetMapping("/getInspectionTypeForCrop/{chowkiId}")
    public List<CropInspectionResponse> getInspectionTypeForCrop(@PathVariable Long chowkiId) {
        return cropInspectionService.getInspectionTypeForCrop(chowkiId);
    }

    @PostMapping("/add-farmer-mulberry-extension-info")
    public AddChowkiResponse insertFarmerMulberryExtension(@Valid @RequestBody CropInspectionRequest cropInspectionRequest) {
        return cropInspectionService.insertFarmerMulberryExtension(cropInspectionRequest);
    }

}
