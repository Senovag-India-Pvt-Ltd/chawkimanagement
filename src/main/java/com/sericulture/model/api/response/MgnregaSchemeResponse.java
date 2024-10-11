package com.sericulture.model.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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
