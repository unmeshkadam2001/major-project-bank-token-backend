package com.pratiti.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratiti.project.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
