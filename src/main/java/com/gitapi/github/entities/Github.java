package com.gitapi.github.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Github {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(unique=true, nullable=false) 
	private int idGithub;

	public int getIdGithub() {
		return idGithub;
	}

	public void setIdGithub(int idGithub) {
		this.idGithub = idGithub;
	}
	
}
