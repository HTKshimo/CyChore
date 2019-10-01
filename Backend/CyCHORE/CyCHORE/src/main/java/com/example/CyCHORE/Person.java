package com.example.CyCHORE;

import javax.persistence.*;

@Entity
class Person {
	
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column
	String email;

	@Column
	String password;
	
	public Integer getId() { return id; }
	
	public String getEmail() { return email; }

	public String getPassword() { return password; }

	public String toString() {
		return getEmail();
	}




}