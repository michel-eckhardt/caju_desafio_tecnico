package br.com.caju.auth.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionStatusEnum {
    APPROVED("00"),
    INSUFFICIENT_FUNDS("51"),
    ERROR("07");

    private final String code;

    TransactionStatusEnum(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public static TransactionStatusEnum fromCode(String code) {
        for (TransactionStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return ERROR;
    }
}