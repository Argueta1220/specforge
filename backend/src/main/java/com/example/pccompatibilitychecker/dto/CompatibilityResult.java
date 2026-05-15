package com.example.pccompatibilitychecker.dto;

import java.util.List;

public class CompatibilityResult {
    private boolean compatible;
    private List<String> errors;
    private List<String> warnings;
    private int estimatedWattage;

    public CompatibilityResult(boolean compatible, List<String> errors, List<String> warnings, int estimatedWattage) {
        this.compatible = compatible;
        this.errors = errors;
        this.warnings = warnings;
        this.estimatedWattage = estimatedWattage;
    }

    public boolean isCompatible() {
        return compatible;
    }

    public List<String> getErrors() {
        return errors;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public int getEstimatedWattage() {
        return estimatedWattage;
    }
}
