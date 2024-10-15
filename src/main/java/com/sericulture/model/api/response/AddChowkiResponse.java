package com.sericulture.model.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddChowkiResponse {
    private int error;
    private Long farmerMulberryExtensionId;
    private Long cropInspectionId;
    private Long fitnessCertificateId;
    private String message;
    private String receiptNo;
}
