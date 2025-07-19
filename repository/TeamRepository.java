package com.taskmanager.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanager.application.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
