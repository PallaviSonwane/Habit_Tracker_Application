package com.dailyhabittrack.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "habits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habit {

    @Id
    @GeneratedValue
    private Long habitId; 

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false)
    private String habitName;

    @Column(name = "goal")
    private Integer goal;

    @NotNull
    @Size(max = 50)
    @Column(name = "unit", nullable = false)
    private String unitName;

    @NotNull
    @Size(max = 50)
    @Column(name = "frequency", nullable = false)
    private String frequency;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date; 
}
