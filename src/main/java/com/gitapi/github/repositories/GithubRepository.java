package com.gitapi.github.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gitapi.github.entities.Github;

public interface GithubRepository extends CrudRepository<Github, Integer> {

	
	

	@Query(value = "SELECT * FROM github WHERE id_github = ?1", nativeQuery = true) 
    Github findBySearchTerm(@Param("searchTerm") int searchTerm);
	 
	
	
}
