package com.dailyhabittrack.service;

import java.util.List;
import com.dailyhabittrack.request.UnitRequest;
import com.dailyhabittrack.response.UnitResponse;

public interface UnitService {

    UnitResponse createUnit(UnitRequest unitRequest);

    List<UnitResponse> getAllUnits();

    UnitResponse getUnit(Long unitId);

    void updateUnit(UnitRequest updatedUnitRequest, Long unitId);

    void deleteUnit(Long unitId);
}
