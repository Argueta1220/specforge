package com.example.pccompatibilitychecker.api;

import com.example.pccompatibilitychecker.dto.PartsResponse;
import com.example.pccompatibilitychecker.repository.PartCatalog;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/parts")
public class PartsController {
    private final PartCatalog partCatalog;

    public PartsController(PartCatalog partCatalog) {
        this.partCatalog = partCatalog;
    }

    @GetMapping
    public PartsResponse getParts() {
        return new PartsResponse(
                partCatalog.getCpus(),
                partCatalog.getMotherboards(),
                partCatalog.getRamModules(),
                partCatalog.getPsus()
        );
    }
}
