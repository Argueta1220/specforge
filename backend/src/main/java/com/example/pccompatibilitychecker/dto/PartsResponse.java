package com.example.pccompatibilitychecker.dto;

import com.example.pccompatibilitychecker.model.Cpu;
import com.example.pccompatibilitychecker.model.Motherboard;
import com.example.pccompatibilitychecker.model.Psu;
import com.example.pccompatibilitychecker.model.Ram;

import java.util.List;

public class PartsResponse {
    private List<Cpu> cpus;
    private List<Motherboard> motherboards;
    private List<Ram> ramModules;
    private List<Psu> psus;

    public PartsResponse(List<Cpu> cpus, List<Motherboard> motherboards, List<Ram> ramModules, List<Psu> psus) {
        this.cpus = cpus;
        this.motherboards = motherboards;
        this.ramModules = ramModules;
        this.psus = psus;
    }

    public List<Cpu> getCpus() {
        return cpus;
    }

    public List<Motherboard> getMotherboards() {
        return motherboards;
    }

    public List<Ram> getRamModules() {
        return ramModules;
    }

    public List<Psu> getPsus() {
        return psus;
    }
}
