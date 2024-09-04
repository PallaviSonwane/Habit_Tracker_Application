package com.dailyhabittrack.mapper;

import com.dailyhabittrack.entity.HabitEntry;
import com.dailyhabittrack.request.HabitEntryRequest;
import com.dailyhabittrack.response.HabitEntryResponse;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HabitEntryMapper {
    HabitEntryMapper INSTANCE = Mappers.getMapper(HabitEntryMapper.class);

    HabitEntry habitEntryRequestToEntity(HabitEntryRequest request);

    HabitEntryResponse habitEntryToResponse(HabitEntry entity);

    List<HabitEntryResponse> habitEntryListToResponseList(List<HabitEntry> entries);
}
