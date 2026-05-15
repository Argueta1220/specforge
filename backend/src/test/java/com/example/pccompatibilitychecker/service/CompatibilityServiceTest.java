package com.example.pccompatibilitychecker.service;

import com.example.pccompatibilitychecker.dto.CompatibilityResult;
import com.example.pccompatibilitychecker.model.Cpu;
import com.example.pccompatibilitychecker.model.Motherboard;
import com.example.pccompatibilitychecker.model.Psu;
import com.example.pccompatibilitychecker.model.Ram;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompatibilityServiceTest {
    private final CompatibilityService compatibilityService = new CompatibilityService();

    @Test
    void compatibleWhenSocketRamTypeAndPsuLoadAreValid() {
        Cpu cpu = new Cpu(1L, "Intel Core i5", "LGA1700", 65);
        Motherboard motherboard = new Motherboard(1L, "B760 Board", "LGA1700", "DDR4", 50);
        Ram ram = new Ram(1L, "16GB DDR4", "DDR4", 16, 8);
        Psu psu = new Psu(1L, "600W PSU", 600);

        CompatibilityResult result = compatibilityService.check(cpu, motherboard, ram, psu);

        assertThat(result.isCompatible()).isTrue();
        assertThat(result.getErrors()).isEmpty();
        assertThat(result.getEstimatedWattage()).isEqualTo(373);
    }

    @Test
    void incompatibleWhenCpuSocketDoesNotMatchMotherboardSocket() {
        Cpu cpu = new Cpu(1L, "AMD Ryzen", "AM5", 65);
        Motherboard motherboard = new Motherboard(1L, "Intel Board", "LGA1700", "DDR5", 50);
        Ram ram = new Ram(1L, "16GB DDR5", "DDR5", 16, 8);
        Psu psu = new Psu(1L, "650W PSU", 650);

        CompatibilityResult result = compatibilityService.check(cpu, motherboard, ram, psu);

        assertThat(result.isCompatible()).isFalse();
        assertThat(result.getErrors()).anyMatch(error -> error.contains("CPU socket"));
    }

    @Test
    void incompatibleWhenRamTypeDoesNotMatchMotherboardRamType() {
        Cpu cpu = new Cpu(1L, "Intel Core i5", "LGA1700", 65);
        Motherboard motherboard = new Motherboard(1L, "DDR5 Board", "LGA1700", "DDR5", 50);
        Ram ram = new Ram(1L, "16GB DDR4", "DDR4", 16, 8);
        Psu psu = new Psu(1L, "650W PSU", 650);

        CompatibilityResult result = compatibilityService.check(cpu, motherboard, ram, psu);

        assertThat(result.isCompatible()).isFalse();
        assertThat(result.getErrors()).anyMatch(error -> error.contains("RAM type"));
    }

    @Test
    void incompatibleWhenEstimatedWattageExceedsEightyPercentOfPsuWattage() {
        Cpu cpu = new Cpu(1L, "High Power CPU", "AM5", 170);
        Motherboard motherboard = new Motherboard(1L, "AM5 Board", "AM5", "DDR5", 70);
        Ram ram = new Ram(1L, "32GB DDR5", "DDR5", 32, 14);
        Psu psu = new Psu(1L, "500W PSU", 500);

        CompatibilityResult result = compatibilityService.check(cpu, motherboard, ram, psu);

        assertThat(result.isCompatible()).isFalse();
        assertThat(result.getEstimatedWattage()).isEqualTo(504);
        assertThat(result.getErrors()).anyMatch(error -> error.contains("80%"));
    }

    @Test
    void warningWhenEstimatedWattageIsHighButStillUnderEightyPercent() {
        Cpu cpu = new Cpu(1L, "Mid Power CPU", "AM5", 100);
        Motherboard motherboard = new Motherboard(1L, "AM5 Board", "AM5", "DDR5", 60);
        Ram ram = new Ram(1L, "32GB DDR5", "DDR5", 32, 14);
        Psu psu = new Psu(1L, "600W PSU", 600);

        CompatibilityResult result = compatibilityService.check(cpu, motherboard, ram, psu);

        assertThat(result.isCompatible()).isTrue();
        assertThat(result.getWarnings()).isNotEmpty();
        assertThat(result.getEstimatedWattage()).isEqualTo(424);
    }
}
