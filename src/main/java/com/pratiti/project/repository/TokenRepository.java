package com.pratiti.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pratiti.project.entity.Token;
import com.pratiti.project.entity.Token.Status;

public interface TokenRepository extends JpaRepository<Token, Integer>{

	
	@Query("select t from Token t where t.status= ?1")
	public List<Token> fetchByStatus(Status status);
}
