package com.sericulture.service;

import com.sericulture.helper.Util;
import com.sericulture.model.Mapper;
import com.sericulture.model.ResponseWrapper;
import com.sericulture.model.api.ChowkiManagementByIdDTO;
import com.sericulture.model.api.requests.AddSaleDisposalRequest;
import com.sericulture.model.api.requests.UpdateChowkiRequest;
import com.sericulture.model.api.response.AddChowkiResponse;
import com.sericulture.model.entity.ChowkiManagement;
import com.sericulture.model.api.requests.AddChowkiRequest;
import com.sericulture.model.api.response.CommonChowkiResponse;
import com.sericulture.model.api.ChowkiManagementResponse;
import com.sericulture.model.entity.District;
import com.sericulture.model.entity.SaleAndDisposalOfDfls;
import com.sericulture.model.entity.UserMaster;
import com.sericulture.repository.ChowkiManagementRepository;
import com.sericulture.repository.DistrictRepository;
import com.sericulture.repository.SaleAndDisposalOfDflsRepository;
import com.sericulture.repository.UserMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static io.micrometer.core.ipc.http.HttpSender.Request.build;

@Service
@Slf4j
public class ChowkiManagementService {
    @Autowired
    ChowkiManagementRepository chowkiManagemenyRepository;

    @Autowired
    SaleAndDisposalOfDflsRepository saleAndDisposalOfDflsRepository;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    UserMasterRepository userMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    public AddChowkiResponse insertData(AddChowkiRequest addChowkiRequest) {
        AddChowkiResponse addChowkiResponse =new AddChowkiResponse();
        ChowkiManagement chowkiManagement=new ChowkiManagement();
        try {
            // Fetch farmerId by fruitsId
            Optional<Long> farmerIdOptional = chowkiManagemenyRepository.findFarmerIdByFruitsId(addChowkiRequest.getFruitsId());
            if (!farmerIdOptional.isPresent()) {
                throw new Exception("Farmer not found for the given fruitsId");
            }

            // Set farmerId and isVerified = 1
            chowkiManagement.setFarmerId(farmerIdOptional.get());
            chowkiManagement.setIsVerified(0);  // Set isVerified as 1

            float price=(addChowkiRequest.getRatePer100Dfls()*addChowkiRequest.getNumbersOfDfls())/100;
            String distCode=getDistrictById(addChowkiRequest.getDistrict()).toUpperCase();
            String nextSeq=chowkiManagemenyRepository.getNextValRecieptSequence().toString();

            if(nextSeq.length()==1)
                nextSeq="0"+nextSeq;

            String receipt= "CRC/"+distCode+"/"+nextSeq;

            chowkiManagement.setDflsSource(addChowkiRequest.getDflsSource());
            chowkiManagement.setDispatchDate(addChowkiRequest.getDispatchDate());
            chowkiManagement.setDistrict(addChowkiRequest.getDistrict());
            chowkiManagement.setFarmerName(addChowkiRequest.getFarmerName());
            chowkiManagement.setState(addChowkiRequest.getState());
            chowkiManagement.setFatherName(addChowkiRequest.getFatherName());
            chowkiManagement.setFruitsId(addChowkiRequest.getFruitsId());
            chowkiManagement.setLotNumberCrc(addChowkiRequest.getLotNumberCrc());
            chowkiManagement.setLotNumberRsp(addChowkiRequest.getLotNumberRsp());
            chowkiManagement.setNumbersOfDfls(addChowkiRequest.getNumbersOfDfls());
            chowkiManagement.setPrice(price);
            chowkiManagement.setRaceOfDfls(addChowkiRequest.getRaceOfDfls());
            chowkiManagement.setRatePer100Dfls(addChowkiRequest.getRatePer100Dfls());
            chowkiManagement.setSoldAfter1stOr2ndMould(addChowkiRequest.getSoldAfter1stOr2ndMould());
            chowkiManagement.setTsc(addChowkiRequest.getTsc());
            chowkiManagement.setVillage(addChowkiRequest.getVillage());
            chowkiManagement.setTaluk(addChowkiRequest.getTaluk());
            chowkiManagement.setHobli(addChowkiRequest.getHobli());
            chowkiManagement.setHatchingDate(addChowkiRequest.getHatchingDate());
            chowkiManagement.setUserMasterId(Util.getUserId(Util.getTokenValues()));
            chowkiManagement.setReceiptNo(receipt);
            chowkiManagemenyRepository.save(chowkiManagement);

            addChowkiResponse.setReceiptNo(receipt);
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

    public AddChowkiResponse insertDFLData(AddChowkiRequest addChowkiRequest) {
        AddChowkiResponse addChowkiResponse =new AddChowkiResponse();
        ChowkiManagement chowkiManagement=new ChowkiManagement();
        try {
            // Fetch farmerId by fruitsId
            Optional<Long> farmerIdOptional = chowkiManagemenyRepository.findFarmerIdByFruitsId(addChowkiRequest.getFruitsId());
            if (!farmerIdOptional.isPresent()) {
                throw new Exception("Farmer not found for the given fruitsId");
            }

            // Set farmerId and isVerified = 1
            chowkiManagement.setFarmerId(farmerIdOptional.get());
            chowkiManagement.setIsVerified(0);
            chowkiManagement.setIsSaleTracked(0); // Set isVerified as 1

            float price=(addChowkiRequest.getRatePer100Dfls()*addChowkiRequest.getNumbersOfDfls())/100;
//            String distCode=getDistrictById(addChowkiRequest.getDistrict()).toUpperCase();
//            String nextSeq=chowkiManagemenyRepository.getNextValRecieptSequence().toString();

//            if(nextSeq.length()==1)
//                nextSeq="0"+nextSeq;
//
//            String receipt= "CRC/"+distCode+"/"+nextSeq;

            chowkiManagement.setDflsSource(addChowkiRequest.getDflsSource());
            chowkiManagement.setDispatchDate(addChowkiRequest.getDispatchDate());
            chowkiManagement.setDistrict(addChowkiRequest.getDistrict());
            chowkiManagement.setFarmerName(addChowkiRequest.getFarmerName());
            chowkiManagement.setState(addChowkiRequest.getState());
            chowkiManagement.setFatherName(addChowkiRequest.getFatherName());
            chowkiManagement.setFruitsId(addChowkiRequest.getFruitsId());
            chowkiManagement.setLotNumberCrc(addChowkiRequest.getLotNumberCrc());
            chowkiManagement.setLotNumberRsp(addChowkiRequest.getLotNumberRsp());
            chowkiManagement.setNumbersOfDfls(addChowkiRequest.getNumbersOfDfls());
            chowkiManagement.setPrice(price);
            chowkiManagement.setRaceOfDfls(addChowkiRequest.getRaceOfDfls());
            chowkiManagement.setRatePer100Dfls(addChowkiRequest.getRatePer100Dfls());
            chowkiManagement.setSoldAfter1stOr2ndMould(addChowkiRequest.getSoldAfter1stOr2ndMould());
            chowkiManagement.setTsc(addChowkiRequest.getTsc());
            chowkiManagement.setVillage(addChowkiRequest.getVillage());
            chowkiManagement.setTaluk(addChowkiRequest.getTaluk());
            chowkiManagement.setHobli(addChowkiRequest.getHobli());
            chowkiManagement.setHatchingDate(addChowkiRequest.getHatchingDate());
            chowkiManagement.setUserMasterId(Util.getUserId(Util.getTokenValues()));
            chowkiManagement.setReceiptNo(addChowkiRequest.getReceiptNo());
            chowkiManagemenyRepository.save(chowkiManagement);

//            addChowkiResponse.setReceiptNo(receipt);
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

    @Transactional
    public AddChowkiResponse insertSaleAndDisposalDFlDetails(AddSaleDisposalRequest addSaleDisposalRequest) {
        AddChowkiResponse addChowkiResponse = new AddChowkiResponse();
        SaleAndDisposalOfDfls saleAndDisposalOfDfls = mapper.saleAndDisposalOfDflsObjectToEntity(addSaleDisposalRequest, SaleAndDisposalOfDfls.class);
        validator.validate(saleAndDisposalOfDfls);
//        List<RpPagePermission> rpPagePermissionList = rpPagePermissionRepository.findByRpPagePermissionName(rpPagePermissionRequest.getRpPagePermissionName());
//        if(!rpPagePermissionList.isEmpty() && rpPagePermissionList.stream().filter(RpPagePermission::getActive).findAny().isPresent()){
//            throw new ValidationException("RpPagePermission name already exist");
//        }
//        if(!rpPagePermissionList.isEmpty() && rpPagePermissionList.stream().filter(Predicate.not(RpPagePermission::getActive)).findAny().isPresent()){
//            throw new ValidationException("RpPagePermission name already exist with inactive state");
//        }
        return mapper.saleAndDisposalOfDflsEntityToObject(saleAndDisposalOfDflsRepository.save(saleAndDisposalOfDfls), AddChowkiResponse.class);
    }


    public CommonChowkiResponse updateData(UpdateChowkiRequest updateChowkiRequest) {
        ChowkiManagement chowkiManagement = new ChowkiManagement();
        CommonChowkiResponse commonChowkiResponse =new CommonChowkiResponse();
        Long userMasterId=Util.getUserId(Util.getTokenValues());
        if(chowkiManagemenyRepository.findByChowkiIdAndUserMasterId(updateChowkiRequest.getChowkiId(),userMasterId).isEmpty()){
            commonChowkiResponse.setError(1);
            commonChowkiResponse.setMessage("Invalid Chowki ID");
        }
        else {
            try {
                // Fetch farmerId by fruitsId
                Optional<Long> farmerIdOptional = chowkiManagemenyRepository.findFarmerIdByFruitsId(updateChowkiRequest.getFruitsId());
                if (!farmerIdOptional.isPresent()) {
                    throw new Exception("Farmer not found for the given fruitsId");
                }

                // Set farmerId and isVerified = 1
                chowkiManagement.setFarmerId(farmerIdOptional.get());
                chowkiManagement.setIsVerified(1);  // Set isVerified as 1

                float price=(updateChowkiRequest.getRatePer100Dfls()*updateChowkiRequest.getNumbersOfDfls())/100;
                chowkiManagement.setReceiptNo(updateChowkiRequest.getReceiptNo());
                chowkiManagement.setChowkiId(updateChowkiRequest.getChowkiId());
                chowkiManagement.setDflsSource(updateChowkiRequest.getDflsSource());
                chowkiManagement.setDispatchDate(updateChowkiRequest.getDispatchDate());
                chowkiManagement.setDistrict(updateChowkiRequest.getDistrict());
                chowkiManagement.setFarmerName(updateChowkiRequest.getFarmerName());
                chowkiManagement.setState(updateChowkiRequest.getState());
                chowkiManagement.setFatherName(updateChowkiRequest.getFatherName());
                chowkiManagement.setFruitsId(updateChowkiRequest.getFruitsId());
                chowkiManagement.setLotNumberCrc(updateChowkiRequest.getLotNumberCrc());
                chowkiManagement.setLotNumberRsp(updateChowkiRequest.getLotNumberRsp());
                chowkiManagement.setNumbersOfDfls(updateChowkiRequest.getNumbersOfDfls());
                chowkiManagement.setPrice(price);
                chowkiManagement.setRaceOfDfls(updateChowkiRequest.getRaceOfDfls());
                chowkiManagement.setRatePer100Dfls(updateChowkiRequest.getRatePer100Dfls());
                chowkiManagement.setSoldAfter1stOr2ndMould(updateChowkiRequest.getSoldAfter1stOr2ndMould());
                chowkiManagement.setTsc(updateChowkiRequest.getTsc());
                chowkiManagement.setVillage(updateChowkiRequest.getVillage());
                chowkiManagement.setTaluk(updateChowkiRequest.getTaluk());
                chowkiManagement.setHobli(updateChowkiRequest.getHobli());
                chowkiManagement.setHatchingDate(updateChowkiRequest.getHatchingDate());
                chowkiManagement.setUserMasterId(Util.getUserId(Util.getTokenValues()));
                chowkiManagemenyRepository.save(chowkiManagement);
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

    public CommonChowkiResponse updateDFLData(UpdateChowkiRequest updateChowkiRequest) {
        CommonChowkiResponse commonChowkiResponse = new CommonChowkiResponse();
        Long userMasterId = Util.getUserId(Util.getTokenValues());

        // Fetch existing ChowkiManagement record using DTO
        Optional<ChowkiManagementByIdDTO> existingChowkiOptional = chowkiManagemenyRepository
                .findByChowkiIdAndUserMasterId(updateChowkiRequest.getChowkiId(), userMasterId);

        if (existingChowkiOptional.isEmpty()) {
            commonChowkiResponse.setError(1);
            commonChowkiResponse.setMessage("Invalid Chowki ID");
            return commonChowkiResponse;
        }

        ChowkiManagementByIdDTO existingChowki = existingChowkiOptional.get();
        ChowkiManagement chowkiManagement = new ChowkiManagement();

        try {
            // Fetch farmerId by fruitsId
            Optional<Long> farmerIdOptional = chowkiManagemenyRepository.findFarmerIdByFruitsIdAndChawkiId(updateChowkiRequest.getFruitsId(), updateChowkiRequest.getChowkiId());
            if (!farmerIdOptional.isPresent()) {
                throw new Exception("Farmer not found for the given fruitsId");
            }

            // Set farmerId and isVerified
            chowkiManagement.setFarmerId(farmerIdOptional.get());
                chowkiManagement.setIsVerified(1);  // Set isVerified as 1

            // Set the Chowki ID from the existing record
            chowkiManagement.setChowkiId(existingChowki.getChowkiId());

            // Other fields
            chowkiManagement.setDflsSource(existingChowki.getDflsSource());
            chowkiManagement.setDispatchDate(existingChowki.getDispatchDate());
            chowkiManagement.setDistrict(existingChowki.getDistrict());
            chowkiManagement.setFarmerName(existingChowki.getFarmerName());
            chowkiManagement.setState(existingChowki.getState());
            chowkiManagement.setFatherName(existingChowki.getFatherName());
            chowkiManagement.setFruitsId(existingChowki.getFruitsId());
            chowkiManagement.setLotNumberCrc(existingChowki.getLotNumberCrc());
            chowkiManagement.setLotNumberRsp(existingChowki.getLotNumberRsp());
            chowkiManagement.setNumbersOfDfls(existingChowki.getNumbersOfDfls());

            // Calculate price
            float price = (existingChowki.getRatePer100Dfls() * existingChowki.getNumbersOfDfls()) / 100;
            chowkiManagement.setPrice(price);
            chowkiManagement.setRaceOfDfls(existingChowki.getRaceOfDfls());
            chowkiManagement.setRatePer100Dfls(existingChowki.getRatePer100Dfls());
            chowkiManagement.setSoldAfter1stOr2ndMould(existingChowki.getSoldAfter1stOr2ndMould());
            chowkiManagement.setTsc(existingChowki.getTsc());
            chowkiManagement.setVillage(existingChowki.getVillage());
            chowkiManagement.setTaluk(existingChowki.getTaluk());
            chowkiManagement.setHobli(existingChowki.getHobli());
            chowkiManagement.setHatchingDate(existingChowki.getHatchingDate());
            chowkiManagement.setUserMasterId(userMasterId);

            // Keep the existing receipt number
            chowkiManagement.setReceiptNo(updateChowkiRequest.getReceiptNo());

            // Save the updated chowkiManagement
            chowkiManagemenyRepository.save(chowkiManagement);

            commonChowkiResponse.setError(0);
            commonChowkiResponse.setMessage("Data updated successfully!");
        } catch (Exception E) {
            commonChowkiResponse.setError(1);
            commonChowkiResponse.setMessage("Something went wrong; please try again!");
            log.error("EXCEPTION : {}", E);
        }

        return commonChowkiResponse;
    }


    public List<ChowkiManagementByIdDTO> findAll() {
        return chowkiManagemenyRepository.getByUserMasterIdOrderByChowkiIdDesc(Util.getUserId(Util.getTokenValues()));
    }

    public CommonChowkiResponse deleteById(Integer id) {
        CommonChowkiResponse commonChowkiResponse =new CommonChowkiResponse();
        Long userMasterId=Util.getUserId(Util.getTokenValues());
        if(chowkiManagemenyRepository.findByChowkiIdAndUserMasterId(id,userMasterId).isEmpty()){
            commonChowkiResponse.setError(1);
            commonChowkiResponse.setMessage("Invalid Chowki ID");
        }
        else {
            try {
                chowkiManagemenyRepository.deleteById(id);
                commonChowkiResponse.setError(0);
                commonChowkiResponse.setMessage("Data deleted successfully!");
            } catch (Exception E) {
                commonChowkiResponse.setError(1);
                commonChowkiResponse.setMessage("Something went wrong; please try again!");
                log.error("EXCEPTION : {}", E);
            }
        }
        return commonChowkiResponse;
    }

    public Optional<ChowkiManagementByIdDTO> getById(Integer id) {
        Long userMasterId=Util.getUserId(Util.getTokenValues());
        Optional<ChowkiManagementByIdDTO> chowkiManagementDTO=chowkiManagemenyRepository.findByChowkiIdAndUserMasterId(id,userMasterId);
        if(chowkiManagementDTO.isEmpty()){
            return Optional.empty();
        }
        return chowkiManagementDTO;
    }

    private String getDistrictById(Integer id){
        String district= districtRepository.findById(id).get().getDistrictName();
        return district.substring(0,Math.min(district.length(), 3));
    }

    public List<ChowkiManagementResponse> getChowkiDetailsByFarmerId(Long farmerId) {
        List<Object[]> chowkiDetails = chowkiManagemenyRepository.getChawkiDetailsByFarmerId(farmerId);
        List<ChowkiManagementResponse> responses = new ArrayList<>();

        for (Object[] arr : chowkiDetails) {
            ChowkiManagementResponse response = ChowkiManagementResponse.builder()
                    .chowkiId(Util.objectToInteger(arr[0]))
                    .lotNumberCrc(Util.objectToString(arr[1]))
                    .lotNumberRsp(Util.objectToString(arr[2]))
                    .numbersOfDfls(Util.objectToLong(arr[3]))
                    .ratePer100Dfls(Util.objectToFloat(arr[4]))
                    .dflsSource(Util.objectToString(arr[6]))
                    .raceName(Util.objectToString(arr[7]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public List<ChowkiManagementResponse> getSaleAndDisposalDetailsByFruitsId(String fruitsId) {
        Long userId = Util.getUserId(Util.getTokenValues());
//        Long tscId = userMasterRepository.findByUserMasterIdAndActive(userId,1);
        Optional<UserMaster> userOpt = userMasterRepository.findByUserMasterIdAndActive(userId, true);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found or inactive");
        }

        Long tscId = userOpt.get().getTscMasterId();

        if (tscId == null) {
            throw new RuntimeException("TSC ID not found for userId: " + userId);
        }
        List<Object[]> chowkiDetails = chowkiManagemenyRepository.getSaleAndDisposalDetailsByFruitsId(fruitsId,tscId);
        List<ChowkiManagementResponse> responses = new ArrayList<>();

        for (Object[] arr : chowkiDetails) {
            ChowkiManagementResponse response = ChowkiManagementResponse.builder()
                    .saleAndDisposalId(Util.objectToInteger(arr[0]))
                    .lotNumberCrc(Util.objectToString(arr[1]))
                    .numbersOfDfls(Util.objectToLong(arr[2]))
                    .ratePer100Dfls(Util.objectToFloat(arr[3]))
                    .raceName(Util.objectToString(arr[5]))
                    .expectedHatchingDate(Util.objectToString(arr[6]))
                    .dateOfDisposal(Util.objectToString(arr[7]))
                    .dflsSource(Util.objectToString(arr[8]))
                    .nameAndAddressOfTheFarm(Util.objectToString(arr[9]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public List<ChowkiManagementResponse> getSaleAndDisposalDetailsForRssoByFruitsId(String fruitsId) {
        List<Object[]> chowkiDetails = chowkiManagemenyRepository.getSaleAndDisposalDetailsForRssoByFruitsId(fruitsId);
        List<ChowkiManagementResponse> responses = new ArrayList<>();

        for (Object[] arr : chowkiDetails) {
            ChowkiManagementResponse response = ChowkiManagementResponse.builder()
                    .saleAndDisposalId(Util.objectToInteger(arr[0]))
                    .lotNumberCrc(Util.objectToString(arr[1]))
                    .numbersOfDfls(Util.objectToLong(arr[2]))
                    .ratePer100Dfls(Util.objectToFloat(arr[3]))
                    .raceName(Util.objectToString(arr[5]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public List<ChowkiManagementResponse> getInspectioninfoForFarmer(Long farmerId) {
        List<Object[]> chowkiDetails = chowkiManagemenyRepository.getInspectioninfoForFarmer(farmerId);
        List<ChowkiManagementResponse> responses = new ArrayList<>();

        for (Object[] arr : chowkiDetails) {
            ChowkiManagementResponse response = ChowkiManagementResponse.builder()
                    .chowkiId(Util.objectToInteger(arr[0]))
                    .lotNumberCrc(Util.objectToString(arr[1]))
                    .lotNumberRsp(Util.objectToString(arr[2]))
                    .numbersOfDfls(Util.objectToLong(arr[3]))
                    .ratePer100Dfls(Util.objectToFloat(arr[4]))
                    .dflsSource(Util.objectToString(arr[6]))
                    .hatchingInspectionDate(Util.objectToString(arr[7]))
                    .raceName(Util.objectToString(arr[8]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public List<ChowkiManagementResponse> getInspectioninfoForFarmerFromSaleDisposalOfDFls(String fruitsId) {
        List<Object[]> chowkiDetails = chowkiManagemenyRepository.getInspectioninfoForFarmerFromSaleDisposalOfDFls(fruitsId);
        List<ChowkiManagementResponse> responses = new ArrayList<>();

        for (Object[] arr : chowkiDetails) {
            ChowkiManagementResponse response = ChowkiManagementResponse.builder()
                    .saleAndDisposalId(Util.objectToInteger(arr[0]))
                    .lotNumberCrc(Util.objectToString(arr[1]))
                    .numbersOfDfls(Util.objectToLong(arr[2]))
                    .ratePer100Dfls(Util.objectToFloat(arr[3]))
                    .raceName(Util.objectToString(arr[5]))
                    .hatchingInspectionDate(Util.objectToString(arr[6]))
                    .dateOfDisposal(Util.objectToString(arr[7]))
                    .dflsSource(Util.objectToString(arr[8]))
                    .nameAndAddressOfTheFarm(Util.objectToString(arr[9]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public List<ChowkiManagementResponse> getInspectioninfoForCocoonTrack(String fruitsId) {
        List<Object[]> chowkiDetails = chowkiManagemenyRepository.getInspectioninfoForCocoonTrack(fruitsId);
        List<ChowkiManagementResponse> responses = new ArrayList<>();

        for (Object[] arr : chowkiDetails) {
            ChowkiManagementResponse response = ChowkiManagementResponse.builder()
                    .chowkiId(Util.objectToInteger(arr[0]))
                    .lotNumberCrc(Util.objectToString(arr[1]))
                    .lotNumberRsp(Util.objectToString(arr[2]))
                    .numbersOfDfls(Util.objectToLong(arr[3]))
                    .ratePer100Dfls(Util.objectToFloat(arr[4]))
                    .dflsSource(Util.objectToString(arr[6]))
                    .hatchingInspectionDate(Util.objectToString(arr[7]))
                    .raceName(Util.objectToString(arr[8]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public List<ChowkiManagementResponse> getInspectioninfoForCocoonSaleTrack(String fruitsId) {
        List<Object[]> chowkiDetails = chowkiManagemenyRepository.getInspectioninfoForCocoonSaleTrack(fruitsId);
        List<ChowkiManagementResponse> responses = new ArrayList<>();

        for (Object[] arr : chowkiDetails) {
            ChowkiManagementResponse response = ChowkiManagementResponse.builder()
                    .saleAndDisposalId(Util.objectToInteger(arr[0]))
                    .ratePerDFls(Util.objectToString(arr[1]))
                    .numbersOfDfls(Util.objectToLong(arr[2]))
                    .lotNumberRsp(Util.objectToString(arr[3]))
                    .expectedHatchingDate(Util.objectToString(arr[4]))
                    .raceId(Util.objectToLong(arr[5]))
                    .raceName(Util.objectToString(arr[6]))
                    .auctionDate(Util.objectToString(arr[7]))
                    .cocoonsQuantity(Util.objectToString(arr[8]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    @Transactional
    public CommonChowkiResponse updateSaleDFLData(UpdateChowkiRequest updateChowkiRequest){
        CommonChowkiResponse commonChowkiResponse = new CommonChowkiResponse();

            SaleAndDisposalOfDfls saleAndDisposalOfDfls = saleAndDisposalOfDflsRepository.findByFruitsIdAndIdAndActiveIn(updateChowkiRequest.getFruitsId(),updateChowkiRequest.getSaleAndDisposalId(), Set.of(true, false));
            if (Objects.nonNull(saleAndDisposalOfDfls)) {
                saleAndDisposalOfDfls.setReceiptNo(updateChowkiRequest.getReceiptNo());
                saleAndDisposalOfDfls.setIsVerified(1);
                saleAndDisposalOfDfls.setUserMasterId(Util.getUserId(Util.getTokenValues()));
                saleAndDisposalOfDfls.setActive(true);
                SaleAndDisposalOfDfls saleAndDisposalOfDfls1 = saleAndDisposalOfDflsRepository.save(saleAndDisposalOfDfls);
                commonChowkiResponse = mapper.saleAndDisposalOfDflsEntityToObject(saleAndDisposalOfDfls1, CommonChowkiResponse.class);
                commonChowkiResponse.setError(0);
            } else {
                commonChowkiResponse.setError(1);
                commonChowkiResponse.setMessage("Error occurred while fetching sale and disposal of dfls");
                // throw new ValidationException("Error occurred while fetching village");
            }
//        }
        return commonChowkiResponse;
    }

    public List<ChowkiManagementResponse> getFarmerDetailsFromSaleDisposalOfDFlsByTsc(Long tscMasterId) {
        List<Object[]> chowkiDetails = chowkiManagemenyRepository.getFarmerDetailsFromSaleDisposalOfDFlsByTsc(tscMasterId);
        List<ChowkiManagementResponse> responses = new ArrayList<>();

        for (Object[] arr : chowkiDetails) {
            ChowkiManagementResponse response = ChowkiManagementResponse.builder()
                    .saleAndDisposalId(Util.objectToInteger(arr[0]))
                    .lotNumberCrc(Util.objectToString(arr[1]))
                    .numbersOfDfls(Util.objectToLong(arr[2]))
                    .ratePer100Dfls(Util.objectToFloat(arr[3]))
                    .raceName(Util.objectToString(arr[5]))
                    .hatchingInspectionDate(Util.objectToString(arr[6]))
                    .dateOfDisposal(Util.objectToString(arr[7]))
                    .dflsSource(Util.objectToString(arr[8]))
                    .nameAndAddressOfTheFarm(Util.objectToString(arr[9]))
                    .fruitsId(Util.objectToString(arr[10]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public List<ChowkiManagementResponse> getFarmerDetailsFromSaleDisposalOfDFlsRssoByTsc(Long tscMasterId) {
        List<Object[]> chowkiDetails = chowkiManagemenyRepository.getFarmerDetailsFromSaleDisposalOfDFlsRssoByTsc(tscMasterId);
        List<ChowkiManagementResponse> responses = new ArrayList<>();

        for (Object[] arr : chowkiDetails) {
            ChowkiManagementResponse response = ChowkiManagementResponse.builder()
                    .saleAndDisposalId(Util.objectToInteger(arr[0]))
                    .lotNumberCrc(Util.objectToString(arr[1]))
                    .numbersOfDfls(Util.objectToLong(arr[2]))
                    .ratePer100Dfls(Util.objectToFloat(arr[3]))
                    .raceName(Util.objectToString(arr[5]))
                    .nameAndAddressOfTheFarm(Util.objectToString(arr[6]))
                    .fruitsId(Util.objectToString(arr[7]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public List<ChowkiManagementResponse> getFarmerDetailsFromChowkiManagementByTsc(Long tscMasterId) {
        List<Object[]> chowkiDetails = chowkiManagemenyRepository.getFarmerDetailsFromChowkiManagementByTsc(tscMasterId);
        List<ChowkiManagementResponse> responses = new ArrayList<>();

        for (Object[] arr : chowkiDetails) {
            ChowkiManagementResponse response = ChowkiManagementResponse.builder()
                    .chowkiId(Util.objectToInteger(arr[0]))
                    .lotNumberCrc(Util.objectToString(arr[1]))
                    .lotNumberRsp(Util.objectToString(arr[2]))
                    .numbersOfDfls(Util.objectToLong(arr[3]))
                    .ratePer100Dfls(Util.objectToFloat(arr[4]))
                    .dflsSource(Util.objectToString(arr[6]))
                    .hatchingInspectionDate(Util.objectToString(arr[7]))
                    .raceName(Util.objectToString(arr[8]))
                    .fruitsId(Util.objectToString(arr[9]))
                    .nameAndAddressOfTheFarm(Util.objectToString(arr[10]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public ResponseEntity<?> getVerifiedDFLDetails(Long raceId, Long tscId, int pageNumber, int pageSize) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(List.class);
        List<ChowkiManagementResponse> responseList = new ArrayList<>();

        raceId = (raceId != null && raceId == 0) ? null : raceId;
        tscId = (tscId != null && tscId == 0) ? null : tscId;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> applicablePage =
                chowkiManagemenyRepository.getVerifiedDFLDetails(raceId, tscId, pageable);

        List<Object[]> applicableList = applicablePage.getContent();
        long totalRecords = applicablePage.getTotalElements();

        buildResponse(responseList, applicableList, pageNumber, pageSize);

        rw.setTotalRecords(totalRecords);
        rw.setContent(responseList);
        return ResponseEntity.ok(rw);
    }

    private static void buildResponse(List<ChowkiManagementResponse> responseList,
                                      List<Object[]> applicableList,
                                      int pageNumber, int pageSize) {
        int serialNumber = pageNumber * pageSize + 1;
        for (Object[] arr : applicableList) {
            ChowkiManagementResponse response = ChowkiManagementResponse.builder()
                    .serialNumber(serialNumber++)
                    .lotNumber(Util.objectToString(arr[0]))
                    .numbersOfDfls(Util.objectToLong(arr[1]))
                    .ratePer100Dfls(Util.objectToFloat(arr[2]))
                    .raceName(Util.objectToString(arr[3]))
                    .expectedHatchingDate(Util.objectToString(arr[4]))
                    .dateOfDisposal(Util.objectToString(arr[5]))
                    .dflsSource(Util.objectToString(arr[6]))
                    .nameAndAddressOfTheFarm(Util.objectToString(arr[7]))
                    .tscName(Util.objectToString(arr[8]))
                    .farmerName(Util.objectToString(arr[9]))
                    .fatherName(Util.objectToString(arr[10]))
                    .fruitsId(Util.objectToString(arr[11]))
                    .build();
            responseList.add(response);
        }
    }

    public FileInputStream getVerifiedDFLReport(Long raceId, Long tscId, int pageNumber, int pageSize) throws Exception {
        List<ChowkiManagementResponse> responseList = new ArrayList<>();

        raceId = (raceId != null && raceId == 0) ? null : raceId;
        tscId = (tscId != null && tscId == 0) ? null : tscId;

        Pageable pageable = null; // fetch all records
        Page<Object[]> applicablePage =
                chowkiManagemenyRepository.getVerifiedDFLDetails(raceId, tscId, pageable);

        buildResponse(responseList, applicablePage.getContent(), pageNumber, pageSize);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Verified DFL Report");

        // Header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Sl.No");
        headerRow.createCell(1).setCellValue("Fruits ID");
        headerRow.createCell(2).setCellValue("Farmer Name");
        headerRow.createCell(3).setCellValue("Father Name");
        headerRow.createCell(4).setCellValue("Lot Number");// new column
        headerRow.createCell(5).setCellValue("Number of DFLs Disposed");
        headerRow.createCell(6).setCellValue("Rate per 100 DFLs Price");
        headerRow.createCell(7).setCellValue("Race Name");
        headerRow.createCell(8).setCellValue("Expected Date of Hatching");
        headerRow.createCell(9).setCellValue("Date of Disposal");
        headerRow.createCell(10).setCellValue("Source of DFLs");
        headerRow.createCell(11).setCellValue("Name and Address of the Farm");
        headerRow.createCell(12).setCellValue("TSC");

// Data rows
        int dataRow = 1;
        for (ChowkiManagementResponse c : responseList) {
            Row row = sheet.createRow(dataRow++);
            row.createCell(0).setCellValue(c.getSerialNumber());
            row.createCell(1).setCellValue(c.getFruitsId());
            row.createCell(2).setCellValue(c.getFarmerName());
            row.createCell(3).setCellValue(c.getFatherName());
            row.createCell(4).setCellValue(c.getLotNumber());// new column
            row.createCell(5).setCellValue(c.getNumbersOfDfls());
            row.createCell(6).setCellValue(c.getRatePer100Dfls());
            row.createCell(7).setCellValue(c.getRaceName());
            row.createCell(8).setCellValue(c.getExpectedHatchingDate());
            row.createCell(9).setCellValue(c.getDateOfDisposal());
            row.createCell(10).setCellValue(c.getDflsSource());
            row.createCell(11).setCellValue(c.getNameAndAddressOfTheFarm());
            row.createCell(12).setCellValue(c.getTscName());
        }

// Auto-size all 13 columns now
        for (int col = 0; col <= 12; col++) {
            sheet.autoSizeColumn(col, true);
        }



        for (int col = 0; col <= 9; col++) {
            sheet.autoSizeColumn(col, true);
        }

        String userHome = System.getProperty("user.home");
        String directoryPath = Paths.get(userHome, "Downloads").toString();
        Files.createDirectories(Paths.get(directoryPath));
        Path filePath = Paths.get(directoryPath, "verified_dfl_report" + Util.getISTLocalDate() + ".xlsx");

        FileOutputStream fileOut = new FileOutputStream(filePath.toString());
        FileInputStream fileIn = new FileInputStream(filePath.toString());
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        return fileIn;
    }

}
