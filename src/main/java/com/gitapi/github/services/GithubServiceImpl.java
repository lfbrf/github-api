package com.gitapi.github.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gitapi.github.entities.Github;
import com.gitapi.github.repositories.GithubRepository;

@Service
public class GithubServiceImpl implements GithubService {
	private GithubRepository githubRepository;
	@Autowired
    public void setGithubRepository(GithubRepository githubRepository) {
        this.githubRepository = githubRepository;
    }
	
	
	
	 @Override
	    public Github saveGithub(Github github) {
	        return githubRepository.save(github);
	    }
}
