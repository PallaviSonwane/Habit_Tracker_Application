package com.dailyhabittrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dailyhabittrack.entity.Units;

@Repository
public interface UnitsRepository extends JpaRepository<Units, Long> {
    Units findByUnitId(Long unitId);
}
