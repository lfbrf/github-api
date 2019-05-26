package com.gitapi.github;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.gitapi.github.entities.Github;
import com.gitapi.github.repositories.GithubRepository;
import com.gitapi.github.services.GithubService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JunitTestes {

	@Autowired
	private MockMvc mvc;

	@Test
	public void exampleTest() throws Exception {
		this.mvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(content().string("Uso da API do Github"));
	}
	
	
	
	
	 @Autowired
	    private GithubService githubService;

	    @Autowired
	    private GithubRepository repository;

	   
	    
	    @Test
	    public void persistOnlyUniqueRepoID() {
	    	Github github = new Github();
	    	github.setIdGithub(123);
	    	Github g = null;
	        
	        Github g2 = null;
	        try {
	        	g = githubService.saveGithub(github);
	        	 g2 = githubService.saveGithub(github);
	        }
	        catch (Exception e){
	        	
	        }
	        
	        assertThat(g!=null && g2 == null) ;
	        
	
	    }
	    
	    @Test
	    public void findByGitReposShouldReturnRepo() {
	    	Github github = new Github();
	    	github.setIdGithub(12345);
	        this.githubService.saveGithub(github);
	        Github git = this.repository.findBySearchTerm(12345);
	        assertThat(git.getIdGithub() == 12345) ;
	    }
	    
	    
	    
	    @Test
		public void persistTest() throws Exception {
	    	Github github = new Github();
	    	github.setIdGithub(321);
	        Github g = this.githubService.saveGithub(github);
	        assertThat(g.getIdGithub() == 321) ;
		}
	    
	    @Test
		public void checkPersistIfRepoWasPersisted() throws Exception {
	    	Github github = new Github();
	    	github.setIdGithub(123123);
	        Github g = this.githubService.saveGithub(github);
	        boolean b = this.githubService.checkPersistRepo(g.getIdGithub());
	        assertThat(b) ;
		}
	    
	    
	    
	    @Test
		public void checkIfDeletedSuccess() throws Exception {
	    	Github github = new Github();
	    	github.setIdGithub(232232);
	        Github g = this.githubService.saveGithub(github);
	        githubService.deleteRepo(g.getId());
	        Github git = this.repository.findBySearchTerm(232232);
	        assertThat(git == null) ;
		}
	    
	    
	    
	    
	    

}