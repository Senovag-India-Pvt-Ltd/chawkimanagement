package com.sericulture.service;

import com.sericulture.controller.S3Controller;
import com.sericulture.helper.Util;
import com.sericulture.model.Mapper;
import com.sericulture.model.ResponseWrapper;
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
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    SaleAndDisposalOfDflsRepository saleAndDisposalOfDflsRepository;

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
            Optional<CropInspection> existingInspection = cropInspectionRepository
                    .findByFarmerIdAndSaleAndDisposalIdAndActive(
                            cropInspectionRequest.getFarmerId(),
                            cropInspectionRequest.getSaleAndDisposalId()
                    );

            if (existingInspection.isPresent()) {
                addChowkiResponse.setError(1);
                addChowkiResponse.setMessage("Data already exists for the given Farmer and Sale/Disposal ID.");
                return addChowkiResponse;
            }


            cropInspection.setSaleAndDisposalId(cropInspectionRequest.getSaleAndDisposalId());
            cropInspection.setFruitsId(cropInspectionRequest.getFruitsId());
            cropInspection.setChowkiId(cropInspectionRequest.getChowkiId());
            cropInspection.setFarmerId(cropInspectionRequest.getFarmerId());
            cropInspection.setCropInspectionTypeId(cropInspectionRequest.getCropInspectionTypeId());
            cropInspection.setDate(cropInspectionRequest.getDate());
            cropInspection.setReasonId(cropInspectionRequest.getReasonId());
            cropInspection.setNote(cropInspectionRequest.getNote());
            cropInspection.setCropStatusId(cropInspectionRequest.getCropStatusId());
            cropInspection.setMountId(cropInspectionRequest.getMountId());
            cropInspection.setCropInspectionPath(cropInspectionRequest.getCropInspectionPath());
            cropInspection.setIsCropInspected(1);
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

    public List<CropInspectionResponse> getInspectionDetailsForSaleAndDisposalDFL(Long saleAndDisposalId) {
        List<Object[]> cropInspectionDetails = cropInspectionRepository.getInspectionDetailsForSaleAndDisposalDFL(saleAndDisposalId);
        List<CropInspectionResponse> responses = new ArrayList<>();

        for (Object[] arr : cropInspectionDetails) {
            CropInspectionResponse response = CropInspectionResponse.builder()
                    .cropDate(Util.objectToString(arr[0]))
                    .note(Util.objectToString(arr[1]))
                    .cropStatusName(Util.objectToString(arr[2]))
                    .mountName(Util.objectToString(arr[3]))
                    .reasonName(Util.objectToString(arr[4]))
                    .saleAndDisposalId(Util.objectToLong(arr[5]))
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

            Optional<FitnessCertificate> existingInspection = fitnessCertificateRepository
                    .findByFarmerIdAndSaleAndDisposalIdAndActive(
                            cropInspectionRequest.getFarmerId(),
                            cropInspectionRequest.getSaleAndDisposalId()
                    );

            if (existingInspection.isPresent()) {
                addChowkiResponse.setError(1);
                addChowkiResponse.setMessage("Data already exists for the given Farmer and Sale/Disposal ID.");
                return addChowkiResponse;
            }


            fitnessCertificate.setSaleAndDisposalId(cropInspectionRequest.getSaleAndDisposalId());
            fitnessCertificate.setFruitsId(cropInspectionRequest.getFruitsId());
            fitnessCertificate.setChowkiId(cropInspectionRequest.getChowkiId());
            fitnessCertificate.setFarmerId(cropInspectionRequest.getFarmerId());
            fitnessCertificate.setExpectedCocoon(cropInspectionRequest.getExpectedCocoon());
            fitnessCertificate.setLotTestDetails(cropInspectionRequest.getLotTestDetails());
            fitnessCertificate.setDiseaseStatusId(cropInspectionRequest.getDiseaseStatusId());
            fitnessCertificate.setSpunFromDate(cropInspectionRequest.getSpunFromDate());
            fitnessCertificate.setSpunToDate(cropInspectionRequest.getSpunToDate());
            fitnessCertificate.setNoOfChandies(cropInspectionRequest.getNoOfChandies());
            fitnessCertificate.setFitnessCertificatePath(cropInspectionRequest.getFitnessCertificatePath());
            fitnessCertificate.setTransactionDate(cropInspectionRequest.getTransactionDate());
            fitnessCertificate.setIsFcIssued(1);
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

    public List<CropInspectionResponse> getInspectionTypeForCropFromSaleAndDisposalOfDfl(Long saleAndDisposalId) {
        List<Object[]> cropInspectionDetails = cropInspectionRepository.getInspectionTypeForCropFromSaleAndDisposalOfDfl(saleAndDisposalId);
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

    public List<CropInspectionResponse> getFitnessCertificatePath(Long farmerId) {
        List<Object[]> chowkiDetails = fitnessCertificateRepository.getFitnessCertificatePath(farmerId);
        List<CropInspectionResponse> responses = new ArrayList<>();

        for (Object[] arr : chowkiDetails) {
            CropInspectionResponse response = CropInspectionResponse.builder()
                    .fitnessCertificateId(Util.objectToLong(arr[0]))
                    .fitnessCertificatePath(Util.objectToString(arr[1]))
                    .farmerId(Util.objectToLong(arr[2]))
                    .spunFromDate(Util.objectToString(arr[3]))
                    .spunToDate(Util.objectToString(arr[4]))
                    .noOfChandies(Util.objectToFloat(arr[5]))
                    .expectedCocoon(Util.objectToFloat(arr[6]))
                    .dflsSource(Util.objectToString(arr[7]))
                    .numbersOfDfls(Util.objectToString(arr[8]))
                    .lotNumberRsp(Util.objectToString(arr[9]))
                    .raceOfDfls(Util.objectToLong(arr[10]))
                    .dateOfBrushing(arr[11] != null ? arr[11].toString() : null)
                    .raceName(Util.objectToString(arr[12]))
                    .grainageName(Util.objectToString(arr[13]))
                    .fruitsId(Util.objectToString(arr[14]))
                    .saleAndDisposalId(Util.objectToLong(arr[15]))
                    .lotTestDetails(Util.objectToString(arr[16]))
                    .diseaseStatusId(Util.objectToLong(arr[17]))
                    .isDisposed(arr[18] != null ? ((Number) arr[18]).intValue() : null)
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public List<CropInspectionResponse> getCropInspectionPath(Long farmerId) {
        List<Object[]> details = cropInspectionRepository.getCropInspectionPath(farmerId);
        List<CropInspectionResponse> responses = new ArrayList<>();

        for (Object[] arr : details) {
            CropInspectionResponse response = CropInspectionResponse.builder()
                    .cropInspectionId(Util.objectToLong(arr[0]))
                    .cropInspectionPath(Util.objectToString(arr[1]))
                    .farmerId(Util.objectToLong(arr[2]))
                    .cropDate(arr[3] != null ? arr[3].toString() : null)
                    .note(Util.objectToString(arr[4]))
                    .cropStatusName(Util.objectToString(arr[5]))
                    .mountName(Util.objectToString(arr[6]))
                    .reasonName(Util.objectToString(arr[7]))
                    .dflsSource(Util.objectToString(arr[8]))
                    .numbersOfDfls(Util.objectToString(arr[9]))
                    .lotNumberRsp(Util.objectToString(arr[10]))
                    .raceOfDfls(Util.objectToLong(arr[11]))
                    .dateOfBrushing(arr[12] != null ? arr[12].toString() : null)
                    .raceName(Util.objectToString(arr[13]))
                    .grainageName(Util.objectToString(arr[14]))
                    .fruitsId(Util.objectToString(arr[15]))
                    .saleAndDisposalId(Util.objectToLong(arr[16]))
                    .cropInspectionTypeId(Util.objectToLong(arr[17]))
                    .isDisposed(arr[18] != null ? ((Number) arr[18]).intValue() : null)
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public AddChowkiResponse insertSaleTrackCocoonData(TrackCocoonRequest trackCocoonRequest) {
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
//        trackCocoon.setChowkiId(trackCocoonRequest.getChowkiId());
            trackCocoon.setSaleAndDisposalId(trackCocoonRequest.getSaleAndDisposalId());
            trackCocoon.setExternalUnitRegistrationName(trackCocoonRequest.getExternalUnitRegistrationName());

            // Save TrackCocoon data
            trackCocconRepository.save(trackCocoon);

            // Fetch existing SaleAndDisposalOfDfls by ID
            Optional<SaleAndDisposalOfDfls> existingRecordOptional = saleAndDisposalOfDflsRepository.findByIdAndActive(trackCocoonRequest.getSaleAndDisposalId(), true);

            if (existingRecordOptional.isPresent()) {
                SaleAndDisposalOfDfls existingRecord = existingRecordOptional.get();

                // Update the necessary field
                existingRecord.setIsSaleTracked(1); // Set isSaleTracked to 1

                // Save the updated SaleAndDisposalOfDfls object
                saleAndDisposalOfDflsRepository.save(existingRecord);
            } else {
                // Handle case where record does not exist (if needed)
                addChowkiResponse.setError(1);
                addChowkiResponse.setMessage("SaleAndDisposalOfDfls record not found!");
                return addChowkiResponse;
            }

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


        public ResponseEntity<?> getCropInspectionDetails(Long tscId, int pageNumber, int pageSize) {
            ResponseWrapper rw = ResponseWrapper.createWrapper(List.class);
            List<CropInspectionResponse> responseList = new ArrayList<>();

            tscId = (tscId != null && tscId == 0) ? null : tscId;

            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<Object[]> page = cropInspectionRepository.getCropInspectionDetails(tscId, pageable);

            buildResponse(responseList, page.getContent(), pageNumber, pageSize);

            rw.setTotalRecords(page.getTotalElements());
            rw.setContent(responseList);
            return ResponseEntity.ok(rw);
        }

    public FileInputStream getCropInspectionReport(Long tscId, int pageNumber, int pageSize) throws Exception {
        List<CropInspectionResponse> responseList = new ArrayList<>();

        tscId = (tscId != null && tscId == 0) ? null : tscId;

        Pageable pageable = null; // fetch all records
        Page<Object[]> page = cropInspectionRepository.getCropInspectionDetails(tscId, pageable);

        buildResponse(responseList, page.getContent(), pageNumber, pageSize);

        String[] headerLabels = {
            "Sl.No", "Farmer Name", "Father Name", "Fruits ID", "Date", "Note",
            "Crop Status", "Mount Name", "Reason Name", "Sale & Disposal ID", "TSC Name"
        };
        final int TOTAL_COLS = headerLabels.length;

        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        workbook.setCompressTempFiles(true);
        Sheet sheet = workbook.createSheet("Crop Inspection Report");

        // ── Colors ───────────────────────────────────────────────────────────
        XSSFColor primaryBlue = new XSSFColor(new byte[]{(byte)26,  (byte)95,  (byte)158}, null);
        XSSFColor darkNavy    = new XSSFColor(new byte[]{(byte)12,  (byte)74,  (byte)158}, null);
        XSSFColor altRow      = new XSSFColor(new byte[]{(byte)247, (byte)250, (byte)253}, null);
        XSSFColor white       = new XSSFColor(new byte[]{(byte)255, (byte)255, (byte)255}, null);
        XSSFColor darkText    = new XSSFColor(new byte[]{(byte)30,  (byte)58,  (byte)95},  null);
        XSSFColor black       = new XSSFColor(new byte[]{(byte)0,   (byte)0,   (byte)0},   null);

        // ── Fonts ────────────────────────────────────────────────────────────
        XSSFFont titleFont = (XSSFFont) workbook.createFont();
        titleFont.setFontName("Calibri"); titleFont.setFontHeightInPoints((short)16);
        titleFont.setBold(true); titleFont.setColor(white);

        XSSFFont subFont = (XSSFFont) workbook.createFont();
        subFont.setFontName("Calibri"); subFont.setFontHeightInPoints((short)11);
        subFont.setColor(white);

        XSSFFont hdrFont = (XSSFFont) workbook.createFont();
        hdrFont.setFontName("Calibri"); hdrFont.setFontHeightInPoints((short)11);
        hdrFont.setBold(true); hdrFont.setColor(white);

        XSSFFont dataFont = (XSSFFont) workbook.createFont();
        dataFont.setFontName("Calibri"); dataFont.setFontHeightInPoints((short)10);
        dataFont.setColor(darkText);

        // ── Styles ───────────────────────────────────────────────────────────
        XSSFCellStyle titleStyle = (XSSFCellStyle) workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setFillForegroundColor(darkNavy);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setBorderTop(BorderStyle.THIN); titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN); titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setTopBorderColor(black); titleStyle.setBottomBorderColor(black);
        titleStyle.setLeftBorderColor(black); titleStyle.setRightBorderColor(black);

        XSSFCellStyle subStyle = (XSSFCellStyle) workbook.createCellStyle();
        subStyle.setFont(subFont);
        subStyle.setFillForegroundColor(primaryBlue);
        subStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        subStyle.setAlignment(HorizontalAlignment.CENTER);
        subStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        subStyle.setBorderTop(BorderStyle.THIN); subStyle.setBorderBottom(BorderStyle.THIN);
        subStyle.setBorderLeft(BorderStyle.THIN); subStyle.setBorderRight(BorderStyle.THIN);
        subStyle.setTopBorderColor(black); subStyle.setBottomBorderColor(black);
        subStyle.setLeftBorderColor(black); subStyle.setRightBorderColor(black);

        XSSFCellStyle hdrStyle = (XSSFCellStyle) workbook.createCellStyle();
        hdrStyle.setFont(hdrFont);
        hdrStyle.setFillForegroundColor(primaryBlue);
        hdrStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        hdrStyle.setAlignment(HorizontalAlignment.CENTER);
        hdrStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        hdrStyle.setWrapText(true);
        hdrStyle.setBorderTop(BorderStyle.THIN); hdrStyle.setBorderBottom(BorderStyle.THIN);
        hdrStyle.setBorderLeft(BorderStyle.THIN); hdrStyle.setBorderRight(BorderStyle.THIN);
        hdrStyle.setTopBorderColor(black); hdrStyle.setBottomBorderColor(black);
        hdrStyle.setLeftBorderColor(black); hdrStyle.setRightBorderColor(black);

        XSSFCellStyle dataWhite = (XSSFCellStyle) workbook.createCellStyle();
        dataWhite.setFont(dataFont);
        dataWhite.setAlignment(HorizontalAlignment.CENTER);
        dataWhite.setVerticalAlignment(VerticalAlignment.CENTER);
        dataWhite.setWrapText(true);
        dataWhite.setBorderTop(BorderStyle.THIN); dataWhite.setBorderBottom(BorderStyle.THIN);
        dataWhite.setBorderLeft(BorderStyle.THIN); dataWhite.setBorderRight(BorderStyle.THIN);
        dataWhite.setTopBorderColor(black); dataWhite.setBottomBorderColor(black);
        dataWhite.setLeftBorderColor(black); dataWhite.setRightBorderColor(black);

        XSSFCellStyle dataAlt = (XSSFCellStyle) workbook.createCellStyle();
        dataAlt.cloneStyleFrom(dataWhite);
        dataAlt.setFillForegroundColor(altRow);
        dataAlt.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // ── Row 0: Department title ───────────────────────────────────────────
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(36);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Department of Sericulture, Government of Karnataka");
        titleCell.setCellStyle(titleStyle);
        for (int c = 1; c < TOTAL_COLS; c++) { titleRow.createCell(c).setCellStyle(titleStyle); }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, TOTAL_COLS - 1));

        // ── Row 1: Report name ────────────────────────────────────────────────
        Row reportRow = sheet.createRow(1);
        reportRow.setHeightInPoints(24);
        Cell reportCell = reportRow.createCell(0);
        reportCell.setCellValue("CROP INSPECTION REPORT");
        reportCell.setCellStyle(subStyle);
        for (int c = 1; c < TOTAL_COLS; c++) { reportRow.createCell(c).setCellStyle(subStyle); }
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, TOTAL_COLS - 1));

        // ── Row 2: Generated on ───────────────────────────────────────────────
        Row genRow = sheet.createRow(2);
        genRow.setHeightInPoints(20);
        Cell genCell = genRow.createCell(0);
        genCell.setCellValue("Generated On: " + new java.text.SimpleDateFormat("dd-MMM-yyyy HH:mm").format(new java.util.Date()));
        genCell.setCellStyle(subStyle);
        for (int c = 1; c < TOTAL_COLS; c++) { genRow.createCell(c).setCellStyle(subStyle); }
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, TOTAL_COLS - 1));

        // ── Row 3: Column headers ─────────────────────────────────────────────
        Row headerRow = sheet.createRow(3);
        headerRow.setHeightInPoints(36);
        for (int i = 0; i < TOTAL_COLS; i++) {
            Cell hCell = headerRow.createCell(i);
            hCell.setCellValue(headerLabels[i]);
            hCell.setCellStyle(hdrStyle);
        }

        // ── Rows 4+: Data rows ────────────────────────────────────────────────
        int dataStartsFrom = 4;
        for (int i = 0; i < responseList.size(); i++) {
            CropInspectionResponse c = responseList.get(i);
            Row row = sheet.createRow(dataStartsFrom + i);
            XSSFCellStyle rowStyle = (i % 2 != 0) ? dataAlt : dataWhite;
            String[] values = {
                String.valueOf(c.getSerialNumber()),
                c.getFarmerName(),
                c.getFatherName(),
                c.getFruitsId(),
                c.getCropInspectionDate(),
                c.getNote(),
                c.getCropStatusName(),
                c.getMountName(),
                c.getReasonName(),
                c.getSaleAndDisposalId() != null ? String.valueOf(c.getSaleAndDisposalId()) : "",
                c.getTscName()
            };
            for (int col = 0; col < TOTAL_COLS; col++) {
                Cell cell = row.createCell(col);
                cell.setCellValue(values[col] != null ? values[col] : "");
                cell.setCellStyle(rowStyle);
            }
        }

        sheet.createFreezePane(0, 4);
        for (int col = 0; col < TOTAL_COLS; col++) {
            sheet.setColumnWidth(col, 20 * 256);
        }

        String userHome = System.getProperty("user.home");
        String directoryPath = Paths.get(userHome, "Downloads").toString();
        Files.createDirectories(Paths.get(directoryPath));
        Path filePath = Paths.get(directoryPath, "crop_inspection_report" + Util.getISTLocalDate() + ".xlsx");

        FileOutputStream fileOut = new FileOutputStream(filePath.toString());
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        FileInputStream fileIn = new FileInputStream(filePath.toString());
        return fileIn;
    }


    private static void buildResponse(List<CropInspectionResponse> responseList, List<Object[]> list, int pageNumber, int pageSize) {
            int serialNumber = pageNumber * pageSize + 1;
            for (Object[] arr : list) {
                CropInspectionResponse response = CropInspectionResponse.builder()
                        .serialNumber(serialNumber++)
                        .cropInspectionDate(Util.objectToString(arr[0]))
                        .note(Util.objectToString(arr[1]))
                        .cropStatusName(Util.objectToString(arr[2]))
                        .mountName(Util.objectToString(arr[3]))
                        .reasonName(Util.objectToString(arr[4]))
                        .saleAndDisposalId(Util.objectToLong(arr[5]))
                        .tscName(Util.objectToString(arr[6]))
                        .farmerName(Util.objectToString(arr[7]))
                        .fatherName(Util.objectToString(arr[8]))
                        .fruitsId(Util.objectToString(arr[9]))
                        .cropInspectionId(Util.objectToLong(arr[10]))
                        .build();
                responseList.add(response);
            }
        }

    public ResponseEntity<?> getFitnessCertificateDetails(Long tscId, int pageNumber, int pageSize) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(List.class);
        List<FitnessCertificateResponse> responseList = new ArrayList<>();

        tscId = (tscId != null && tscId == 0) ? null : tscId;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> page = fitnessCertificateRepository.getFitnessCertificateDetails(tscId, pageable);

        buildResponseForFitness(responseList, page.getContent(), pageNumber, pageSize);

        rw.setTotalRecords(page.getTotalElements());
        rw.setContent(responseList);
        return ResponseEntity.ok(rw);
    }

    public FileInputStream getFitnessCertificateReport(Long tscId, int pageNumber, int pageSize) throws Exception {
        List<FitnessCertificateResponse> responseList = new ArrayList<>();

        tscId = (tscId != null && tscId == 0) ? null : tscId;

        Pageable pageable = null; // fetch all records for Excel
        Page<Object[]> page = fitnessCertificateRepository.getFitnessCertificateDetails(tscId, pageable);

        buildResponseForFitness(responseList, page.getContent(), pageNumber, pageSize);

        String[] headerLabels = {
            "Sl.No", "Farmer Name", "Father Name", "Fruits ID", "Fitness Certificate ID",
            "Fitness Certificate Path", "Farmer ID", "Rate per 100 DFLs Price",
            "Number of DFLs Disposed", "Lot Number", "Race Name", "TSC Name"
        };
        final int TOTAL_COLS = headerLabels.length;

        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        workbook.setCompressTempFiles(true);
        Sheet sheet = workbook.createSheet("Fitness Certificate Report");

        // ── Colors ───────────────────────────────────────────────────────────
        XSSFColor primaryBlue = new XSSFColor(new byte[]{(byte)26,  (byte)95,  (byte)158}, null);
        XSSFColor darkNavy    = new XSSFColor(new byte[]{(byte)12,  (byte)74,  (byte)158}, null);
        XSSFColor altRow      = new XSSFColor(new byte[]{(byte)247, (byte)250, (byte)253}, null);
        XSSFColor white       = new XSSFColor(new byte[]{(byte)255, (byte)255, (byte)255}, null);
        XSSFColor darkText    = new XSSFColor(new byte[]{(byte)30,  (byte)58,  (byte)95},  null);
        XSSFColor black       = new XSSFColor(new byte[]{(byte)0,   (byte)0,   (byte)0},   null);

        // ── Fonts ────────────────────────────────────────────────────────────
        XSSFFont titleFont = (XSSFFont) workbook.createFont();
        titleFont.setFontName("Calibri"); titleFont.setFontHeightInPoints((short)16);
        titleFont.setBold(true); titleFont.setColor(white);

        XSSFFont subFont = (XSSFFont) workbook.createFont();
        subFont.setFontName("Calibri"); subFont.setFontHeightInPoints((short)11);
        subFont.setColor(white);

        XSSFFont hdrFont = (XSSFFont) workbook.createFont();
        hdrFont.setFontName("Calibri"); hdrFont.setFontHeightInPoints((short)11);
        hdrFont.setBold(true); hdrFont.setColor(white);

        XSSFFont dataFont = (XSSFFont) workbook.createFont();
        dataFont.setFontName("Calibri"); dataFont.setFontHeightInPoints((short)10);
        dataFont.setColor(darkText);

        // ── Styles ───────────────────────────────────────────────────────────
        XSSFCellStyle titleStyle = (XSSFCellStyle) workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setFillForegroundColor(darkNavy);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setBorderTop(BorderStyle.THIN); titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN); titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setTopBorderColor(black); titleStyle.setBottomBorderColor(black);
        titleStyle.setLeftBorderColor(black); titleStyle.setRightBorderColor(black);

        XSSFCellStyle subStyle = (XSSFCellStyle) workbook.createCellStyle();
        subStyle.setFont(subFont);
        subStyle.setFillForegroundColor(primaryBlue);
        subStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        subStyle.setAlignment(HorizontalAlignment.CENTER);
        subStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        subStyle.setBorderTop(BorderStyle.THIN); subStyle.setBorderBottom(BorderStyle.THIN);
        subStyle.setBorderLeft(BorderStyle.THIN); subStyle.setBorderRight(BorderStyle.THIN);
        subStyle.setTopBorderColor(black); subStyle.setBottomBorderColor(black);
        subStyle.setLeftBorderColor(black); subStyle.setRightBorderColor(black);

        XSSFCellStyle hdrStyle = (XSSFCellStyle) workbook.createCellStyle();
        hdrStyle.setFont(hdrFont);
        hdrStyle.setFillForegroundColor(primaryBlue);
        hdrStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        hdrStyle.setAlignment(HorizontalAlignment.CENTER);
        hdrStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        hdrStyle.setWrapText(true);
        hdrStyle.setBorderTop(BorderStyle.THIN); hdrStyle.setBorderBottom(BorderStyle.THIN);
        hdrStyle.setBorderLeft(BorderStyle.THIN); hdrStyle.setBorderRight(BorderStyle.THIN);
        hdrStyle.setTopBorderColor(black); hdrStyle.setBottomBorderColor(black);
        hdrStyle.setLeftBorderColor(black); hdrStyle.setRightBorderColor(black);

        XSSFCellStyle dataWhite = (XSSFCellStyle) workbook.createCellStyle();
        dataWhite.setFont(dataFont);
        dataWhite.setAlignment(HorizontalAlignment.CENTER);
        dataWhite.setVerticalAlignment(VerticalAlignment.CENTER);
        dataWhite.setWrapText(true);
        dataWhite.setBorderTop(BorderStyle.THIN); dataWhite.setBorderBottom(BorderStyle.THIN);
        dataWhite.setBorderLeft(BorderStyle.THIN); dataWhite.setBorderRight(BorderStyle.THIN);
        dataWhite.setTopBorderColor(black); dataWhite.setBottomBorderColor(black);
        dataWhite.setLeftBorderColor(black); dataWhite.setRightBorderColor(black);

        XSSFCellStyle dataAlt = (XSSFCellStyle) workbook.createCellStyle();
        dataAlt.cloneStyleFrom(dataWhite);
        dataAlt.setFillForegroundColor(altRow);
        dataAlt.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // ── Row 0: Department title ───────────────────────────────────────────
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(36);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Department of Sericulture, Government of Karnataka");
        titleCell.setCellStyle(titleStyle);
        for (int c = 1; c < TOTAL_COLS; c++) { titleRow.createCell(c).setCellStyle(titleStyle); }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, TOTAL_COLS - 1));

        // ── Row 1: Report name ────────────────────────────────────────────────
        Row reportRow = sheet.createRow(1);
        reportRow.setHeightInPoints(24);
        Cell reportCell = reportRow.createCell(0);
        reportCell.setCellValue("FITNESS CERTIFICATE REPORT");
        reportCell.setCellStyle(subStyle);
        for (int c = 1; c < TOTAL_COLS; c++) { reportRow.createCell(c).setCellStyle(subStyle); }
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, TOTAL_COLS - 1));

        // ── Row 2: Generated on ───────────────────────────────────────────────
        Row genRow = sheet.createRow(2);
        genRow.setHeightInPoints(20);
        Cell genCell = genRow.createCell(0);
        genCell.setCellValue("Generated On: " + new java.text.SimpleDateFormat("dd-MMM-yyyy HH:mm").format(new java.util.Date()));
        genCell.setCellStyle(subStyle);
        for (int c = 1; c < TOTAL_COLS; c++) { genRow.createCell(c).setCellStyle(subStyle); }
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, TOTAL_COLS - 1));

        // ── Row 3: Column headers ─────────────────────────────────────────────
        Row headerRow = sheet.createRow(3);
        headerRow.setHeightInPoints(36);
        for (int i = 0; i < TOTAL_COLS; i++) {
            Cell hCell = headerRow.createCell(i);
            hCell.setCellValue(headerLabels[i]);
            hCell.setCellStyle(hdrStyle);
        }

        // ── Rows 4+: Data rows ────────────────────────────────────────────────
        int dataStartsFrom = 4;
        for (int i = 0; i < responseList.size(); i++) {
            FitnessCertificateResponse f = responseList.get(i);
            Row row = sheet.createRow(dataStartsFrom + i);
            XSSFCellStyle rowStyle = (i % 2 != 0) ? dataAlt : dataWhite;
            String[] values = {
                String.valueOf(f.getSerialNumber()),
                f.getFarmerName(),
                f.getFatherName(),
                f.getFruitsId(),
                f.getFitnessCertificateId() != null ? String.valueOf(f.getFitnessCertificateId()) : "",
                f.getFitnessCertificatePath(),
                f.getFarmerId() != null ? String.valueOf(f.getFarmerId()) : "",
                f.getRatePer100Dfls() != null ? String.valueOf(f.getRatePer100Dfls()) : "",
                f.getNumberOfDflsDisposed() != null ? String.valueOf(f.getNumberOfDflsDisposed()) : "",
                f.getLotNumber(),
                f.getRaceName(),
                f.getTscName()
            };
            for (int col = 0; col < TOTAL_COLS; col++) {
                Cell cell = row.createCell(col);
                cell.setCellValue(values[col] != null ? values[col] : "");
                cell.setCellStyle(rowStyle);
            }
        }

        sheet.createFreezePane(0, 4);
        for (int col = 0; col < TOTAL_COLS; col++) {
            sheet.setColumnWidth(col, 20 * 256);
        }

        String userHome = System.getProperty("user.home");
        String directoryPath = Paths.get(userHome, "Downloads").toString();
        Files.createDirectories(Paths.get(directoryPath));
        Path filePath = Paths.get(directoryPath, "fitness_certificate_report" + Util.getISTLocalDate() + ".xlsx");

        FileOutputStream fileOut = new FileOutputStream(filePath.toString());
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        FileInputStream fileIn = new FileInputStream(filePath.toString());
        return fileIn;
    }

    private static void buildResponseForFitness(List<FitnessCertificateResponse> responseList, List<Object[]> list, int pageNumber, int pageSize) {
        int serialNumber = pageNumber * pageSize + 1;
        for (Object[] arr : list) {
            FitnessCertificateResponse response = FitnessCertificateResponse.builder()
                    .serialNumber(serialNumber++)
                    .farmerName(Util.objectToString(arr[0]))
                    .fatherName(Util.objectToString(arr[1]))
                    .fruitsId(Util.objectToString(arr[2]))
                    .fitnessCertificateId(Util.objectToLong(arr[3]))
                    .fitnessCertificatePath(Util.objectToString(arr[4]))
                    .farmerId(Util.objectToLong(arr[5]))
                    .ratePer100Dfls(Util.objectToFloat(arr[6]))
                    .numberOfDflsDisposed(Util.objectToInteger(arr[7]))
                    .lotNumber(Util.objectToString(arr[8]))
                    .raceName(Util.objectToString(arr[9]))
                    .tscName(Util.objectToString(arr[10]))
                    .build();
            responseList.add(response);
        }
    }

    public ResponseEntity<?> getFarmerMulberryExtensionDetails(Long tscId,Long districtId,Long talukId, String applicationType,
                                                               int pageNumber, int pageSize) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(List.class);
        List<FarmerMulberryExtensionResponse> responseList = new ArrayList<>();

        tscId = (tscId != null && tscId == 0) ? null : tscId;
        districtId = (districtId != null && districtId == 0) ? null : districtId;
        talukId = (talukId != null && talukId == 0) ? null : talukId;
        applicationType = (applicationType != null && applicationType.isEmpty()) ? null : applicationType;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> applicablePage =
                farmerMulberryExtensionRepository.getFarmerMulberryExtensionDetails(tscId, districtId,talukId,applicationType, pageable);

        List<Object[]> applicableList = applicablePage.getContent();
        long totalRecords = applicablePage.getTotalElements();

        buildResponseForFarmerMulberryExtension(responseList, applicableList, pageNumber, pageSize);

        rw.setTotalRecords(totalRecords);
        rw.setContent(responseList);
        return ResponseEntity.ok(rw);
    }

    private static void buildResponseForFarmerMulberryExtension(List<FarmerMulberryExtensionResponse> responseList,
                                      List<Object[]> applicableList,
                                      int pageNumber, int pageSize) {
        int serialNumber = pageNumber * pageSize + 1;
        for (Object[] arr : applicableList) {
            FarmerMulberryExtensionResponse response = FarmerMulberryExtensionResponse.builder()
                    .serialNumber(serialNumber++)
                    .firstName(Util.objectToString(arr[0]))
                    .fatherName(Util.objectToString(arr[1]))
                    .fruitsId(Util.objectToString(arr[2]))
                    .scheme(Util.objectToString(arr[3]))
                    .addressText(Util.objectToString(arr[4]))
                    .tscName(Util.objectToString(arr[5]))
                    .tscNameKannada(Util.objectToString(arr[6]))
                    .mulberryVarietyName(Util.objectToString(arr[7]))
                    .mulberryVarietyNameKannada(Util.objectToString(arr[8]))
                    .plantationDate(Util.objectToString(arr[9]))
                    .numberOfSaplings(Util.objectToString(arr[10]))
                    .mulberryArea(Util.objectToString(arr[11]))
                    .spacing(Util.objectToString(arr[12]))
                    .applicationType(Util.objectToString(arr[13]))
                    .uprootingReason(Util.objectToString(arr[14]))
                    .uprootingDate(Util.objectToString(arr[15]))
                    .districtName(Util.objectToString(arr[16]))
                    .talukName(Util.objectToString(arr[17]))
                    .build();
            responseList.add(response);
        }
    }

    public FileInputStream getFarmerMulberryExtensionReport(Long tscId, Long districtId,Long talukId,String applicationType,
                                                            int pageNumber, int pageSize) throws Exception {
        List<FarmerMulberryExtensionResponse> responseList = new ArrayList<>();

        tscId = (tscId != null && tscId == 0) ? null : tscId;
        districtId = (districtId != null && districtId == 0) ? null : districtId;
        talukId = (talukId != null && talukId == 0) ? null : talukId;
        applicationType = (applicationType != null && applicationType.isEmpty()) ? null : applicationType;

        Pageable pageable = null; // fetch all records
        Page<Object[]> applicablePage =
                farmerMulberryExtensionRepository.getFarmerMulberryExtensionDetails(tscId,districtId,talukId, applicationType, pageable);

        buildResponseForFarmerMulberryExtension(responseList, applicablePage.getContent(), pageNumber, pageSize);

        String[] headerLabels = {
            "Sl.No", "First Name", "Father Name", "Fruits ID", "Scheme", "Address",
            "TSC", "TSC (Kannada)", "Mulberry Variety", "Mulberry Variety (Kannada)",
            "Plantation Date", "Number of Saplings", "Mulberry Area", "Spacing",
            "Application Type", "Uprooting Reason", "Uprooting Date", "District", "Taluk"
        };
        final int TOTAL_COLS = headerLabels.length;

        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        workbook.setCompressTempFiles(true);
        Sheet sheet = workbook.createSheet("Farmer Mulberry Extension Report");

        // ── Colors ───────────────────────────────────────────────────────────
        XSSFColor primaryBlue = new XSSFColor(new byte[]{(byte)26,  (byte)95,  (byte)158}, null);
        XSSFColor darkNavy    = new XSSFColor(new byte[]{(byte)12,  (byte)74,  (byte)158}, null);
        XSSFColor altRow      = new XSSFColor(new byte[]{(byte)247, (byte)250, (byte)253}, null);
        XSSFColor white       = new XSSFColor(new byte[]{(byte)255, (byte)255, (byte)255}, null);
        XSSFColor darkText    = new XSSFColor(new byte[]{(byte)30,  (byte)58,  (byte)95},  null);
        XSSFColor black       = new XSSFColor(new byte[]{(byte)0,   (byte)0,   (byte)0},   null);

        // ── Fonts ────────────────────────────────────────────────────────────
        XSSFFont titleFont = (XSSFFont) workbook.createFont();
        titleFont.setFontName("Calibri"); titleFont.setFontHeightInPoints((short)16);
        titleFont.setBold(true); titleFont.setColor(white);

        XSSFFont subFont = (XSSFFont) workbook.createFont();
        subFont.setFontName("Calibri"); subFont.setFontHeightInPoints((short)11);
        subFont.setColor(white);

        XSSFFont hdrFont = (XSSFFont) workbook.createFont();
        hdrFont.setFontName("Calibri"); hdrFont.setFontHeightInPoints((short)11);
        hdrFont.setBold(true); hdrFont.setColor(white);

        XSSFFont dataFont = (XSSFFont) workbook.createFont();
        dataFont.setFontName("Calibri"); dataFont.setFontHeightInPoints((short)10);
        dataFont.setColor(darkText);

        // ── Styles ───────────────────────────────────────────────────────────
        XSSFCellStyle titleStyle = (XSSFCellStyle) workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setFillForegroundColor(darkNavy);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setBorderTop(BorderStyle.THIN); titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN); titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setTopBorderColor(black); titleStyle.setBottomBorderColor(black);
        titleStyle.setLeftBorderColor(black); titleStyle.setRightBorderColor(black);

        XSSFCellStyle subStyle = (XSSFCellStyle) workbook.createCellStyle();
        subStyle.setFont(subFont);
        subStyle.setFillForegroundColor(primaryBlue);
        subStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        subStyle.setAlignment(HorizontalAlignment.CENTER);
        subStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        subStyle.setBorderTop(BorderStyle.THIN); subStyle.setBorderBottom(BorderStyle.THIN);
        subStyle.setBorderLeft(BorderStyle.THIN); subStyle.setBorderRight(BorderStyle.THIN);
        subStyle.setTopBorderColor(black); subStyle.setBottomBorderColor(black);
        subStyle.setLeftBorderColor(black); subStyle.setRightBorderColor(black);

        XSSFCellStyle hdrStyle = (XSSFCellStyle) workbook.createCellStyle();
        hdrStyle.setFont(hdrFont);
        hdrStyle.setFillForegroundColor(primaryBlue);
        hdrStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        hdrStyle.setAlignment(HorizontalAlignment.CENTER);
        hdrStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        hdrStyle.setWrapText(true);
        hdrStyle.setBorderTop(BorderStyle.THIN); hdrStyle.setBorderBottom(BorderStyle.THIN);
        hdrStyle.setBorderLeft(BorderStyle.THIN); hdrStyle.setBorderRight(BorderStyle.THIN);
        hdrStyle.setTopBorderColor(black); hdrStyle.setBottomBorderColor(black);
        hdrStyle.setLeftBorderColor(black); hdrStyle.setRightBorderColor(black);

        XSSFCellStyle dataWhite = (XSSFCellStyle) workbook.createCellStyle();
        dataWhite.setFont(dataFont);
        dataWhite.setAlignment(HorizontalAlignment.CENTER);
        dataWhite.setVerticalAlignment(VerticalAlignment.CENTER);
        dataWhite.setWrapText(true);
        dataWhite.setBorderTop(BorderStyle.THIN); dataWhite.setBorderBottom(BorderStyle.THIN);
        dataWhite.setBorderLeft(BorderStyle.THIN); dataWhite.setBorderRight(BorderStyle.THIN);
        dataWhite.setTopBorderColor(black); dataWhite.setBottomBorderColor(black);
        dataWhite.setLeftBorderColor(black); dataWhite.setRightBorderColor(black);

        XSSFCellStyle dataAlt = (XSSFCellStyle) workbook.createCellStyle();
        dataAlt.cloneStyleFrom(dataWhite);
        dataAlt.setFillForegroundColor(altRow);
        dataAlt.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // ── Row 0: Department title ───────────────────────────────────────────
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(36);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Department of Sericulture, Government of Karnataka");
        titleCell.setCellStyle(titleStyle);
        for (int c = 1; c < TOTAL_COLS; c++) { titleRow.createCell(c).setCellStyle(titleStyle); }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, TOTAL_COLS - 1));

        // ── Row 1: Report name ────────────────────────────────────────────────
        Row reportRow = sheet.createRow(1);
        reportRow.setHeightInPoints(24);
        Cell reportCell = reportRow.createCell(0);
        reportCell.setCellValue("FARMER MULBERRY EXTENSION REPORT");
        reportCell.setCellStyle(subStyle);
        for (int c = 1; c < TOTAL_COLS; c++) { reportRow.createCell(c).setCellStyle(subStyle); }
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, TOTAL_COLS - 1));

        // ── Row 2: Generated on ───────────────────────────────────────────────
        Row genRow = sheet.createRow(2);
        genRow.setHeightInPoints(20);
        Cell genCell = genRow.createCell(0);
        genCell.setCellValue("Generated On: " + new java.text.SimpleDateFormat("dd-MMM-yyyy HH:mm").format(new java.util.Date()));
        genCell.setCellStyle(subStyle);
        for (int c = 1; c < TOTAL_COLS; c++) { genRow.createCell(c).setCellStyle(subStyle); }
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, TOTAL_COLS - 1));

        // ── Row 3: Column headers ─────────────────────────────────────────────
        Row headerRow = sheet.createRow(3);
        headerRow.setHeightInPoints(36);
        for (int i = 0; i < TOTAL_COLS; i++) {
            Cell hCell = headerRow.createCell(i);
            hCell.setCellValue(headerLabels[i]);
            hCell.setCellStyle(hdrStyle);
        }

        // ── Rows 4+: Data rows ────────────────────────────────────────────────
        int dataStartsFrom = 4;
        for (int i = 0; i < responseList.size(); i++) {
            FarmerMulberryExtensionResponse r = responseList.get(i);
            Row row = sheet.createRow(dataStartsFrom + i);
            XSSFCellStyle rowStyle = (i % 2 != 0) ? dataAlt : dataWhite;
            String[] values = {
                String.valueOf(r.getSerialNumber()),
                r.getFirstName(),
                r.getFatherName(),
                r.getFruitsId(),
                r.getScheme(),
                r.getAddressText(),
                r.getTscName(),
                r.getTscNameKannada(),
                r.getMulberryVarietyName(),
                r.getMulberryVarietyNameKannada(),
                r.getPlantationDate(),
                r.getNumberOfSaplings(),
                r.getMulberryArea(),
                r.getSpacing(),
                r.getApplicationType(),
                r.getUprootingReason(),
                r.getUprootingDate(),
                r.getDistrictName(),
                r.getTalukName()
            };
            for (int col = 0; col < TOTAL_COLS; col++) {
                Cell cell = row.createCell(col);
                cell.setCellValue(values[col] != null ? values[col] : "");
                cell.setCellStyle(rowStyle);
            }
        }

        sheet.createFreezePane(0, 4);
        for (int col = 0; col < TOTAL_COLS; col++) {
            sheet.setColumnWidth(col, 20 * 256);
        }

        String userHome = System.getProperty("user.home");
        String directoryPath = Paths.get(userHome, "Downloads").toString();
        Files.createDirectories(Paths.get(directoryPath));
        Path filePath = Paths.get(directoryPath, "farmer_mulberry_extension_report" + Util.getISTLocalDate() + ".xlsx");

        FileOutputStream fileOut = new FileOutputStream(filePath.toString());
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        FileInputStream fileIn = new FileInputStream(filePath.toString());
        return fileIn;
    }

    public ResponseEntity<?> getSupplyOfDisinfectantDetails(Long tscId, int pageNumber, int pageSize) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(List.class);
        List<SupplyOfDisinfectantResponse> responseList = new ArrayList<>();

        tscId = (tscId != null && tscId == 0) ? null : tscId;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> page = supplyOfDisinfectantsRepository.getSupplyOfDisinfectantDetails(tscId, pageable);

        buildResponseForDisinfectant(responseList, page.getContent(), pageNumber, pageSize);

        rw.setTotalRecords(page.getTotalElements());
        rw.setContent(responseList);
        return ResponseEntity.ok(rw);
    }

    public FileInputStream getSupplyOfDisinfectantReport(Long tscId, int pageNumber, int pageSize) throws Exception {
        List<SupplyOfDisinfectantResponse> responseList = new ArrayList<>();

        tscId = (tscId != null && tscId == 0) ? null : tscId;

        Pageable pageable = null; // fetch all records
        Page<Object[]> page = supplyOfDisinfectantsRepository.getSupplyOfDisinfectantDetails(tscId, pageable);

        buildResponseForDisinfectant(responseList, page.getContent(), pageNumber, pageSize);

        String[] headerLabels = {
            "Sl.No", "Farmer Name", "Father Name", "Fruits ID", "Invoice No/Date",
            "Quantity", "Disinfectant Name", "Quantity Supplied", "Supply Date",
            "Size of Rearing House", "No of DFLs", "Disinfectant Master Name", "TSC Name"
        };
        final int TOTAL_COLS = headerLabels.length;

        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        workbook.setCompressTempFiles(true);
        Sheet sheet = workbook.createSheet("Supply Of Disinfectant Report");

        // ── Colors ───────────────────────────────────────────────────────────
        XSSFColor primaryBlue = new XSSFColor(new byte[]{(byte)26,  (byte)95,  (byte)158}, null);
        XSSFColor darkNavy    = new XSSFColor(new byte[]{(byte)12,  (byte)74,  (byte)158}, null);
        XSSFColor altRow      = new XSSFColor(new byte[]{(byte)247, (byte)250, (byte)253}, null);
        XSSFColor white       = new XSSFColor(new byte[]{(byte)255, (byte)255, (byte)255}, null);
        XSSFColor darkText    = new XSSFColor(new byte[]{(byte)30,  (byte)58,  (byte)95},  null);
        XSSFColor black       = new XSSFColor(new byte[]{(byte)0,   (byte)0,   (byte)0},   null);

        // ── Fonts ────────────────────────────────────────────────────────────
        XSSFFont titleFont = (XSSFFont) workbook.createFont();
        titleFont.setFontName("Calibri"); titleFont.setFontHeightInPoints((short)16);
        titleFont.setBold(true); titleFont.setColor(white);

        XSSFFont subFont = (XSSFFont) workbook.createFont();
        subFont.setFontName("Calibri"); subFont.setFontHeightInPoints((short)11);
        subFont.setColor(white);

        XSSFFont hdrFont = (XSSFFont) workbook.createFont();
        hdrFont.setFontName("Calibri"); hdrFont.setFontHeightInPoints((short)11);
        hdrFont.setBold(true); hdrFont.setColor(white);

        XSSFFont dataFont = (XSSFFont) workbook.createFont();
        dataFont.setFontName("Calibri"); dataFont.setFontHeightInPoints((short)10);
        dataFont.setColor(darkText);

        // ── Styles ───────────────────────────────────────────────────────────
        XSSFCellStyle titleStyle = (XSSFCellStyle) workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setFillForegroundColor(darkNavy);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setBorderTop(BorderStyle.THIN); titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN); titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setTopBorderColor(black); titleStyle.setBottomBorderColor(black);
        titleStyle.setLeftBorderColor(black); titleStyle.setRightBorderColor(black);

        XSSFCellStyle subStyle = (XSSFCellStyle) workbook.createCellStyle();
        subStyle.setFont(subFont);
        subStyle.setFillForegroundColor(primaryBlue);
        subStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        subStyle.setAlignment(HorizontalAlignment.CENTER);
        subStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        subStyle.setBorderTop(BorderStyle.THIN); subStyle.setBorderBottom(BorderStyle.THIN);
        subStyle.setBorderLeft(BorderStyle.THIN); subStyle.setBorderRight(BorderStyle.THIN);
        subStyle.setTopBorderColor(black); subStyle.setBottomBorderColor(black);
        subStyle.setLeftBorderColor(black); subStyle.setRightBorderColor(black);

        XSSFCellStyle hdrStyle = (XSSFCellStyle) workbook.createCellStyle();
        hdrStyle.setFont(hdrFont);
        hdrStyle.setFillForegroundColor(primaryBlue);
        hdrStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        hdrStyle.setAlignment(HorizontalAlignment.CENTER);
        hdrStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        hdrStyle.setWrapText(true);
        hdrStyle.setBorderTop(BorderStyle.THIN); hdrStyle.setBorderBottom(BorderStyle.THIN);
        hdrStyle.setBorderLeft(BorderStyle.THIN); hdrStyle.setBorderRight(BorderStyle.THIN);
        hdrStyle.setTopBorderColor(black); hdrStyle.setBottomBorderColor(black);
        hdrStyle.setLeftBorderColor(black); hdrStyle.setRightBorderColor(black);

        XSSFCellStyle dataWhite = (XSSFCellStyle) workbook.createCellStyle();
        dataWhite.setFont(dataFont);
        dataWhite.setAlignment(HorizontalAlignment.CENTER);
        dataWhite.setVerticalAlignment(VerticalAlignment.CENTER);
        dataWhite.setWrapText(true);
        dataWhite.setBorderTop(BorderStyle.THIN); dataWhite.setBorderBottom(BorderStyle.THIN);
        dataWhite.setBorderLeft(BorderStyle.THIN); dataWhite.setBorderRight(BorderStyle.THIN);
        dataWhite.setTopBorderColor(black); dataWhite.setBottomBorderColor(black);
        dataWhite.setLeftBorderColor(black); dataWhite.setRightBorderColor(black);

        XSSFCellStyle dataAlt = (XSSFCellStyle) workbook.createCellStyle();
        dataAlt.cloneStyleFrom(dataWhite);
        dataAlt.setFillForegroundColor(altRow);
        dataAlt.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // ── Row 0: Department title ───────────────────────────────────────────
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(36);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Department of Sericulture, Government of Karnataka");
        titleCell.setCellStyle(titleStyle);
        for (int c = 1; c < TOTAL_COLS; c++) { titleRow.createCell(c).setCellStyle(titleStyle); }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, TOTAL_COLS - 1));

        // ── Row 1: Report name ────────────────────────────────────────────────
        Row reportRow = sheet.createRow(1);
        reportRow.setHeightInPoints(24);
        Cell reportCell = reportRow.createCell(0);
        reportCell.setCellValue("SUPPLY OF DISINFECTANT REPORT");
        reportCell.setCellStyle(subStyle);
        for (int c = 1; c < TOTAL_COLS; c++) { reportRow.createCell(c).setCellStyle(subStyle); }
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, TOTAL_COLS - 1));

        // ── Row 2: Generated on ───────────────────────────────────────────────
        Row genRow = sheet.createRow(2);
        genRow.setHeightInPoints(20);
        Cell genCell = genRow.createCell(0);
        genCell.setCellValue("Generated On: " + new java.text.SimpleDateFormat("dd-MMM-yyyy HH:mm").format(new java.util.Date()));
        genCell.setCellStyle(subStyle);
        for (int c = 1; c < TOTAL_COLS; c++) { genRow.createCell(c).setCellStyle(subStyle); }
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, TOTAL_COLS - 1));

        // ── Row 3: Column headers ─────────────────────────────────────────────
        Row headerRow = sheet.createRow(3);
        headerRow.setHeightInPoints(36);
        for (int i = 0; i < TOTAL_COLS; i++) {
            Cell hCell = headerRow.createCell(i);
            hCell.setCellValue(headerLabels[i]);
            hCell.setCellStyle(hdrStyle);
        }

        // ── Rows 4+: Data rows ────────────────────────────────────────────────
        int dataStartsFrom = 4;
        for (int i = 0; i < responseList.size(); i++) {
            SupplyOfDisinfectantResponse r = responseList.get(i);
            Row row = sheet.createRow(dataStartsFrom + i);
            XSSFCellStyle rowStyle = (i % 2 != 0) ? dataAlt : dataWhite;
            String[] values = {
                String.valueOf(r.getSerialNumber()),
                r.getFarmerName(),
                r.getFatherName(),
                r.getFruitsId(),
                r.getInvoiceNoDate(),
                r.getQuantity() != null ? String.valueOf(r.getQuantity()) : "",
                r.getDisinfectantName(),
                r.getQuantitySupplied() != null ? String.valueOf(r.getQuantitySupplied()) : "",
                r.getSupplyDate(),
                r.getSizeOfRearingHouse(),
                r.getNoOfDfls() != null ? String.valueOf(r.getNoOfDfls()) : "",
                r.getDisinfectantMasterName(),
                r.getTscName()
            };
            for (int col = 0; col < TOTAL_COLS; col++) {
                Cell cell = row.createCell(col);
                cell.setCellValue(values[col] != null ? values[col] : "");
                cell.setCellStyle(rowStyle);
            }
        }

        sheet.createFreezePane(0, 4);
        for (int col = 0; col < TOTAL_COLS; col++) {
            sheet.setColumnWidth(col, 20 * 256);
        }

        String userHome = System.getProperty("user.home");
        String directoryPath = Paths.get(userHome, "Downloads").toString();
        Files.createDirectories(Paths.get(directoryPath));
        Path filePath = Paths.get(directoryPath, "supply_of_disinfectant_report" + Util.getISTLocalDate() + ".xlsx");

        FileOutputStream fileOut = new FileOutputStream(filePath.toString());
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        FileInputStream fileIn = new FileInputStream(filePath.toString());
        return fileIn;
    }

    private static void buildResponseForDisinfectant(List<SupplyOfDisinfectantResponse> responseList, List<Object[]> list, int pageNumber, int pageSize) {
        int serialNumber = pageNumber * pageSize + 1;
        for (Object[] arr : list) {
            SupplyOfDisinfectantResponse response = SupplyOfDisinfectantResponse.builder()
                    .serialNumber(serialNumber++)
                    .farmerName(Util.objectToString(arr[0]))
                    .fatherName(Util.objectToString(arr[1]))
                    .fruitsId(Util.objectToString(arr[2]))
                    .invoiceNoDate(Util.objectToString(arr[3]))
                    .quantity(Util.objectToInteger(arr[4]))
                    .disinfectantName(Util.objectToString(arr[5]))
                    .quantitySupplied(Util.objectToInteger(arr[6]))
                    .supplyDate(Util.objectToString(arr[7]))
                    .sizeOfRearingHouse(Util.objectToString(arr[8]))
                    .noOfDfls(Util.objectToInteger(arr[9]))
                    .disinfectantMasterName(Util.objectToString(arr[10]))
                    .tscName(Util.objectToString(arr[11]))
                    .build();
            responseList.add(response);
        }
    }


    @Transactional
    public CropInspectionResponse updateCropDetailsSeedMarketDetails(CropInspectionRequest cropInspectionRequest){
        CropInspectionResponse cropDetailsSeedMarketResponse = new CropInspectionResponse();
//        List<RpRoleAssociation> rpRoleAssociationList = rpRoleAssociationRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(rpPageRootList.size()>0){
//            throw new ValidationException("RpPageRoot already exists with this name, duplicates are not allowed.");
//        }

        FitnessCertificate fitnessCertificate = fitnessCertificateRepository.findByFitnessCertificateIdAndActiveIn(cropInspectionRequest.getCropInspectionId(), Set.of(true,false));
        if(Objects.nonNull(fitnessCertificate)){
            if (fitnessCertificate.getSaleAndDisposalId() != null) {
                Optional<SaleAndDisposalOfDfls> sadodOpt = saleAndDisposalOfDflsRepository.findByIdAndActive(fitnessCertificate.getSaleAndDisposalId().intValue(), true);
                if (sadodOpt.isPresent() && Integer.valueOf(1).equals(sadodOpt.get().getIsDisposed())) {
                    cropDetailsSeedMarketResponse.setError(true);
                    cropDetailsSeedMarketResponse.setError_description("Cannot edit: E-Inward has already been processed for this record.");
                    return cropDetailsSeedMarketResponse;
                }
            }
            if (cropInspectionRequest.getChowkiId() != null) {
                fitnessCertificate.setChowkiId(cropInspectionRequest.getChowkiId());
            }
            if (cropInspectionRequest.getSaleAndDisposalId() != null) {
                fitnessCertificate.setSaleAndDisposalId(cropInspectionRequest.getSaleAndDisposalId());
            }
            fitnessCertificate.setFarmerId(cropInspectionRequest.getFarmerId());
            fitnessCertificate.setExpectedCocoon(cropInspectionRequest.getExpectedCocoon());
            fitnessCertificate.setLotTestDetails(cropInspectionRequest.getLotTestDetails());
            fitnessCertificate.setDiseaseStatusId(cropInspectionRequest.getDiseaseStatusId());
            fitnessCertificate.setNoOfChandies(cropInspectionRequest.getNoOfChandies());
            fitnessCertificate.setSpunFromDate(cropInspectionRequest.getSpunFromDate());
            fitnessCertificate.setSpunToDate(cropInspectionRequest.getSpunToDate());
            fitnessCertificate.setFitnessCertificatePath(cropInspectionRequest.getFitnessCertificatePath());
            fitnessCertificate.setFruitsId(cropInspectionRequest.getFruitsId());
            fitnessCertificate.setActive(true);
            FitnessCertificate fitnessCertificate1 = fitnessCertificateRepository.save(fitnessCertificate);

            // Update race, grainage, and DFLs count on the linked SaleAndDisposalOfDfls via JOIN — no entity fields added to FitnessCertificate
            Long linkedSadodId = fitnessCertificate1.getSaleAndDisposalId();
            if (linkedSadodId != null) {
                Optional<SaleAndDisposalOfDfls> sadodToUpdate = saleAndDisposalOfDflsRepository.findByIdAndActive(linkedSadodId.intValue(), true);
                if (sadodToUpdate.isPresent()) {
                    SaleAndDisposalOfDfls sadod = sadodToUpdate.get();
                    if (cropInspectionRequest.getRaceOfDfls() != null) sadod.setRaceId(cropInspectionRequest.getRaceOfDfls());
                    if (cropInspectionRequest.getGrainageMasterId() != null) sadod.setGrainageId(cropInspectionRequest.getGrainageMasterId());
                    if (cropInspectionRequest.getNumbersOfDfls() != null) sadod.setNumberOfDflsDisposed(cropInspectionRequest.getNumbersOfDfls());
                    if (cropInspectionRequest.getDateOfBrushing() != null) sadod.setExpectedDateOfHatching(cropInspectionRequest.getDateOfBrushing());
                    saleAndDisposalOfDflsRepository.save(sadod);
                }
            }

            cropDetailsSeedMarketResponse = mapper.fitnessCertificateEntityToObject(fitnessCertificate1, CropInspectionResponse.class);
            cropDetailsSeedMarketResponse.setError(false);
        } else {
            cropDetailsSeedMarketResponse.setError(true);
            cropDetailsSeedMarketResponse.setError_description("Error occurred while fetching Details");
            // throw new ValidationException("Error occurred while fetching village");
        }

        return cropDetailsSeedMarketResponse;
    }


    @Transactional
    public void markSaleAndDisposalAsDisposed(Long saleAndDisposalId) {
        if (saleAndDisposalId == null) return;
        saleAndDisposalOfDflsRepository.findByIdAndActive(saleAndDisposalId.intValue(), true)
                .ifPresent(sadod -> {
                    sadod.setIsDisposed(1);
                    saleAndDisposalOfDflsRepository.save(sadod);
                });
    }

    @Transactional
    public CropInspectionResponse updateFitnessCertificateDetails(CropInspectionRequest cropInspectionRequest){
        CropInspectionResponse cropDetailsSeedMarketResponse = new CropInspectionResponse();
//        List<RpRoleAssociation> rpRoleAssociationList = rpRoleAssociationRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(rpPageRootList.size()>0){
//            throw new ValidationException("RpPageRoot already exists with this name, duplicates are not allowed.");
//        }

        CropInspection cropInspection = cropInspectionRepository.findByCropInspectionIdAndActiveIn(cropInspectionRequest.getCropInspectionId(), Set.of(true,false));
        if(Objects.nonNull(cropInspection)){
            if (cropInspection.getSaleAndDisposalId() != null) {
                Optional<SaleAndDisposalOfDfls> sadodOpt = saleAndDisposalOfDflsRepository.findByIdAndActive(cropInspection.getSaleAndDisposalId().intValue(), true);
                if (sadodOpt.isPresent() && Integer.valueOf(1).equals(sadodOpt.get().getIsDisposed())) {
                    cropDetailsSeedMarketResponse.setError(true);
                    cropDetailsSeedMarketResponse.setError_description("Cannot edit: E-Inward has already been processed for this record.");
                    return cropDetailsSeedMarketResponse;
                }
            }
            if (cropInspectionRequest.getChowkiId() != null) {
                cropInspection.setChowkiId(cropInspectionRequest.getChowkiId());
            }
            if (cropInspectionRequest.getSaleAndDisposalId() != null) {
                cropInspection.setSaleAndDisposalId(cropInspectionRequest.getSaleAndDisposalId());
            }
            if (cropInspectionRequest.getFarmerId() != null) {
                cropInspection.setFarmerId(cropInspectionRequest.getFarmerId());
            }
            if (cropInspectionRequest.getCropInspectionTypeId() != null) {
                cropInspection.setCropInspectionTypeId(cropInspectionRequest.getCropInspectionTypeId());
            }
            if (cropInspectionRequest.getDate() != null) {
                cropInspection.setDate(cropInspectionRequest.getDate());
            }
            if (cropInspectionRequest.getReasonId() != null) {
                cropInspection.setReasonId(cropInspectionRequest.getReasonId());
            }
            if (cropInspectionRequest.getCropStatusId() != null) {
                cropInspection.setCropStatusId(cropInspectionRequest.getCropStatusId());
            }
            if (cropInspectionRequest.getMountId() != null) {
                cropInspection.setMountId(cropInspectionRequest.getMountId());
            }
            if (cropInspectionRequest.getFruitsId() != null) {
                cropInspection.setFruitsId(cropInspectionRequest.getFruitsId());
            }
            if (cropInspectionRequest.getCropInspectionPath() != null) {
                cropInspection.setCropInspectionPath(cropInspectionRequest.getCropInspectionPath());
            }
            cropInspection.setNote(cropInspectionRequest.getNote());
            cropInspection.setActive(true);
            CropInspection cropInspection1 = cropInspectionRepository.save(cropInspection);

            // Update race, grainage, DFLs, and date of brushing on the linked SaleAndDisposalOfDfls
            Long linkedSadodId = cropInspection1.getSaleAndDisposalId();
            if (linkedSadodId != null) {
                Optional<SaleAndDisposalOfDfls> sadodToUpdate = saleAndDisposalOfDflsRepository.findByIdAndActive(linkedSadodId.intValue(), true);
                if (sadodToUpdate.isPresent()) {
                    SaleAndDisposalOfDfls sadod = sadodToUpdate.get();
                    if (cropInspectionRequest.getRaceOfDfls() != null) sadod.setRaceId(cropInspectionRequest.getRaceOfDfls());
                    if (cropInspectionRequest.getGrainageMasterId() != null) sadod.setGrainageId(cropInspectionRequest.getGrainageMasterId());
                    if (cropInspectionRequest.getNumbersOfDfls() != null) sadod.setNumberOfDflsDisposed(cropInspectionRequest.getNumbersOfDfls());
                    if (cropInspectionRequest.getDateOfBrushing() != null) sadod.setExpectedDateOfHatching(cropInspectionRequest.getDateOfBrushing());
                    saleAndDisposalOfDflsRepository.save(sadod);
                }
            }

            cropDetailsSeedMarketResponse = mapper.cropInspectionEntityToObject(cropInspection1, CropInspectionResponse.class);
            cropDetailsSeedMarketResponse.setError(false);
        } else {
            cropDetailsSeedMarketResponse.setError(true);
            cropDetailsSeedMarketResponse.setError_description("Error occurred while fetching Details");
            // throw new ValidationException("Error occurred while fetching village");
        }

        return cropDetailsSeedMarketResponse;
    }




}
