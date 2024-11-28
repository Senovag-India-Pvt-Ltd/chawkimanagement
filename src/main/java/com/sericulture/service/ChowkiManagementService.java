package com.sericulture.service;

import com.sericulture.helper.Util;
import com.sericulture.model.Mapper;
import com.sericulture.model.api.ChowkiManagementByIdDTO;
import com.sericulture.model.api.requests.UpdateChowkiRequest;
import com.sericulture.model.api.response.AddChowkiResponse;
import com.sericulture.model.entity.ChowkiManagement;
import com.sericulture.model.api.requests.AddChowkiRequest;
import com.sericulture.model.api.response.CommonChowkiResponse;
import com.sericulture.model.api.ChowkiManagementResponse;
import com.sericulture.model.entity.District;
import com.sericulture.model.entity.SaleAndDisposalOfDfls;
import com.sericulture.repository.ChowkiManagementRepository;
import com.sericulture.repository.DistrictRepository;
import com.sericulture.repository.SaleAndDisposalOfDflsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AddChowkiResponse insertSaleAndDisposalDFlDetails(AddChowkiRequest addChowkiRequest) {
        AddChowkiResponse addChowkiResponse = new AddChowkiResponse();
        SaleAndDisposalOfDfls saleAndDisposalOfDfls = mapper.saleAndDisposalOfDflsObjectToEntity(addChowkiRequest, SaleAndDisposalOfDfls.class);
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
        List<Object[]> chowkiDetails = chowkiManagemenyRepository.getSaleAndDisposalDetailsByFruitsId(fruitsId);
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

}
