package com.sericulture.service;

import com.sericulture.helper.Util;
import com.sericulture.model.api.ChowkiManagementResponse;
import com.sericulture.model.api.requests.AddChowkiRequest;
import com.sericulture.model.api.requests.CropInspectionRequest;
import com.sericulture.model.api.response.AddChowkiResponse;
import com.sericulture.model.api.response.CropInspectionResponse;
import com.sericulture.model.entity.ChowkiManagement;
import com.sericulture.model.entity.CropInspection;
import com.sericulture.model.entity.FarmerMulberryExtension;
import com.sericulture.model.entity.FitnessCertificate;
import com.sericulture.repository.CropInspectionRepository;
import com.sericulture.repository.FarmerMulberryExtensionRepository;
import com.sericulture.repository.FitnessCertificateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CropInspectionService {

    @Autowired
    CropInspectionRepository cropInspectionRepository;

    @Autowired
    FitnessCertificateRepository fitnessCertificateRepository;

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
}
