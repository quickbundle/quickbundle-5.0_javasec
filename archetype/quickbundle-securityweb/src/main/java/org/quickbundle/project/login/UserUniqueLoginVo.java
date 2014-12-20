package org.quickbundle.project.login;

import java.sql.Timestamp;

/**
 * 
 */
public class UserUniqueLoginVo {
	public enum EnumSystem {
		SCM("轻量级"),
		NC("NC");
		private String title;
		EnumSystem(String title) {
			this.title = title;
		}
		public String getTitle() {
			return title;
		}
	}
	private String id;
	private String name;
	private String login_id;
	private String login_ip;
	private Timestamp login_date;
	private String session_id;
	private String description;
	private EnumSystem online_system;
	private EnumSystem from_system;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getLogin_ip() {
		return login_ip;
	}
	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}
	public Timestamp getLogin_date() {
		return login_date;
	}
	public void setLogin_date(Timestamp login_date) {
		this.login_date = login_date;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public EnumSystem getOnline_system() {
		return online_system;
	}
	public void setOnline_system(EnumSystem online_system) {
		this.online_system = online_system;
	}
	public EnumSystem getFrom_system() {
		return from_system;
	}
	public void setFrom_system(EnumSystem from_system) {
		this.from_system = from_system;
	}
	
	
}