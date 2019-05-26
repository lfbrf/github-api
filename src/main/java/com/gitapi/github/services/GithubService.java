package com.gitapi.github.services;


import com.gitapi.github.entities.Github;

public interface GithubService {
	
	 Github saveGithub(Github github);
	 Iterable<Github> listallRepos();
	 boolean checkPersistRepo(int id);
	 void deleteRepo(Integer id);
	 boolean listarBanco();
}
 