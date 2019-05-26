package com.gitapi.github;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.gitapi.github.controller.GitController;
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
public class MockTeste {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private GitController gitController;
	
	@After
	public void after() {
		
	}
	
	@Before
	public void setUp() {

		
		this.mockMvc = MockMvcBuilders.standaloneSetup(gitController).build();
	}
	
	@Test
	public void inicio() throws Exception {
		this.mvc.perform(get("/")).andExpect(status().isOk());
	}


	@Autowired
	private GithubService githubService;

	@Autowired
	private GithubRepository repository;


	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testLisTarApi() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/listarapi")).andExpect(MockMvcResultMatchers.status().isOk());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/search")).andExpect(MockMvcResultMatchers.status().isOk());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/save")).andExpect(MockMvcResultMatchers.status().isOk());
	
		
	}
	
	@Test
	public void testLisBanco() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/listarbanco")).andExpect(MockMvcResultMatchers.status().isOk());
	}
		
	@Test
	public void testListPersist() throws Exception {
		
		Github github = new Github();
		github.setIdGithub(157030492); // Id referente ao repositório utfcoin, para consulta https://api.github.com/repositories/157030492
		this.githubService.saveGithub(github);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/listarbanco?query=utfcoin&language=JavaScript")).andExpect(MockMvcResultMatchers.status().isOk());
		
	}

	
	@Test
	public void testPOSTSaveRepoSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/save")
				.param("idSalvar", "140123412") // id do meu tcc
				).andExpect(MockMvcResultMatchers.redirectedUrl("/listarbanco?retorno=1")); // retorno 1 indica sucesso ao persistir
	}
	
	
	@Test
	public void testPOSTSaveRepoFail() throws Exception {
		Github github = new Github();
		github.setIdGithub(188518472); // Id referente ao repositório github-api, para consulta https://api.github.com/repositories/188518472
		this.githubService.saveGithub(github);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/save")
				.param("idSalvar", "188518472") // deve retornar 0 pois ja foi salvo o repositório
				).andExpect(MockMvcResultMatchers.redirectedUrl("/listarbanco?retorno=0")); // retorno 1 indica falha ao persistir
	}
	
	
	@Test
	public void testSaveInexistentRepoIDApi() throws Exception {
		Github github = new Github();
		this.mockMvc.perform(MockMvcRequestBuilders.post("/save")
				.param("idSalvar", "99942") // deve retornar 2 pois  nao existe repositorio com esse id na API
				).andExpect(MockMvcResultMatchers.redirectedUrl("/listarbanco?retorno=2")); // retorno 2 indica falha ao persistir
	}
	
	
	
	
	@Test
	public void testPOSTDeletRepoSuccess() throws Exception {
		Github github = new Github();
		github.setIdGithub(99990104); // Id referente ao repositório web5, para consulta https://api.github.com/repositories/99990104
		this.githubService.saveGithub(github);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/delete")
				.param("idDeletar", "99990104") // deve retornar 10 pois foi deletado com sucesso
				).andExpect(MockMvcResultMatchers.redirectedUrl("/listarbanco?retorno=10")); // retorno 10 indica sucesso ao deletar repo
	}
	
	
	@Test
	public void testFailDeletInexistentRepoSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/delete")
				.param("idDeletar", "123456789") // deve retornar 20 pois não foi deletado com sucesso
				).andExpect(MockMvcResultMatchers.redirectedUrl("/listarbanco?retorno=20")); // retorno 20 indica falha ao deletar repo
	}
	
	
	public void testDeleteAlreadyDeletedRepo() throws Exception {
		Github github = new Github();
		github.setIdGithub(105583448); // Id referente ao repositório crudphp, para consulta https://api.github.com/repositories/105583448
		this.githubService.saveGithub(github);
		this.githubService.deleteRepo(github.getId());
		this.mockMvc.perform(MockMvcRequestBuilders.post("/delete")
				.param("idDeletar", "105583448") // deve retornar 20 pois não foi deletado com sucesso
				).andExpect(MockMvcResultMatchers.redirectedUrl("/listarbanco?retorno=20")); // retorno 20 indica falha ao deletar repo
	}


}