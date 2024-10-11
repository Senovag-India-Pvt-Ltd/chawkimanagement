package com.sericulture.model.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MgnregaSchemeResponse {
    private Long mgnregaSchemeId;
    private String acresPlanted;
    private String spacingFollwedFeet;
    private String spacingProcuredNos;
    private String spacingFollowed;
    private String spacingProcured;
    private Long noOfCuttingPlanted;
    private Long noOfSuccessfullSamplings;
}
