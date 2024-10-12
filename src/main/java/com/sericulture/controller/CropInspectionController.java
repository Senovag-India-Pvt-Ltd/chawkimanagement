package com.sericulture.controller;

import com.sericulture.helper.Util;
import com.sericulture.model.ResponseWrapper;
import com.sericulture.model.api.ChowkiManagementByIdDTO;
import com.sericulture.model.api.ChowkiManagementResponse;
import com.sericulture.model.api.requests.*;
import com.sericulture.model.api.response.*;
import com.sericulture.model.entity.MgnregaScheme;
import com.sericulture.model.entity.SupplyOfDisinfectants;
import com.sericulture.service.ChowkiManagementService;
import com.sericulture.service.CropInspectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/add-supply-of-disinfectants-info")
    public AddChowkiResponse insertSupplyOfDisinfectantsData(@Valid @RequestBody SupplyOfDisinfectantsRequest supplyOfDisinfectantsRequest) {
        return cropInspectionService.insertSupplyOfDisinfectantsData(supplyOfDisinfectantsRequest);
    }

    @GetMapping("/get-supply-of-disinfectants-info")
    public List<SupplyOfDisinfectantsListResponse> getByUserMasterIdOrderBySupplyOfDisinfectantsIdDesc() {
        return cropInspectionService.getByUserMasterIdOrderBySupplyOfDisinfectantsIdDesc(Util.getUserId(Util.getTokenValues()));
    }

    @PostMapping("/update-supply-of-disinfectants-info")
    public CommonChowkiResponse updateSupplyOfDisinfectantsData(@Valid @RequestBody SupplyOfDisinfectantsRequest supplyOfDisinfectantsRequest) {
        return cropInspectionService.updateSupplyOfDisinfectantsData(supplyOfDisinfectantsRequest);
    }

    @PostMapping("/add-mgnrega-scheme-info")
    public AddChowkiResponse insertMgnregaSchemeData(@Valid @RequestBody MgnregaSchemeRequest mgnregaSchemeRequest) {
        return cropInspectionService.insertMgnregaSchemeData(mgnregaSchemeRequest);
    }

    @GetMapping("/get-mgnrega-scheme-info")
    public List<MgnregaScheme> getAllMgnrega() {
        return cropInspectionService.findMgnregaSchemeAll();
    }

    @PostMapping("/update-mgnrega-scheme-info")
    public CommonChowkiResponse updateMgnregaSchemeData(@Valid @RequestBody MgnregaSchemeRequest mgnregaSchemeRequest) {
        return cropInspectionService.updateMgnregaSchemeData(mgnregaSchemeRequest);
    }

    @GetMapping("/get-by-supply-of-disinfectants-id/{supplyOfDisinfectantsId}")
    public Optional<SupplyOfDisinfectantsResponse> getBySupplyOfDisinfectantsId(@PathVariable Long supplyOfDisinfectantsId) {
        return cropInspectionService.getBySupplyOfDisinfectantsId(supplyOfDisinfectantsId);
    }

    @GetMapping("/get-by-mgnrega-scheme-id/{mgnregaSchemeId}")
    public Optional<MgnregaSchemeResponse> getByMgnregaSchemeId(@PathVariable Long mgnregaSchemeId) {
        return cropInspectionService.getByMgnregaSchemeId(mgnregaSchemeId);
    }

    @PostMapping("/add-track-cocoon-info")
    public AddChowkiResponse insertTrackCocoonData(@Valid @RequestBody TrackCocoonRequest trackCocoonRequest) {
        return cropInspectionService.insertTrackCocoonData(trackCocoonRequest);
    }

}
