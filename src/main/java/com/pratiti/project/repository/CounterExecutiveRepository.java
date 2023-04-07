package com.pratiti.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import com.pratiti.project.entity.CounterExecutive;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CounterExecutiveRepository extends JpaRepository<CounterExecutive, Integer> {

	public  CounterExecutive findByUsername(String username);
	
	boolean existsByUsername(String username);

	@Query("select c from CounterExecutive c where c.counter.id=?1")
	Optional<CounterExecutive> findByCounterId(int id);

//	@Query("select c from CounterExecutive c where c.username=?1")
//	Optional<CounterExecutive> findCounterExecutiveByUsername(String username);

	@Query("select c from CounterExecutive c where c.counter.id = null")
	List<CounterExecutive> fetchAll();

	@Query("select c from CounterExecutive c where c.username = ?1")
	CounterExecutive fetchByUsername(String counterExecutiveName);

}
