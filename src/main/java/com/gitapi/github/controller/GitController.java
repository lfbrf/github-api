package com.gitapi.github.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gitapi.github.api.Http;
import com.gitapi.github.entities.Github;
import com.gitapi.github.entities.GithubApi;
import com.gitapi.github.repositories.GithubRepository;
import com.gitapi.github.services.GithubService;



@Controller

public class GitController {

	@Autowired
	private GithubService githubService;

	@Autowired
	private GithubRepository githubRepository;


	public GithubService getGithubService() {
		return githubService;
	}




	@Autowired
	public void setGithubService(GithubService githubService) {
		this.githubService = githubService;
	}





	/**
	 * List all git repos.
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/", "/search", "/save", "/listarapi"}, method = RequestMethod.GET)
	public String list(Model model) {

		ModelAndView modelAndView = new ModelAndView("repos");
		modelAndView.addObject("githubService", githubService);
		return "repos";
	}


	@RequestMapping(value = {"/listarbanco"}, method = RequestMethod.GET)
	public ModelAndView listArBanco(Model model,  @RequestParam(value="query", required = false) String query, @RequestParam(value="language", required = false)  String language,
			@RequestParam(value="retorno", required = false) String retorno) {
		ModelAndView modelAndView = new ModelAndView("repos");
		if (retorno!=null) {
			String message = "";
			if (retorno.equals("1") || retorno == "1")
				message = "Repositório salvo com sucesso!";
			else if (retorno.equals("-1") || retorno == "-1"  || retorno.equals("2") || retorno == "2" )
				message = "Não foi possível salvar o repositório, favor entrar em contato com o administrador";
			else if (retorno.equals("0") || retorno == "0")
				message = "Não foi possível salvar o repositório, ja existe um registro salvo para o mesmo!";

			else if (retorno.equals("10") || retorno == "10")
				message = "Repositório deletado com sucesso";
			else if (retorno.equals("20") || retorno == "20")
				message = "Não foi possível deletar o repositório, favor entrar em contato com o administrador";

			modelAndView.addObject("retornoSave", message);
			//modelAndView.setViewName("redirect:listarbanco");
		}
		modelAndView.addObject("githubService", githubService);
		List<GithubApi> todos = new ArrayList<GithubApi>();
		todos = listAllSaved(query, language, 1);
		if (todos!=null && !(todos.isEmpty()))
			modelAndView.addObject("repos", todos);
		return modelAndView;
	}





	public List<GithubApi> setGithubRepos(String query, String language, boolean listaBanco, int currentPage) {
		List<GithubApi> github = new ArrayList<GithubApi>();	
		JSONObject object = null;
		try {

			object = chamadaHttp(query,  language, true, currentPage);
			JSONArray jArray;
			if (!listaBanco) {
				object = chamadaHttp(query,  language, true, currentPage);
				jArray = object.getJSONArray("items");
			}

			else {

				jArray = chamadaHttpBanco(query, language, true, currentPage);

			}	


			for (int i = 0; i < jArray.length(); i++) {
				GithubApi g = new GithubApi();
				JSONObject row = jArray.getJSONObject(i);
				JSONObject owner = (JSONObject) row.get("owner");

				if (!row.isNull("id"))
					g.setId(row.getInt("id"));
				if (!row.isNull("url"))
					g.setUrl(row.getString("url"));
				if (!row.isNull("name"))
					g.setName(row.getString("name"));
				if (!row.isNull("full_name"))
					g.setFullName(row.getString("full_name"));

				if (!row.isNull("description"))
					g.setDescription(row.getString("description"));
				if (!row.isNull("fork"))
					g.setFork(row.getBoolean("fork"));
				if (!row.isNull("created_at"))
					g.setCreatedAt(row.getString("created_at"));
				if (!row.isNull("updatedAt"))
					g.setUpdatedAt(row.getString("updated_at"));
				if (!row.isNull("pushed_at"))
					g.setPushedAt(row.getString("pushed_at"));
				if (!row.isNull("git_url"))
					g.setGitUrl(row.getString("git_url"));
				if (!row.isNull("ssh_url"))
					g.setSshUrl(row.getString("ssh_url"));
				if (!row.isNull("clone_url"))
					g.setCloneUrl(row.getString("clone_url"));
				if (!row.isNull("size"))
					g.setSize(row.getInt("size"));
				if (!row.isNull("language"))
					g.setLanguage(row.getString("language")); 
				if (!row.isNull("watchers_count"))
					g.setWatchersCount(row.getInt("watchers_count"));
				if (!row.isNull("forks"))
					g.setForks(row.getInt("forks"));
				if (!row.isNull("open_issues"))
					g.setOpenIssues(row.getInt("open_issues"));
				if (!row.isNull("watchers"))
					g.setWatchers(row.getInt("watchers"));
				if (!row.isNull("default_branch"))
					g.setDefaultBranch(row.getString("default_branch"));
				if (!row.isNull("score"))
					g.setScore(row.getFloat("score"));



				if (!owner.isNull("login"))
					g.setLogin(owner.getString("login"));
				if (!owner.isNull("owner_id"))
					g.setOwnerId(owner.getInt("owner_id"));
				if (!owner.isNull("avatar_url"))
					g.setAvatarUrl(owner.getString("avatar_url"));
				if (!owner.isNull("type"))
					g.setType(owner.getString("type"));


				github.add(g);

			}

			return github;

		} catch (IOException e) {

			e.printStackTrace();
		}
		return github;
	}

	public List<GithubApi> listAllSaved(String query, String language, int currentPage) {
		List<GithubApi> gitsRposAux = new ArrayList<GithubApi>();
		if (query == null || query.equals("") || query == "") {
			Iterable<Github> z = githubService.listallRepos();

			for(Github g: z){
				int idSalvo = g.getIdGithub();
				String aux = idSalvo + "";
				List<GithubApi> gits = setGithubRepos(aux, language, true, currentPage);
				if (!gits.isEmpty() && idSalvo == gits.get(0).getId())
					gitsRposAux.add(gits.get(0));
			}
		}
		else {
			/* Comentado forma antiga, consulta fica muito lenta para buscas mais genericas. 
			Iterable<Github> z = githubService.listallRepos();
			
			for(Github g: z){
				while(adicionarPagina(query, language, currentPage)) {
					List<GithubApi> gits = setGithubRepos(query, language, false, currentPage);
					for (int i =0; i<gits.size(); i++) {
						if (g.getIdGithub() == gits.get(i).getId())	{
							gitsRposAux.add(gits.get(i));

						}
					}
					currentPage ++ ;
				}
				
			}*/
			System.out.println("QUERY ANTES DO ERRO");
			System.out.println(query);
			List <Github> git = null;
			if (language == null || language.equals("") ||  language == "" || language.equals("Todas") || language == "Todas")
				git = githubRepository.findBySearchReposName(query);
			else
				git = githubRepository.findBySearchReposNameLanguage(query, language);
			
			
			for (int i = 0; i< git.size(); i++) {
				List<GithubApi> gits = setGithubRepos(git.get(i).getIdGithub() + "", language, true, currentPage);
				gitsRposAux.add(gits.get(0));
			}
			
		}
		return gitsRposAux;
	}
	@RequestMapping("/search")
	public ModelAndView search(Model model,  @RequestParam String query, @RequestParam String language, String url, @RequestParam int currentPage) {
		ModelAndView modelAndView = new ModelAndView("repos");
		modelAndView.addObject("query", query);
		modelAndView.addObject("githubService", githubService);
		modelAndView.addObject("language", language);
		if (url.startsWith("http://localhost:8080/listarbanco")||
				url.startsWith("https://localhost:8080/listarbanco")||
				url.startsWith("localhost:8080/listarbanco")) {
			modelAndView.setViewName("redirect:listarbanco");
			try
			{
				List<GithubApi> gitsAllrepos = new ArrayList<GithubApi>();
			}

			catch (NumberFormatException nfe)
			{
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}

		}  
		else {
			
			modelAndView.addObject("repos", setGithubRepos(query, language, false, currentPage));
			if (adicionarPagina(query, language, currentPage))
				currentPage ++;
			else
				currentPage = -1;
			modelAndView.addObject("valorPagina", currentPage);
		}
		
		//modelAndView.setViewName("/search?retorno=");
		return modelAndView;
	} 

	
	public boolean adicionarPagina(String query, String language, int currentPage) {
		
		List<GithubApi> s = setGithubRepos(query, language, false, currentPage + 1);
		if (!(s.isEmpty()))
			return true;
		return false;
		
	}



	@RequestMapping("/save")
	public ModelAndView save(@RequestParam int idSalvar, @RequestParam int currentPageSave) {
		Github github = new Github(); 
		github.setIdGithub(idSalvar);
		String message = "";
		ModelAndView modelAndView = new ModelAndView("repos");
		String aux = idSalvar  + "";
		List<GithubApi> gits = setGithubRepos(aux, "", true, currentPageSave);
		
		if (gits == null || gits.isEmpty()) {
			
			modelAndView.setViewName("redirect:/listarbanco?retorno=2" );
			
			return modelAndView;
		}
			
		modelAndView.addObject("githubService", githubService);
		if (githubService.checkPersistRepo(idSalvar)) {
			modelAndView.setViewName("redirect:/listarbanco?retorno=0");
			return modelAndView;
		}
		github.setRepoName(gits.get(0).getFullName());
		github.setLanguage(gits.get(0).getLanguage());
		Github p = githubService.saveGithub(github);
		if (p!=null)
			message = "1";
		else
			message = "-1";
		
		modelAndView.setViewName("redirect:/listarbanco?retorno=" + message);
		return modelAndView;
	}

	
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam int idDeletar) {
		ModelAndView modelAndView = new ModelAndView("repos");
		try {
			Github git = githubRepository.findBySearchTerm(idDeletar);
			if (git!=null) {
				githubService.deleteRepo(git.getId());
				modelAndView.addObject("githubService", githubService);
				modelAndView.setViewName("redirect:/listarbanco?retorno=10");
			}
			else
				modelAndView.setViewName("redirect:/listarbanco?retorno=20");
			
		}
		catch(Exception e) {
			modelAndView.setViewName("redirect:/listarbanco?retorno=20");
			throw e;
		}
		
		return modelAndView;
	}
	


	private static JSONObject chamadaHttp(String busca, String linguagem, boolean ordem, int currentPage) throws IOException {
		Http http = new Http();

		String retornoJson = (http.chamaUrl(busca, linguagem, ordem, false, currentPage) + " ]}");
		JSONObject objetoJson = new JSONObject(retornoJson);  
		objetoJson.isEmpty();
		return objetoJson;
	}

	private static JSONArray chamadaHttpBanco(String busca, String linguagem, boolean ordem, int currentPage) throws IOException {
		Http http = new Http();
		String retornoJson =  "[" + (http.chamaUrl(busca, linguagem, ordem, true, currentPage) + " ]}");
		JSONArray itemArray=new JSONArray(retornoJson);  
		return itemArray;
	}





}
