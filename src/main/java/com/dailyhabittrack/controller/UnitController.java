package com.dailyhabittrack.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dailyhabittrack.request.UnitRequest;
import com.dailyhabittrack.response.UnitResponse;
import com.dailyhabittrack.service.UnitService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/units")
@CrossOrigin(origins = "*")
public class UnitController {
    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<UnitResponse> createUnit(@Valid @RequestBody UnitRequest unitRequest) {
        logger.info("Unit create request received: {}", unitRequest);
        UnitResponse creationResponse = unitService.createUnit(unitRequest);
        logger.info("Unit created: {}", creationResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(creationResponse);
    }

    @GetMapping
    public ResponseEntity<List<UnitResponse>> getAllUnits() {
        List<UnitResponse> unitList = unitService.getAllUnits();
        logger.info("Unit information fetched for all units: {}", unitList);
        return ResponseEntity.ok(unitList);
    }

    @GetMapping("/{unit-id}")
    public ResponseEntity<UnitResponse> getUnit(@PathVariable("unit-id") Long unitId) {
        logger.info("Fetching unit data information request received for unit id: {}", unitId);
        UnitResponse unitData = unitService.getUnit(unitId);
        logger.info("Unit information fetched for unit id: {}", unitId);
        return ResponseEntity.ok(unitData);
    }

    @PutMapping("/{unit-id}")
    public ResponseEntity<String> updateUnit(@Valid @PathVariable("unit-id") Long unitId,
                                              @RequestBody UnitRequest updatedUnit) {
        logger.info("Unit update request received for unit id {} with unit details: {}", unitId, updatedUnit);
        unitService.updateUnit(updatedUnit, unitId);
        logger.info("Unit updated for unit id: {}", unitId);
        return ResponseEntity.ok().body("Unit successfully updated.");
    }

    @DeleteMapping("/{unit-id}")
    public ResponseEntity<String> deleteUnit(@PathVariable("unit-id") Long unitId) {
        logger.info("Unit delete request received for unit id: {}", unitId);
        unitService.deleteUnit(unitId);
        logger.info("Unit deleted for unit id: {}", unitId);
        return ResponseEntity.ok().body("Unit successfully deleted.");
    }
}
