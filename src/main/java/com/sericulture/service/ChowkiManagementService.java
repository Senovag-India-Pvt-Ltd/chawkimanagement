package com.sericulture.service;

import com.sericulture.model.ChowkiManagement;
import com.sericulture.model.api.AddChowkiResponse;
import com.sericulture.model.api.ChowkiManagementRequest;
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



    public AddChowkiResponse insertData(ChowkiManagementRequest chowkiManagementRequest) {
        AddChowkiResponse addChowkiResponse=new AddChowkiResponse();
        ChowkiManagement chowkiManagement=new ChowkiManagement();
        try {
            chowkiManagement.setDflsSource(chowkiManagementRequest.getDflsSource());
            chowkiManagement.setDispatchDate(chowkiManagementRequest.getDispatchDate());
            chowkiManagement.setDistrict(chowkiManagementRequest.getDistrict());
            chowkiManagement.setFarmerName(chowkiManagementRequest.getFarmerName());
            chowkiManagement.setState(chowkiManagementRequest.getState());
            chowkiManagement.setFatherName(chowkiManagementRequest.getFatherName());
            chowkiManagement.setFruitsId(chowkiManagementRequest.getFruitsId());
            chowkiManagement.setLotNumberCrc(chowkiManagementRequest.getLotNumberCrc());
            chowkiManagement.setLotNumberRsp(chowkiManagementRequest.getLotNumberRsp());
            chowkiManagement.setNumbersOfDfls(chowkiManagementRequest.getNumbersOfDfls());
            chowkiManagement.setPrice(chowkiManagementRequest.getPrice());
            chowkiManagement.setRaceOfDfls(chowkiManagementRequest.getRaceOfDfls());
            chowkiManagement.setRatePer100Dfls(chowkiManagementRequest.getRatePer100Dfls());
            chowkiManagement.setSoldAfter1stOr2ndMould(chowkiManagementRequest.getSoldAfter1stOr2ndMould());
            chowkiManagement.setTsc(chowkiManagementRequest.getTsc());
            chowkiManagement.setVillage(chowkiManagementRequest.getVillage());
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


    public AddChowkiResponse updateData(ChowkiManagement chowkiManagement) {
        AddChowkiResponse addChowkiResponse=new AddChowkiResponse();
        try {
            chowkiManagemenyRepository.save(chowkiManagement);
            addChowkiResponse.setError(0);
            addChowkiResponse.setMessage("Data updated successfully!");
        }
        catch(Exception E){
            addChowkiResponse.setError(1);
            addChowkiResponse.setMessage("Something went wrong; please try again!");
            log.error("EXCEPTION : {}",E);
        }
        return addChowkiResponse;
    }

    public List<ChowkiManagement> findAll() {
        return chowkiManagemenyRepository.findAll();
    }

    public AddChowkiResponse deleteById(Integer id) {
        AddChowkiResponse addChowkiResponse=new AddChowkiResponse();
        if(chowkiManagemenyRepository.findById(id).isEmpty()){
            addChowkiResponse.setError(1);
            addChowkiResponse.setMessage("Wrong Chowki ID");
        }
        try {
            chowkiManagemenyRepository.deleteById(id);
            addChowkiResponse.setError(0);
            addChowkiResponse.setMessage("Data deleted successfully!");
        }
        catch (Exception E){
            addChowkiResponse.setError(1);
            addChowkiResponse.setMessage("Something went wrong; please try again!");
            log.error("EXCEPTION : {}",E);
        }
        return addChowkiResponse;
    }

    public Optional<ChowkiManagement> getById(Integer id) {
        return chowkiManagemenyRepository.findById(id);
    }
}
