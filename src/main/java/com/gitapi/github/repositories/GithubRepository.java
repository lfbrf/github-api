package com.gitapi.github.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gitapi.github.entities.Github;
import com.gitapi.github.entities.GithubApi;

public interface GithubRepository extends CrudRepository<Github, Integer> {

	
	

	@Query(value = "SELECT * FROM github WHERE id_github = ?1", nativeQuery = true) 
    Github findBySearchTerm(@Param("searchTerm") int searchTerm);
	
	@Query(value = "SELECT * FROM github WHERE repo_name like (%?1%)", nativeQuery = true) 
	List<Github> findBySearchReposName(@Param("searchTerm") String searchTerm);
	
	
	@Query(value = "SELECT * FROM github WHERE repo_name LIKE (%?1%) AND language LIKE (%?2%)", nativeQuery = true) 
	List<Github> findBySearchReposNameLanguage(@Param("reponame") String reponame, @Param("language") String language);
	 
	
	
}
