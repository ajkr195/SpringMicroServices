package com.spring.boot.rocks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "appusers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "active")
	private boolean active;

	public AppUser(String name, String email, boolean active) {
		super();
		this.name = name;
		this.email = email;
		this.active = active;
	}


}
