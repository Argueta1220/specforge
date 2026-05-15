package com.example.pccompatibilitychecker.api;

import com.example.pccompatibilitychecker.dto.CompatibilityRequest;
import com.example.pccompatibilitychecker.dto.CompatibilityResult;
import com.example.pccompatibilitychecker.repository.PartCatalog;
import com.example.pccompatibilitychecker.service.CompatibilityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/compatibility")
public class CompatibilityController {
    private final PartCatalog partCatalog;
    private final CompatibilityService compatibilityService;

    public CompatibilityController(PartCatalog partCatalog, CompatibilityService compatibilityService) {
        this.partCatalog = partCatalog;
        this.compatibilityService = compatibilityService;
    }

    @PostMapping("/check")
    public CompatibilityResult checkCompatibility(@Valid @RequestBody CompatibilityRequest request) {
        return compatibilityService.check(
                partCatalog.findCpu(request.getCpuId()),
                partCatalog.findMotherboard(request.getMotherboardId()),
                partCatalog.findRam(request.getRamId()),
                partCatalog.findPsu(request.getPsuId())
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleMissingPart(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", exception.getMessage()));
    }
}
