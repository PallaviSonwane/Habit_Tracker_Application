package com.dailyhabittrack.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import com.dailyhabittrack.entity.Units;
import com.dailyhabittrack.request.UnitRequest;
import com.dailyhabittrack.response.UnitResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnitMapper {
    UnitMapper INSTANCE = Mappers.getMapper(UnitMapper.class);

    Units unitRequestToUnit(UnitRequest request);

    List<UnitResponse> unitListToUnitResponseList(List<Units> unitList);

    UnitResponse unitToUnitResponse(Units data);
}
