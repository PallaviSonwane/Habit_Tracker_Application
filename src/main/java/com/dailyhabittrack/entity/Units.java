package com.dailyhabittrack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="units")
public class Units {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "unit_name", nullable = false, unique = true)
    private String unitName;

}
