package com.sericulture.model;


import com.sericulture.model.api.requests.AddChowkiRequest;
import com.sericulture.model.entity.CropInspection;
import com.sericulture.model.entity.FarmerMulberryExtension;
import com.sericulture.model.entity.FitnessCertificate;
import com.sericulture.model.entity.SaleAndDisposalOfDfls;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class Mapper {

    @Autowired
    ModelMapper mapper;

    /**
     * Maps cropInspectionEntity to Farmer Response Object
     * @param cropInspectionEntity
     * @param <T>
     */
    public <T> T cropInspectionEntityToObject(CropInspection cropInspectionEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, cropInspectionEntity);
        return (T) mapper.map(cropInspectionEntity, claaz);
    }

    /**
     * Maps mulberryExtensionEntity to Farmer Response Object
     * @param mulberryExtensionEntity
     * @param <T>
     */
    public <T> T mulberryExtensionEntityToObject(FarmerMulberryExtension mulberryExtensionEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, mulberryExtensionEntity);
        return (T) mapper.map(mulberryExtensionEntity, claaz);
    }

    /**
     * Maps fitnessCertificateEntity to Farmer Response Object
     * @param fitnessCertificateEntity
     * @param <T>
     */
    public <T> T fitnessCertificateEntityToObject(FitnessCertificate fitnessCertificateEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, fitnessCertificateEntity);
        return (T) mapper.map(fitnessCertificateEntity, claaz);
    }

    /**
     * Maps Sale and disposal of dfls Entity to Village Response Object
     * @param saleAndDisposalOfDflsEntity
     * @param <T>
     */
    public <T> T saleAndDisposalOfDflsEntityToObject(SaleAndDisposalOfDfls saleAndDisposalOfDflsEntity, Class<T> claaz) {
        log.info("Value of mapper is:",mapper, saleAndDisposalOfDflsEntity);
        return (T) mapper.map(saleAndDisposalOfDflsEntity, claaz);
    }

    /**
     * Maps Sale and disposal of dfls Object to Sale and disposal of dfls  Response Object
     * @param addChowkiRequest
     * @param <T>
     */
    public <T> T saleAndDisposalOfDflsObjectToEntity(AddChowkiRequest addChowkiRequest , Class<T> claaz) {
        log.info("Value of mapper is:",mapper, addChowkiRequest);
        return (T) mapper.map(addChowkiRequest, claaz);
    }


}
