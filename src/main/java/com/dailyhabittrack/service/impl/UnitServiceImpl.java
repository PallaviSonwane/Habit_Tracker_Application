package com.dailyhabittrack.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.dailyhabittrack.entity.Units;
import com.dailyhabittrack.exception.UnitException;
import com.dailyhabittrack.mapper.UnitMapper;
import com.dailyhabittrack.repository.UnitsRepository;
import com.dailyhabittrack.request.UnitRequest;
import com.dailyhabittrack.response.UnitResponse;
import com.dailyhabittrack.service.UnitService;
import com.dailyhabittrack.constant.enums.UnitResponseMessage;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {
    private static final Logger logger = LoggerFactory.getLogger(UnitServiceImpl.class);

    private final UnitsRepository unitsRepository;

    public UnitServiceImpl(UnitsRepository unitsRepository) {
        this.unitsRepository = unitsRepository;
    }

    @Override
    public UnitResponse createUnit(UnitRequest unitRequest) {
        String workFlow = "UnitServiceImpl.createUnit";
        try {
            Units unit = UnitMapper.INSTANCE.unitRequestToUnit(unitRequest);
            Units createdUnit = unitsRepository.save(unit);
            return UnitMapper.INSTANCE.unitToUnitResponse(createdUnit);
        } catch (Exception ex) {
            throw new UnitException(
                    UnitResponseMessage.FAILED_TO_SAVE_UNIT.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, workFlow);
        }
    }

    @Override
    public List<UnitResponse> getAllUnits() {
        String workFlow = "UnitServiceImpl.getAllUnits";
        List<Units> unitList = unitsRepository.findAll();
        if (unitList.isEmpty()) {
            throw new UnitException(
                    UnitResponseMessage.UNIT_NOT_EXISTS.getMessage(),
                    HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT, workFlow);
        }
        return UnitMapper.INSTANCE.unitListToUnitResponseList(unitList);
    }

    @Override
    public UnitResponse getUnit(Long unitId) {
        String workFlow = "UnitServiceImpl.getUnit";
        Units unit = unitsRepository.findByUnitId(unitId);
        if (unit == null) {
            throw new UnitException(
                    UnitResponseMessage.UNIT_NOT_FOUND.getMessage(unitId),
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow);
        }
        return UnitMapper.INSTANCE.unitToUnitResponse(unit);
    }

    @Override
    public void updateUnit(UnitRequest updatedUnitRequest, Long unitId) {
        String workFlow = "UnitServiceImpl.updateUnit";
        UnitResponse existingUnit = getUnit(unitId);
        if (existingUnit == null) {
            throw new UnitException(
                    UnitResponseMessage.UNIT_NOT_FOUND.getMessage(unitId),
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow);
        }

        Units updateUnit = UnitMapper.INSTANCE.unitRequestToUnit(updatedUnitRequest);

        Units updatedUnit = unitsRepository.save(updateUnit);
        logger.info("Updated unit details: {}", updatedUnit);
    }

    @Override
    public void deleteUnit(Long unitId) {
        String workFlow = "UnitServiceImpl.deleteUnit";
        Units existingUnit = unitsRepository.findByUnitId(unitId);
        if (existingUnit == null) {
            throw new UnitException(
                    UnitResponseMessage.UNIT_NOT_FOUND.getMessage(unitId),
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow);
        }
        unitsRepository.deleteById(unitId);
    }
}
