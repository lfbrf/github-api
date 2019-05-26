package com.gitapi.github.services;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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



	@Override
	public Iterable<Github> listallRepos() {
		 return githubRepository.findAll();
	}



	@Override
	public boolean checkPersistRepo(int id) {
		
		Github github = githubRepository.findBySearchTerm(id);
		if (github!=null && github.getId()>-1)
			return true;
		return false;
	}



	@Override
	public void deleteRepo(Integer id) {
		githubRepository.delete(id);
	}



	@Override
	public boolean listarBanco() {
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
		builder.scheme("https");
		builder.replaceQueryParam("someBoolean", false);
		URI url = builder.build().toUri();
		String u = "" + url;
		if (u.startsWith("http://githubapijava.herokuapp.com/listarbanco") || u.startsWith("https://githubapijava.herokuapp.com/listarbanco") || u.startsWith("githubapijava.herokuapp.com/listarbanco"))
			return true;
		return false;
	}
	
}
