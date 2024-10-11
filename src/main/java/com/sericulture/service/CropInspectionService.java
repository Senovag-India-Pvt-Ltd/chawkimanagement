package com.sericulture.service;

import com.sericulture.helper.Util;
import com.sericulture.model.api.requests.CropInspectionRequest;
import com.sericulture.model.api.requests.MgnregaSchemeRequest;
import com.sericulture.model.api.requests.SupplyOfDisinfectantsRequest;
import com.sericulture.model.api.response.*;
import com.sericulture.model.entity.*;
import com.sericulture.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CropInspectionService {

    @Autowired
    CropInspectionRepository cropInspectionRepository;

    @Autowired
    FitnessCertificateRepository fitnessCertificateRepository;

    @Autowired
    SupplyOfDisinfectantsRepository supplyOfDisinfectantsRepository;

    @Autowired
    MgnregaSchemeRepository mgnregaSchemeRepository;

    @Autowired
    FarmerMulberryExtensionRepository farmerMulberryExtensionRepository;

    public AddChowkiResponse insertData(CropInspectionRequest cropInspectionRequest) {
        AddChowkiResponse addChowkiResponse =new AddChowkiResponse();
        CropInspection cropInspection=new CropInspection();
        try {
            // Fetch farmerId by fruitsId


            cropInspection.setChowkiId(cropInspectionRequest.getChowkiId());
            cropInspection.setFarmerId(cropInspectionRequest.getFarmerId());
            cropInspection.setCropInspectionTypeId(cropInspectionRequest.getCropInspectionTypeId());
            cropInspection.setDate(cropInspectionRequest.getDate());
            cropInspection.setReasonId(cropInspectionRequest.getReasonId());
            cropInspection.setNote(cropInspectionRequest.getNote());
            cropInspection.setCropStatusId(cropInspectionRequest.getCropStatusId());
            cropInspection.setMountId(cropInspectionRequest.getMountId());
            cropInspectionRepository.save(cropInspection);

            addChowkiResponse.setError(0);
            addChowkiResponse.setMessage("Data added successfully!");
        }
        catch(Exception E){
            addChowkiResponse.setError(1);
            addChowkiResponse.setMessage("Selected Crop Inspection is invalid or something else went wrong; please try again!");
            log.error("EXCEPTION : {}",E);
        }
        return addChowkiResponse;
    }

    public List<CropInspectionResponse> getInspectionDetails(Long chowkiId) {
        List<Object[]> cropInspectionDetails = cropInspectionRepository.getInspectionDetails(chowkiId);
        List<CropInspectionResponse> responses = new ArrayList<>();

        for (Object[] arr : cropInspectionDetails) {
            CropInspectionResponse response = CropInspectionResponse.builder()
                    .cropDate(Util.objectToString(arr[0]))
                    .note(Util.objectToString(arr[1]))
                    .cropStatusName(Util.objectToString(arr[2]))
                    .mountName(Util.objectToString(arr[3]))
                    .reasonName(Util.objectToString(arr[4]))
                    .chowkiId(Util.objectToLong(arr[5]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public AddChowkiResponse insertFitnessData(CropInspectionRequest cropInspectionRequest) {
        AddChowkiResponse addChowkiResponse =new AddChowkiResponse();
        FitnessCertificate fitnessCertificate=new FitnessCertificate();
        try {
            // Fetch farmerId by fruitsId


            fitnessCertificate.setChowkiId(cropInspectionRequest.getChowkiId());
            fitnessCertificate.setFarmerId(cropInspectionRequest.getFarmerId());
            fitnessCertificate.setExpectedCocoon(cropInspectionRequest.getExpectedCocoon());
            fitnessCertificate.setLotTestDetails(cropInspectionRequest.getLotTestDetails());
            fitnessCertificate.setDiseaseStatusId(cropInspectionRequest.getDiseaseStatusId());
            fitnessCertificate.setExpectedMarkerDate(cropInspectionRequest.getExpectedMarkerDate());
            fitnessCertificate.setNoOfChandies(cropInspectionRequest.getNoOfChandies());
            fitnessCertificateRepository.save(fitnessCertificate);

            addChowkiResponse.setError(0);
            addChowkiResponse.setMessage("Data added successfully!");
        }
        catch(Exception E){
            addChowkiResponse.setError(1);
            addChowkiResponse.setMessage("Selected Fitness Certificate is invalid or something else went wrong; please try again!");
            log.error("EXCEPTION : {}",E);
        }
        return addChowkiResponse;
    }

    public List<CropInspectionResponse> getInspectionTypeForCrop(Long chowkiId) {
        List<Object[]> cropInspectionDetails = cropInspectionRepository.getInspectionTypeForCrop(chowkiId);
        List<CropInspectionResponse> responses = new ArrayList<>();

        for (Object[] arr : cropInspectionDetails) {
            CropInspectionResponse response = CropInspectionResponse.builder()
                    .cropInspectionTypeId(Util.objectToLong(arr[0]))
                    .cropInspectionTypeName(Util.objectToString(arr[1]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public AddChowkiResponse insertFarmerMulberryExtension(CropInspectionRequest cropInspectionRequest) {
        AddChowkiResponse addChowkiResponse =new AddChowkiResponse();
        FarmerMulberryExtension farmerMulberryExtension=new FarmerMulberryExtension();
        try {
            // Fetch farmerId by fruitsId


            farmerMulberryExtension.setFarmerLandDetailsId(cropInspectionRequest.getFarmerLandDetailsId());
            farmerMulberryExtension.setFarmerId(cropInspectionRequest.getFarmerId());
            farmerMulberryExtension.setMulberryArea(cropInspectionRequest.getMulberryArea());
            farmerMulberryExtension.setMulberryVarietyId(cropInspectionRequest.getMulberryVarietyId());
            farmerMulberryExtension.setExtensionDate(cropInspectionRequest.getExtensionDate());
            farmerMulberryExtension.setPhotoPath(cropInspectionRequest.getPhotoPath());
            farmerMulberryExtensionRepository.save(farmerMulberryExtension);

            addChowkiResponse.setError(0);
            addChowkiResponse.setMessage("Data added successfully!");
        }
        catch(Exception E){
            addChowkiResponse.setError(1);
            addChowkiResponse.setMessage("Selected Farmer Mulberry Extension is invalid or something else went wrong; please try again!");
            log.error("EXCEPTION : {}",E);
        }
        return addChowkiResponse;
    }

    public List<SupplyOfDisinfectantsResponse> findAll() {
        return supplyOfDisinfectantsRepository.getByUserMasterIdOrderBySupplyOfDisinfectantsIdDesc(Util.getUserId(Util.getTokenValues()));
    }

    public AddChowkiResponse insertSupplyOfDisinfectantsData(SupplyOfDisinfectantsRequest supplyOfDisinfectantsRequest) {
        AddChowkiResponse addChowkiResponse =new AddChowkiResponse();
        SupplyOfDisinfectants supplyOfDisinfectants=new SupplyOfDisinfectants();
        try {

            supplyOfDisinfectants.setFarmerId(supplyOfDisinfectantsRequest.getFarmerId());
            supplyOfDisinfectants.setDisinfectantMasterId(supplyOfDisinfectantsRequest.getDisinfectantMasterId());
            supplyOfDisinfectants.setInvoiceNoDate(supplyOfDisinfectantsRequest.getInvoiceNoDate());
            supplyOfDisinfectants.setQuantity(supplyOfDisinfectantsRequest.getQuantity());
            supplyOfDisinfectants.setDisinfectantName(supplyOfDisinfectantsRequest.getDisinfectantName());
            supplyOfDisinfectants.setQuantitySupplied(supplyOfDisinfectantsRequest.getQuantitySupplied());
            supplyOfDisinfectants.setSuppliedDate(supplyOfDisinfectantsRequest.getSuppliedDate());
            supplyOfDisinfectants.setNumbersOfDfls(supplyOfDisinfectantsRequest.getNumbersOfDfls());
            supplyOfDisinfectants.setSizeOfRearingHouse(supplyOfDisinfectantsRequest.getSizeOfRearingHouse());
            supplyOfDisinfectants.setUserMasterId(Util.getUserId(Util.getTokenValues()));
            supplyOfDisinfectantsRepository.save(supplyOfDisinfectants);
            addChowkiResponse.setError(0);
            addChowkiResponse.setMessage("Data added successfully!");
        }
        catch(Exception E){
            addChowkiResponse.setError(1);
            addChowkiResponse.setMessage("Selected district is invalid or something else went wrong; please try again!");
            log.error("EXCEPTION : {}",E);
        }
        return addChowkiResponse;
    }

    public CommonChowkiResponse updateSupplyOfDisinfectantsData(SupplyOfDisinfectantsRequest supplyOfDisinfectantsRequest) {
        CommonChowkiResponse commonChowkiResponse = new CommonChowkiResponse();
        Long userMasterId = Util.getUserId(Util.getTokenValues());

        // Fetch the existing record from the database
        SupplyOfDisinfectants existingDisinfectant = supplyOfDisinfectantsRepository
                .findBySupplyOfDisinfectantsIdAndUserMasterIdAndActive(supplyOfDisinfectantsRequest.getSupplyOfDisinfectantsId(), userMasterId, true);

        if (existingDisinfectant == null) {
            commonChowkiResponse.setError(1);
            commonChowkiResponse.setMessage("Invalid ID");
        } else {
            try {
                // Update only the necessary fields (timestamps are managed automatically)
                existingDisinfectant.setFarmerId(supplyOfDisinfectantsRequest.getFarmerId());
                existingDisinfectant.setDisinfectantMasterId(supplyOfDisinfectantsRequest.getDisinfectantMasterId());
                existingDisinfectant.setInvoiceNoDate(supplyOfDisinfectantsRequest.getInvoiceNoDate());
                existingDisinfectant.setQuantity(supplyOfDisinfectantsRequest.getQuantity());
                existingDisinfectant.setDisinfectantName(supplyOfDisinfectantsRequest.getDisinfectantName());
                existingDisinfectant.setQuantitySupplied(supplyOfDisinfectantsRequest.getQuantitySupplied());
                existingDisinfectant.setSuppliedDate(supplyOfDisinfectantsRequest.getSuppliedDate());
                existingDisinfectant.setNumbersOfDfls(supplyOfDisinfectantsRequest.getNumbersOfDfls());
                existingDisinfectant.setSizeOfRearingHouse(supplyOfDisinfectantsRequest.getSizeOfRearingHouse());
                existingDisinfectant.setUserMasterId(userMasterId);

                // Save the updated entity
                supplyOfDisinfectantsRepository.save(existingDisinfectant);
                commonChowkiResponse.setError(0);
                commonChowkiResponse.setMessage("Data updated successfully!");
            } catch (Exception E) {
                commonChowkiResponse.setError(1);
                commonChowkiResponse.setMessage("Something went wrong; please try again!");
                log.error("EXCEPTION : {}", E);
            }
        }

        return commonChowkiResponse;
    }

    public List<MgnregaScheme> findMgnregaSchemeAll() {
        return mgnregaSchemeRepository.findByActiveOrderByMgnregaSchemeIdDesc(true);
    }

    public AddChowkiResponse insertMgnregaSchemeData(MgnregaSchemeRequest mgnregaSchemeRequest) {
        AddChowkiResponse addChowkiResponse =new AddChowkiResponse();
        MgnregaScheme mgnregaScheme=new MgnregaScheme();
        try {

            mgnregaScheme.setAcresPlanted(mgnregaSchemeRequest.getAcresPlanted());
            mgnregaScheme.setSpacingFollwedFeet(mgnregaSchemeRequest.getSpacingFollwedFeet());
            mgnregaScheme.setSpacingProcuredNos(mgnregaSchemeRequest.getSpacingProcuredNos());
            mgnregaScheme.setSpacingFollowed(mgnregaSchemeRequest.getSpacingFollowed());
            mgnregaScheme.setSpacingProcured(mgnregaSchemeRequest.getSpacingProcured());
            mgnregaScheme.setNoOfCuttingPlanted(mgnregaSchemeRequest.getNoOfCuttingPlanted());
            mgnregaScheme.setNoOfSuccessfullSamplings(mgnregaSchemeRequest.getNoOfSuccessfullSamplings());
            mgnregaSchemeRepository.save(mgnregaScheme);
            addChowkiResponse.setError(0);
            addChowkiResponse.setMessage("Data added successfully!");
        }
        catch(Exception E){
            addChowkiResponse.setError(1);
            addChowkiResponse.setMessage("Selected district is invalid or something else went wrong; please try again!");
            log.error("EXCEPTION : {}",E);
        }
        return addChowkiResponse;
    }

    public CommonChowkiResponse updateMgnregaSchemeData(MgnregaSchemeRequest mgnregaSchemeRequest) {
        CommonChowkiResponse commonChowkiResponse = new CommonChowkiResponse();

        // Fetch the existing record from the database
        MgnregaScheme existingMgnregaScheme = mgnregaSchemeRepository
                .findByMgnregaSchemeIdAndActive(mgnregaSchemeRequest.getMgnregaSchemeId(),true);

        if (existingMgnregaScheme == null) {
            commonChowkiResponse.setError(1);
            commonChowkiResponse.setMessage("Invalid ID");
        } else {
            try {
                // Update only the necessary fields (timestamps are managed automatically)
                existingMgnregaScheme.setAcresPlanted(mgnregaSchemeRequest.getAcresPlanted());
                existingMgnregaScheme.setSpacingFollwedFeet(mgnregaSchemeRequest.getSpacingFollwedFeet());
                existingMgnregaScheme.setSpacingProcuredNos(mgnregaSchemeRequest.getSpacingProcuredNos());
                existingMgnregaScheme.setSpacingFollowed(mgnregaSchemeRequest.getSpacingFollowed());
                existingMgnregaScheme.setSpacingProcured(mgnregaSchemeRequest.getSpacingProcured());
                existingMgnregaScheme.setNoOfCuttingPlanted(mgnregaSchemeRequest.getNoOfCuttingPlanted());
                existingMgnregaScheme.setNoOfSuccessfullSamplings(mgnregaSchemeRequest.getNoOfSuccessfullSamplings());
                mgnregaSchemeRepository.save(existingMgnregaScheme);
                commonChowkiResponse.setError(0);
                commonChowkiResponse.setMessage("Data updated successfully!");
            } catch (Exception E) {
                commonChowkiResponse.setError(1);
                commonChowkiResponse.setMessage("Something went wrong; please try again!");
                log.error("EXCEPTION : {}", E);
            }
        }

        return commonChowkiResponse;
    }


}
