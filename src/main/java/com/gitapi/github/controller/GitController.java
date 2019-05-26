package com.gitapi.github.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public ModelAndView listArBanco(Model model,  @RequestParam(value="query", required = false) String query, @RequestParam(value="language", required = false)  String language ) {
		ModelAndView modelAndView = new ModelAndView("repos");
		//List<GithubApi> gitsAllrepos = new ArrayList<GithubApi>();
		modelAndView.addObject("githubService", githubService);
		List<GithubApi> todos = new ArrayList<GithubApi>();
		System.out.println("listArBanco listArBanco listArBanco");
		if (query!= null && query!="" && !query.equals("")) {
			todos = listAllSaved(query, language);

		}

		else {
			System.out.println("ELSEEEEEEEEEEEEEEEE");
			Iterable<Github> z = githubService.listallRepos();
			if (z!=null) 
				for(Github s: z){
					String st = s.getIdGithub() + "";
					List<GithubApi> m = setGithubRepos(st, "", true);
					System.out.println("UP UP");
					if (m!=null && !m.isEmpty()) {
						System.out.println("AQUI S");
						todos.add(m.get(0));
					}
						
				}
		}
		if (todos!=null && !todos.isEmpty())
			modelAndView.addObject("repos", todos);
		return modelAndView;
	}





	public List<GithubApi> setGithubRepos(String query, String language, boolean listaBanco) {
		List<GithubApi> github = new ArrayList<GithubApi>();	
		JSONObject object = null;
		try {

			object = chamadaHttp(query,  language, true);
			JSONArray jArray;
			if (!listaBanco) {
				object = chamadaHttp(query,  language, true);
				jArray = object.getJSONArray("items");
			}

			else {

				jArray = chamadaHttpBanco(query, language, true);

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

	public List<GithubApi> listAllSaved(String query, String language) {
		List<GithubApi> gitsRposAux = new ArrayList<GithubApi>();
		if (query.equals("") || query == "") {
			Iterable<Github> z = githubService.listallRepos();

			for(Github g: z){
				int idSalvo = g.getIdGithub();
				String aux = idSalvo + "";
				List<GithubApi> gits = setGithubRepos(aux, language, true);
				if (!gits.isEmpty() && idSalvo == gits.get(0).getId())
					gitsRposAux.add(gits.get(0));
			}
		}
		else {
			Iterable<Github> z = githubService.listallRepos();
			List<GithubApi> gits = setGithubRepos(query, language, false);
			System.out.println("IF ELSE CORRETO");
			for(Github g: z){
				for (int i =0; i<gits.size(); i++) {
					if (g.getIdGithub() == gits.get(i).getId())	{
						gitsRposAux.add(gits.get(i));

					}

				}
			}
		}
		return gitsRposAux;
	}
	@RequestMapping("/search")
	public ModelAndView search(Model model,  @RequestParam String query, @RequestParam String language, String url) {
		System.out.println(url);
		ModelAndView modelAndView = new ModelAndView("repos");
		modelAndView.addObject("query", query);
		modelAndView.addObject("githubService", githubService);
		modelAndView.addObject("language", language);
		System.out.println("Minha URL!!!!!");
		System.out.println(url);
		if (url.startsWith("http://localhost:8080/listarbanco")) {
			//githubService.searchById(query);
			System.out.println("ATE AQI");
			modelAndView.setViewName("redirect:listarbanco");

			try
			{
				List<GithubApi> gitsAllrepos = new ArrayList<GithubApi>();

				//gitsAllrepos = listAllSaved(query, language);

				//System.out.println("APOS RETORNO");


				//modelAndView.addObject("repos", gitsAllrepos);


			}

			catch (NumberFormatException nfe)
			{
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}

		}  
		else {
			modelAndView.addObject("repos", setGithubRepos(query, language, false));
		}


		return modelAndView;
	} 




	@RequestMapping("/save")
	public ModelAndView save(@RequestParam int idSalvar) {
		Github github = new Github(); 
		github.setIdGithub(idSalvar);
		githubService.saveGithub(github);

		ModelAndView modelAndView = new ModelAndView("repos");
		modelAndView.addObject("githubService", githubService);
		modelAndView.setViewName("redirect:/listarbanco");
		return modelAndView;
	}




	public static List<String> getValuesForGivenKey(String jsonArrayStr, String key) {
		JSONArray jsonArray = new JSONArray(jsonArrayStr);
		return IntStream.range(0, jsonArray.length())
				.mapToObj(index -> ((JSONObject)jsonArray.get(index)).optString(key))
				.collect(Collectors.toList());
	}


	private static JSONObject chamadaHttp(String busca, String linguagem, boolean ordem) throws IOException {
		Http http = new Http();
		String retornoJson = (http.chamaUrl(busca, linguagem, ordem, false) + " ]}");
		JSONObject objetoJson = new JSONObject(retornoJson);  
		return objetoJson;
	}

	private static JSONArray chamadaHttpBanco(String busca, String linguagem, boolean ordem) throws IOException {
		Http http = new Http();
		String retornoJson =  "[" + (http.chamaUrl(busca, linguagem, ordem, true) + " ]}");
		JSONArray itemArray=new JSONArray(retornoJson);  
		return itemArray;
	}





}
