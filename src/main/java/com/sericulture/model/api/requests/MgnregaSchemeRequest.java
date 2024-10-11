package com.sericulture.model.api.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MgnregaSchemeRequest {
    private Long mgnregaSchemeId;
    private String acresPlanted;
    private String spacingFollwedFeet;
    private String spacingProcuredNos;
    private String spacingFollowed;
    private String spacingProcured;
    private Long noOfCuttingPlanted;
    private Long noOfSuccessfullSamplings;
}
