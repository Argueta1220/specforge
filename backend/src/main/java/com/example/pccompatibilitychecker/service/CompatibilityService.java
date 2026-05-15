package com.example.pccompatibilitychecker.service;

import com.example.pccompatibilitychecker.dto.CompatibilityResult;
import com.example.pccompatibilitychecker.model.Cpu;
import com.example.pccompatibilitychecker.model.Motherboard;
import com.example.pccompatibilitychecker.model.Psu;
import com.example.pccompatibilitychecker.model.Ram;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompatibilityService {
    private static final double MAX_RECOMMENDED_PSU_LOAD = 0.80;
    private static final double WARNING_PSU_LOAD = 0.70;

    public CompatibilityResult check(Cpu cpu, Motherboard motherboard, Ram ram, Psu psu) {
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();

        if (!cpu.getSocket().equalsIgnoreCase(motherboard.getSocket())) {
            errors.add("CPU socket " + cpu.getSocket() + " does not match motherboard socket " + motherboard.getSocket() + ".");
        }

        if (!ram.getType().equalsIgnoreCase(motherboard.getRamType())) {
            errors.add("RAM type " + ram.getType() + " does not match motherboard RAM type " + motherboard.getRamType() + ".");
        }

        int estimatedWattage = estimateWattage(cpu, motherboard, ram);
        double maxRecommendedWattage = psu.getWattageWatts() * MAX_RECOMMENDED_PSU_LOAD;
        double warningWattage = psu.getWattageWatts() * WARNING_PSU_LOAD;

        if (estimatedWattage > maxRecommendedWattage) {
            errors.add("Estimated wattage " + estimatedWattage + "W exceeds 80% of the PSU capacity (" + Math.round(maxRecommendedWattage) + "W).");
        } else if (estimatedWattage > warningWattage) {
            warnings.add("Estimated wattage is above 70% of the PSU capacity. A higher wattage PSU may leave more upgrade room.");
        }

        return new CompatibilityResult(errors.isEmpty(), errors, warnings, estimatedWattage);
    }

    private int estimateWattage(Cpu cpu, Motherboard motherboard, Ram ram) {
        int systemOverheadWatts = 250;
        return cpu.getTdpWatts()
                + motherboard.getWattageWatts()
                + ram.getWattageWatts()
                + systemOverheadWatts;
    }
}
