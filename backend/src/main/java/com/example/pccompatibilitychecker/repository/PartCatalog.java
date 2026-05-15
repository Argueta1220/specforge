package com.example.pccompatibilitychecker.repository;

import com.example.pccompatibilitychecker.model.Cpu;
import com.example.pccompatibilitychecker.model.Motherboard;
import com.example.pccompatibilitychecker.model.Psu;
import com.example.pccompatibilitychecker.model.Ram;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PartCatalog {
    private final List<Cpu> cpus = List.of(
            new Cpu(1L, "Intel Core i5-13400F", "LGA1700", 65),
            new Cpu(2L, "Intel Core i7-14700K", "LGA1700", 125),
            new Cpu(3L, "AMD Ryzen 5 7600", "AM5", 65),
            new Cpu(4L, "AMD Ryzen 7 5800X", "AM4", 105)
    );

    private final List<Motherboard> motherboards = List.of(
            new Motherboard(1L, "MSI PRO B760M-A DDR4", "LGA1700", "DDR4", 50),
            new Motherboard(2L, "ASUS Prime Z790-P WiFi", "LGA1700", "DDR5", 60),
            new Motherboard(3L, "Gigabyte B650 Eagle AX", "AM5", "DDR5", 55),
            new Motherboard(4L, "ASRock B550M Pro4", "AM4", "DDR4", 45)
    );

    private final List<Ram> ramModules = List.of(
            new Ram(1L, "Corsair Vengeance LPX 16GB", "DDR4", 16, 8),
            new Ram(2L, "G.Skill Ripjaws V 32GB", "DDR4", 32, 12),
            new Ram(3L, "Kingston Fury Beast 16GB", "DDR5", 16, 10),
            new Ram(4L, "Corsair Vengeance 32GB", "DDR5", 32, 14)
    );

    private final List<Psu> psus = List.of(
            new Psu(1L, "Corsair CX450M", 450),
            new Psu(2L, "EVGA 600 BA", 600),
            new Psu(3L, "Seasonic Focus GX-750", 750)
    );

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

    public Cpu findCpu(Long id) {
        return cpus.stream()
                .filter(cpu -> cpu.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("CPU not found: " + id));
    }

    public Motherboard findMotherboard(Long id) {
        return motherboards.stream()
                .filter(motherboard -> motherboard.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Motherboard not found: " + id));
    }

    public Ram findRam(Long id) {
        return ramModules.stream()
                .filter(ram -> ram.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("RAM not found: " + id));
    }

    public Psu findPsu(Long id) {
        return psus.stream()
                .filter(psu -> psu.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("PSU not found: " + id));
    }
}
