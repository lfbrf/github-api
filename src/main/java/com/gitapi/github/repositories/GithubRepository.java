package com.gitapi.github.repositories;


import org.springframework.data.repository.CrudRepository;

import com.gitapi.github.entities.Github;

public interface GithubRepository extends CrudRepository<Github, Integer> {

}
