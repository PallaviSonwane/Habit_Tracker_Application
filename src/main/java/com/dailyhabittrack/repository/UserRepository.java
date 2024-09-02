package com.dailyhabittrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dailyhabittrack.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(Long userId);

    @Modifying
    @Query(value = "UPDATE app_user SET deleted = true WHERE user_id = :userId", nativeQuery = true)
    void softDeleteByUserId(Long userId);

}
