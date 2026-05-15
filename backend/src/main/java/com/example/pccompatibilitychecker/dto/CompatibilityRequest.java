package com.example.pccompatibilitychecker.dto;

import jakarta.validation.constraints.NotNull;

public class CompatibilityRequest {
    @NotNull
    private Long cpuId;

    @NotNull
    private Long motherboardId;

    @NotNull
    private Long ramId;

    @NotNull
    private Long psuId;

    public Long getCpuId() {
        return cpuId;
    }

    public void setCpuId(Long cpuId) {
        this.cpuId = cpuId;
    }

    public Long getMotherboardId() {
        return motherboardId;
    }

    public void setMotherboardId(Long motherboardId) {
        this.motherboardId = motherboardId;
    }

    public Long getRamId() {
        return ramId;
    }

    public void setRamId(Long ramId) {
        this.ramId = ramId;
    }

    public Long getPsuId() {
        return psuId;
    }

    public void setPsuId(Long psuId) {
        this.psuId = psuId;
    }
}
