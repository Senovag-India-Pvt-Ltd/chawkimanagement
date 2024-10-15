package com.sericulture.service;

import com.sericulture.controller.S3Controller;
import com.sericulture.helper.Util;
import com.sericulture.model.Mapper;
import com.sericulture.model.api.ChowkiManagementByIdDTO;
import com.sericulture.model.api.ChowkiManagementResponse;
import com.sericulture.model.api.requests.CropInspectionRequest;
import com.sericulture.model.api.requests.MgnregaSchemeRequest;
import com.sericulture.model.api.requests.SupplyOfDisinfectantsRequest;
import com.sericulture.model.api.requests.TrackCocoonRequest;
import com.sericulture.model.api.response.*;
import com.sericulture.model.entity.*;
import com.sericulture.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
    TrackCocconRepository trackCocconRepository;

    @Autowired
    MgnregaSchemeRepository mgnregaSchemeRepository;

    @Autowired
    S3Controller s3Controller;

    @Autowired
    Mapper mapper;

    @Autowired
    ChowkiManagementRepository chowkiManagementRepository;

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
            cropInspection.setCropInspectionPath(cropInspectionRequest.getCropInspectionPath());
            cropInspectionRepository.save(cropInspection);

            addChowkiResponse.setError(0);
            addChowkiResponse.setMessage("Data added successfully!");
            addChowkiResponse.setCropInspectionId(cropInspection.getCropInspectionId());
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
            fitnessCertificate.setFitnessCertificatePath(cropInspectionRequest.getFitnessCertificatePath());
            fitnessCertificateRepository.save(fitnessCertificate);

            addChowkiResponse.setError(0);
            addChowkiResponse.setMessage("Data added successfully!");
            addChowkiResponse.setFitnessCertificateId(fitnessCertificate.getFitnessCertificateId());
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
            farmerMulberryExtension.setSpacing(cropInspectionRequest.getSpacing());
            farmerMulberryExtension.setScheme(cropInspectionRequest.getScheme());
            farmerMulberryExtension.setApplicationType(cropInspectionRequest.getApplicationType());
            farmerMulberryExtension.setUprootingDate(cropInspectionRequest.getUprootingDate());
            farmerMulberryExtension.setUprootingReason(cropInspectionRequest.getUprootingReason());
            farmerMulberryExtension.setPhotoPath(cropInspectionRequest.getPhotoPath());
            farmerMulberryExtension.setNoOfSapplings(cropInspectionRequest.getNoOfSapplings());
            farmerMulberryExtensionRepository.save(farmerMulberryExtension);

            addChowkiResponse.setError(0);
            addChowkiResponse.setMessage("Data added successfully!");
            addChowkiResponse.setFarmerMulberryExtensionId(farmerMulberryExtension.getFarmerMulberryExtensionId());
        }
        catch(Exception E){
            addChowkiResponse.setError(1);
            addChowkiResponse.setMessage("Selected Farmer Mulberry Extension is invalid or something else went wrong; please try again!");
            log.error("EXCEPTION : {}",E);
        }
        return addChowkiResponse;
    }

