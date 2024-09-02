package com.dailyhabittrack.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import com.dailyhabittrack.constant.enums.HabitResponseMessage;
import com.dailyhabittrack.entity.Habit;
import com.dailyhabittrack.exception.HabitException;
import com.dailyhabittrack.mapper.HabitMapper;
import com.dailyhabittrack.repository.HabitRepository;
import com.dailyhabittrack.request.HabitRequest;
import com.dailyhabittrack.response.HabitResponse;
import com.dailyhabittrack.service.HabitService;

@Service
public class HabitServiceImpl implements HabitService {
    private static final Logger logger = LoggerFactory.getLogger(HabitServiceImpl.class);

    private final HabitRepository habitRepository;

    public HabitServiceImpl(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    @Override
    public HabitResponse createHabit(HabitRequest habitRequest) {
        String workFlow = "HabitServiceImpl.createHabit";
        try {
            Habit existingHabit = habitRepository.findByHabitName(habitRequest.getHabitName());
            if (existingHabit != null) {
                throw new HabitException(
                        HabitResponseMessage.HABIT_ALREADY_ADDED.getMessage(),
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT,
                        workFlow
                );
            }
            Habit habit = HabitMapper.INSTANCE.habitRequestToHabit(habitRequest);
            Habit createdHabit = habitRepository.save(habit);
            return HabitMapper.INSTANCE.habitToHabitResponse(createdHabit);
        } catch (HabitException ex) {
            throw ex; 
        } catch (Exception ex) {
            logger.error("Failed to save habit: {}", habitRequest, ex);
            throw new HabitException(
                    HabitResponseMessage.FAILED_TO_SAVE_HABIT.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    workFlow
            );
        }
    }
    

    @Override
    public List<HabitResponse> getAllHabits() {
        String workFlow = "HabitServiceImpl.getAllHabits";
        List<Habit> habitList = habitRepository.findAll();
        if (habitList.isEmpty()) {
            throw new HabitException(
                    HabitResponseMessage.HABIT_NOT_EXISTS.getMessage(),
                    HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT, workFlow);
        }
        return HabitMapper.INSTANCE.habitListToHabitResponseList(habitList);
    }

    @Override
    public HabitResponse getHabit(Long habitId) {
        String workFlow = "HabitServiceImpl.getHabit";
        Habit habit = habitRepository.findByHabitId(habitId);
        if (habit == null) {
            throw new HabitException(
                    HabitResponseMessage.HABIT_NOT_FOUND.getMessage(habitId),
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow);
        }
        return HabitMapper.INSTANCE.habitToHabitResponse(habit);
    }

    @Override
    public void updateHabit(HabitRequest updatedHabitRequest, Long habitId) {
        String workFlow = "HabitServiceImpl.updateHabit";
        Habit existingHabit = habitRepository.findByHabitId(habitId);
        if (existingHabit == null) {
            throw new HabitException(
                    HabitResponseMessage.HABIT_NOT_FOUND.getMessage(habitId),
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow);
        }

        Habit updatedHabit = HabitMapper.INSTANCE.habitRequestToHabit(updatedHabitRequest);
        updatedHabit.setHabitId(existingHabit.getHabitId());
        updatedHabit.setDate(existingHabit.getDate());

        habitRepository.save(updatedHabit);
        logger.info("Updated habit details: {}", updatedHabit);
    }

    @Override
    @Transactional
    public void deleteHabit(Long habitId) {
        String workFlow = "HabitServiceImpl.deleteHabit";
        Habit existingHabit = habitRepository.findByHabitId(habitId);
        if (existingHabit == null) {
            throw new HabitException(
                    HabitResponseMessage.HABIT_NOT_FOUND.getMessage(habitId),
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow);
        }
        habitRepository.deleteByHabitId(habitId);
    }

    @Override
    public List<HabitResponse> getHabitsByDate(LocalDate date) {
        String workFlow = "HabitServiceImpl.getHabitsByDate";
        List<Habit> habits = habitRepository.findByDateAfter(date);
        if (habits.isEmpty()) {
            throw new HabitException(
                    HabitResponseMessage.HABIT_NOT_EXISTS.getMessage(),
                    HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT, workFlow);
        }
        return HabitMapper.INSTANCE.habitListToHabitResponseList(habits);
    }

}
