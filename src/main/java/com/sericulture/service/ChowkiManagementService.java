package com.sericulture.service;

import com.sericulture.helper.Util;
import com.sericulture.model.api.ChowkiManagementByIdDTO;
import com.sericulture.model.api.requests.UpdateChowkiRequest;
import com.sericulture.model.api.response.AddChowkiResponse;
import com.sericulture.model.entity.ChowkiManagement;
import com.sericulture.model.api.requests.AddChowkiRequest;
import com.sericulture.model.api.response.CommonChowkiResponse;
import com.sericulture.model.api.ChowkiManagementResponse;
import com.sericulture.model.entity.District;
import com.sericulture.repository.ChowkiManagementRepository;
import com.sericulture.repository.DistrictRepository;
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

    @Autowired
    DistrictRepository districtRepository;

    public AddChowkiResponse insertData(AddChowkiRequest addChowkiRequest) {
        AddChowkiResponse addChowkiResponse =new AddChowkiResponse();
        ChowkiManagement chowkiManagement=new ChowkiManagement();
        try {
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

    public List<ChowkiManagementResponse> findAll() {
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
}