//    public List<SupplyOfDisinfectantsResponse> findAll() {
//        return supplyOfDisinfectantsRepository.getByUserMasterIdOrderBySupplyOfDisinfectantsIdDesc(Util.getUserId(Util.getTokenValues()));
//    }

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
                existingDisinfectant.setSupplyOfDisinfectantsId(supplyOfDisinfectantsRequest.getSupplyOfDisinfectantsId());
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
                existingMgnregaScheme.setMgnregaSchemeId(mgnregaSchemeRequest.getMgnregaSchemeId());
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

    public Optional<SupplyOfDisinfectantsResponse> getBySupplyOfDisinfectantsId(Long supplyOfDisinfectantsId) {
        Long userMasterId=Util.getUserId(Util.getTokenValues());
        Optional<SupplyOfDisinfectantsResponse> supplyOfDisinfectantsResponse=supplyOfDisinfectantsRepository.findBySupplyOfDisinfectantsIdAndUserMasterId(supplyOfDisinfectantsId,userMasterId);
        if(supplyOfDisinfectantsResponse.isEmpty()){
            return Optional.empty();
        }
        return supplyOfDisinfectantsResponse;
    }


    public Optional<MgnregaSchemeResponse> getByMgnregaSchemeId(Long mgnregaSchemeId) {
        Optional<MgnregaSchemeResponse> mgnregaSchemeResponse=mgnregaSchemeRepository.findByMgnregaSchemeId(mgnregaSchemeId);
        if(mgnregaSchemeResponse.isEmpty()){
            return Optional.empty();
        }
        return mgnregaSchemeResponse;
    }

    public AddChowkiResponse insertTrackCocoonData(TrackCocoonRequest trackCocoonRequest) {
        AddChowkiResponse addChowkiResponse = new AddChowkiResponse();
        TrackCocoon trackCocoon = new TrackCocoon();
        try {
            // Set data for TrackCocoon
            trackCocoon.setMarketAuctionDate(trackCocoonRequest.getMarketAuctionDate());
            trackCocoon.setMarketMasterId(trackCocoonRequest.getMarketMasterId());
            trackCocoon.setCocoonsQty(trackCocoonRequest.getCocoonsQty());
            trackCocoon.setRatePerKg(trackCocoonRequest.getRatePerKg());
            trackCocoon.setBuyerType(trackCocoonRequest.getBuyerType());
            trackCocoon.setReelerId(trackCocoonRequest.getReelerId());
            trackCocoon.setChowkiId(trackCocoonRequest.getChowkiId());
            trackCocoon.setExternalUnitRegistrationName(trackCocoonRequest.getExternalUnitRegistrationName());

            // Save TrackCocoon data
            trackCocconRepository.save(trackCocoon);

            // Create ChowkiManagement object and set isSaleTracked as 1
            ChowkiManagement chowkiManagement = new ChowkiManagement();
            chowkiManagement.setChowkiId(trackCocoonRequest.getChowkiId());
            chowkiManagement.setIsSaleTracked(1); // Set isSaleTracked to 1

            // Save ChowkiManagement data
            chowkiManagementRepository.save(chowkiManagement);

            // Set success response
            addChowkiResponse.setError(0);
            addChowkiResponse.setMessage("Data added successfully!");
        } catch (Exception e) {
            // Handle error scenario
            addChowkiResponse.setError(1);
            addChowkiResponse.setMessage("Selected district is invalid or something else went wrong; please try again!");
            log.error("EXCEPTION : {}", e);
        }

        return addChowkiResponse;
    }


    public List<SupplyOfDisinfectantsListResponse> getByUserMasterIdOrderBySupplyOfDisinfectantsIdDesc(Long userId) {
        List<Object[]> chowkiDetails = supplyOfDisinfectantsRepository.getByUserMasterIdOrderBySupplyOfDisinfectantsIdDesc(userId);
        List<SupplyOfDisinfectantsListResponse> responses = new ArrayList<>();

        for (Object[] arr : chowkiDetails) {
            SupplyOfDisinfectantsListResponse response = SupplyOfDisinfectantsListResponse.builder()
                    .supplyOfDisinfectantsId(Util.objectToLong(arr[0]))
                    .farmerId(Util.objectToLong(arr[1]))
                    .userMasterId(Util.objectToLong(arr[2]))
                    .disinfectantMasterId(Util.objectToLong(arr[3]))
                    .invoiceNoDate(Util.objectToString(arr[4]))
                    .quantity(Util.objectToLong(arr[5]))
                    .disinfectantName(Util.objectToString(arr[6]))
                    .quantitySupplied(Util.objectToLong(arr[7]))
                    .supplyDate(Util.objectToString(arr[8]))
                    .sizeOfRearingHouse(Util.objectToString(arr[9]))
                    .numbersOfDfls(Util.objectToLong(arr[10]))
                    .firstName(Util.objectToString(arr[11]))
                    .disinfectantMasterName(Util.objectToString(arr[12]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    @Transactional
    public CropInspectionResponse uploadCropInspection(MultipartFile multipartFile, String cropInspectionId) throws Exception {
        CropInspectionResponse cropInspectionResponse = new CropInspectionResponse();
        CropInspection cropInspection = cropInspectionRepository.findByCropInspectionIdAndActive(Long.parseLong(cropInspectionId), true);
        if (Objects.nonNull(cropInspection)) {
            UUID uuid = UUID.randomUUID();
            String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            String fileName = "cropInspection/" + cropInspectionId + "_" + uuid + "_" + extension;
            s3Controller.uploadFile(multipartFile, fileName);
            cropInspection.setCropInspectionPath(fileName);
            cropInspection.setActive(true);
            CropInspection cropInspection1 = cropInspectionRepository.save(cropInspection);
            cropInspectionResponse = mapper.cropInspectionEntityToObject(cropInspection1, CropInspectionResponse.class);
            cropInspectionResponse.setError(false);
        } else {
            cropInspectionResponse.setError(true);
            cropInspectionResponse.setError_description("Error occurred while fetching Mulberry Extension");
            // throw new ValidationException("Error occurred while fetching village");
        }
        return cropInspectionResponse;
    }

    @Transactional
    public CropInspectionResponse uploadMulberryExtension(MultipartFile multipartFile, String farmerMulberryExtensionId) throws Exception {
        CropInspectionResponse cropInspectionResponse = new CropInspectionResponse();
        FarmerMulberryExtension farmerMulberryExtension = farmerMulberryExtensionRepository.findByFarmerMulberryExtensionIdAndActive(Long.parseLong(farmerMulberryExtensionId), true);
        if (Objects.nonNull(farmerMulberryExtension)) {
            UUID uuid = UUID.randomUUID();
            String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            String fileName = "farmerMulberryExtension/" + farmerMulberryExtensionId + "_" + uuid + "_" + extension;
            s3Controller.uploadFile(multipartFile, fileName);
            farmerMulberryExtension.setPhotoPath(fileName);
            farmerMulberryExtension.setActive(true);
            FarmerMulberryExtension farmerMulberryExtension1 = farmerMulberryExtensionRepository.save(farmerMulberryExtension);
            cropInspectionResponse = mapper.mulberryExtensionEntityToObject(farmerMulberryExtension1, CropInspectionResponse.class);
            cropInspectionResponse.setError(false);
        } else {
            cropInspectionResponse.setError(true);
            cropInspectionResponse.setError_description("Error occurred while fetching Mulberry Extension");
            // throw new ValidationException("Error occurred while fetching village");
        }
        return cropInspectionResponse;
    }


    @Transactional
    public CropInspectionResponse uploadFitnessCertificate(MultipartFile multipartFile, String fitnessCertificateId) throws Exception {
        CropInspectionResponse cropInspectionResponse = new CropInspectionResponse();
        FitnessCertificate fitnessCertificate = fitnessCertificateRepository.findByFitnessCertificateIdAndActive(Long.parseLong(fitnessCertificateId), true);
        if (Objects.nonNull(fitnessCertificate)) {
            UUID uuid = UUID.randomUUID();
            String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            String fileName = "fitnessCertificate/" + fitnessCertificateId + "_" + uuid + "_" + extension;
            s3Controller.uploadFile(multipartFile, fileName);
            fitnessCertificate.setFitnessCertificatePath(fileName);
            fitnessCertificate.setActive(true);
            FitnessCertificate fitnessCertificate1 = fitnessCertificateRepository.save(fitnessCertificate);
            cropInspectionResponse = mapper.fitnessCertificateEntityToObject(fitnessCertificate1, CropInspectionResponse.class);
            cropInspectionResponse.setError(false);
        } else {
            cropInspectionResponse.setError(true);
            cropInspectionResponse.setError_description("Error occurred while fetching Fitness Certificate");
            // throw new ValidationException("Error occurred while fetching village");
        }
        return cropInspectionResponse;
    }




}
