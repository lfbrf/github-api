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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gitapi.github.api.Http;
import com.gitapi.github.entities.Github;
import com.gitapi.github.entities.GithubApi;
import com.gitapi.github.services.GithubService;



@Controller
public class GitController {

	private GithubService githubService;

    
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
    @RequestMapping(value = {"/", "/search", "/save"}, method = RequestMethod.GET)
    public String list(Model model) {
  
        System.out.println("Returning rpoducts:");
        return "repos";
    }
 
  

    
   
 public List<GithubApi> setGithubRepos(String query, JSONObject object) {
	 List<GithubApi> github = new ArrayList<GithubApi>();	
	 try {
			
			object = chamadaHttp(query, "java", true);
			Object urls = object.get("items"); 
	        JSONArray jArray = object.getJSONArray("items");
	        
	        //String url = (String) object.get("url");
	        //System.out.println(url);
	        System.out.println(urls);
	         
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
    
    @RequestMapping("/search")
    public ModelAndView search(@RequestParam String query) {
    	System.out.println("Iniciio");
        System.out.println("AQUI!!" + query);
        ModelAndView modelAndView = new ModelAndView("repos");
        modelAndView.addObject("message", "Baeldung");
        JSONObject object = null;
        String[] continents = {
                "Africa", "Antarctica", "Asia", "Australia", 
                "Europe", "North America", "Sourth America"
              };
               
        modelAndView.addObject("continents", continents);
        modelAndView.addObject("repos", setGithubRepos(query, object));
		
        return modelAndView;
    }
    
    
    
   
    @RequestMapping("/save")
    public ModelAndView save(@RequestParam int idSalvar) {
    	System.out.println("Iniciio");
        System.out.println("AQUI!!" + idSalvar);
        Github github = new Github();
        github.setIdGithub(idSalvar);
        githubService.saveGithub(github);
      
        ModelAndView modelAndView = new ModelAndView("repos");
        modelAndView.addObject("message", "Baeldung");
        JSONObject object = null;
        
		
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
        String retornoJson = (http.chamaUrl(busca, linguagem, ordem) + " ]}");
        JSONObject objetoJson = new JSONObject(retornoJson);  
        return objetoJson;
    }

   

  

}
