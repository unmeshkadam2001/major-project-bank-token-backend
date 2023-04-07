package com.pratiti.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratiti.project.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	boolean existsByName(String username);

	Optional<Manager> findByName(String username);

}
