package com.dailyhabittrack.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.dailyhabittrack.entity.Habit;
import com.dailyhabittrack.request.HabitRequest;
import com.dailyhabittrack.response.HabitResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HabitMapper {
    HabitMapper INSTANCE = Mappers.getMapper(HabitMapper.class);

    Habit habitRequestToHabit(HabitRequest request);

    List<HabitResponse> habitListToHabitResponseList(List<Habit> habitList);

    HabitResponse habitToHabitResponse(Habit data);
}
