package com.example.universityapplication.datamodel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RoleData is an entity file to keep all the columns related to roles table
 * 
 * @author Nisha Athmanathan
 *
 */
@Entity
@Table(name = "roles")
public class RoleData {

	@Id
	private String name;
	private String role;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public RoleData(String name, String role) {
		super();
		this.name = name;
		this.role = role;
	}

	public RoleData() {
		super();
		// TODO Auto-generated constructor stub
	}

}
