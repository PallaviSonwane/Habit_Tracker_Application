package com.dailyhabittrack.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import com.dailyhabittrack.entity.HabitEntry;
import com.dailyhabittrack.request.HabitEntryRequest;
import com.dailyhabittrack.response.HabitEntryResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HabitEntryMapper {
    HabitEntryMapper INSTANCE = Mappers.getMapper(HabitEntryMapper.class);

    HabitEntry habitEntryRequestToHabitEntry(HabitEntryRequest request);

    List<HabitEntryResponse> habitEntryListToHabitEntryResponseList(List<HabitEntry> habitEntryList);

    HabitEntryResponse habitEntryToHabitEntryResponse(HabitEntry habitEntry);
}
