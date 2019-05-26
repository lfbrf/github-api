package com.gitapi.github.entities;
public class GithubApi {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isFork() {
		return fork;
	}
	public void setFork(boolean fork) {
		this.fork = fork;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getPushedAt() {
		return pushedAt;
	}
	public void setPushedAt(String pushedAt) {
		this.pushedAt = pushedAt;
	}
	public String getGitUrl() {
		return gitUrl;
	}
	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}
	public String getSshUrl() {
		return sshUrl;
	}
	public void setSshUrl(String sshUrl) {
		this.sshUrl = sshUrl;
	}
	public String getCloneUrl() {
		return cloneUrl;
	}
	public void setCloneUrl(String cloneUrl) {
		this.cloneUrl = cloneUrl;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getWatchersCount() {
		return watchersCount;
	}
	public void setWatchersCount(int watchersCount) {
		this.watchersCount = watchersCount;
	}
	public int getForks() {
		return forks;
	}
	public void setForks(int forks) {
		this.forks = forks;
	}
	public int getOpenIssues() {
		return openIssues;
	}
	public void setOpenIssues(int openIssues) {
		this.openIssues = openIssues;
	}
	public int getWatchers() {
		return watchers;
	}
	public void setWatchers(int watchers) {
		this.watchers = watchers;
	}
	public String getDefaultBranch() {
		return defaultBranch;
	}
	public void setDefaultBranch(String defaultBranch) {
		this.defaultBranch = defaultBranch;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String url;




	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String login;
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public int ownerId;
	public String avatarUrl;
	public String type;



	public String name;
	public String fullName;
	public String description;
	public boolean fork;
	public String createdAt;
	public String updatedAt;
	public String pushedAt;
	public String gitUrl;
	public String sshUrl;
	public String cloneUrl;
	public int size;
	public String language;
	public int watchersCount;
	public int forks;
	public int openIssues;
	public int watchers;
	public String defaultBranch;
	public float score;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	} 
	
	public int id;
}
