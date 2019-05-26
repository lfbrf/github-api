package com.gitapi.github;

import java.util.ArrayList;
import java.util.List;

import com.gitapi.github.entities.Github;

public class TesteSemRegras {

	public static void main(String[] args) {
		
	     List<Github> listaGithub = new ArrayList<Github>();
	     //System.out.println(listaGithub.get(0).getId()); //Tentando acessar arrayList 
	     //System.out.println(listaGithub.get(0).getIdGithub());  //que não foi inicializa
	     Github g = new Github();
	     g.setId(0);
	     g.setIdGithub(123);
	     listaGithub.add(g);
	     System.out.println(listaGithub.get(0).getId());  // Ok é necessário inicializar o mesmo
	     System.out.println(listaGithub.get(0).getIdGithub());
	    
	}

}
