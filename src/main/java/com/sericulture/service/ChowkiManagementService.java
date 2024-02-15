package com.sericulture.service;

import com.sericulture.helper.Util;
import com.sericulture.model.entity.ChowkiManagement;
import com.sericulture.model.api.AddChowkiRequest;
import com.sericulture.model.api.AddChowkiResponse;
import com.sericulture.model.api.ChowkiManagementResponse;
import com.sericulture.model.dto.ChowkiManagementDTO;
import com.sericulture.repository.ChowkiManagementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ChowkiManagementService {
    @Autowired
    ChowkiManagementRepository chowkiManagemenyRepository;

    public AddChowkiResponse insertData(AddChowkiRequest addChowkiRequest) {
        AddChowkiResponse addChowkiResponse=new AddChowkiResponse();
        ChowkiManagement chowkiManagement=new ChowkiManagement();
        try {
            float price=(addChowkiRequest.getRatePer100Dfls()*addChowkiRequest.getNumbersOfDfls())/100;
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
            chowkiManagemenyRepository.save(chowkiManagement);

            addChowkiResponse.setError(0);
            addChowkiResponse.setMessage("Data added successfully!");
        }
        catch(Exception E){
            addChowkiResponse.setError(1);
            addChowkiResponse.setMessage("Something went wrong; please try again!");
            log.error("EXCEPTION : {}",E);
        }
        return addChowkiResponse;
    }


    public AddChowkiResponse updateData(ChowkiManagementDTO updateChowkiRequest) {
        ChowkiManagement chowkiManagement = new ChowkiManagement();
        AddChowkiResponse addChowkiResponse=new AddChowkiResponse();
        Long userMasterId=Util.getUserId(Util.getTokenValues());
        if(chowkiManagemenyRepository.findByChowkiIdAndUserMasterId(updateChowkiRequest.getChowkiId(),userMasterId).isEmpty()){
            addChowkiResponse.setError(1);
            addChowkiResponse.setMessage("Invalid Chowki ID");
        }
        else {
            try {
                float price=(updateChowkiRequest.getRatePer100Dfls()*updateChowkiRequest.getNumbersOfDfls())/100;
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
                addChowkiResponse.setError(0);
                addChowkiResponse.setMessage("Data updated successfully!");
            } catch (Exception E) {
                addChowkiResponse.setError(1);
                addChowkiResponse.setMessage("Something went wrong; please try again!");
                log.error("EXCEPTION : {}", E);
            }
        }
        return addChowkiResponse;
    }

    public List<ChowkiManagementResponse> findAll() {
        return chowkiManagemenyRepository.getByUserMasterIdOrderByChowkiIdDesc(Util.getUserId(Util.getTokenValues()));
    }

    public AddChowkiResponse deleteById(Integer id) {
        AddChowkiResponse addChowkiResponse=new AddChowkiResponse();
        Long userMasterId=Util.getUserId(Util.getTokenValues());
        if(chowkiManagemenyRepository.findByChowkiIdAndUserMasterId(id,userMasterId).isEmpty()){
            addChowkiResponse.setError(1);
            addChowkiResponse.setMessage("Invalid Chowki ID");
        }
        else {
            try {
                chowkiManagemenyRepository.deleteById(id);
                addChowkiResponse.setError(0);
                addChowkiResponse.setMessage("Data deleted successfully!");
            } catch (Exception E) {
                addChowkiResponse.setError(1);
                addChowkiResponse.setMessage("Something went wrong; please try again!");
                log.error("EXCEPTION : {}", E);
            }
        }
        return addChowkiResponse;
    }

    public Optional<ChowkiManagementResponse> getById(Integer id) {
        Long userMasterId=Util.getUserId(Util.getTokenValues());
        Optional<ChowkiManagementResponse> chowkiManagementDTO=chowkiManagemenyRepository.findByChowkiIdAndUserMasterId(id,userMasterId);
        if(chowkiManagementDTO.isEmpty()){
            return Optional.empty();
        }
        return chowkiManagementDTO;
    }
}
