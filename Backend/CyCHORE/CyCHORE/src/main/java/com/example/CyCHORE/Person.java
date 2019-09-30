package com.example.CyCHORE;

import javax.persistence.*;

@Entity
class Person {
	
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column
	String email;
	
	public Integer getId() { return id; }
	
	public String getUser() { return email; }

	public String toString() {
		return getUser();
	}




}