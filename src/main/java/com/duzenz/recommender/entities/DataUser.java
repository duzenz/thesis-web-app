package com.duzenz.recommender.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lastfm_users")
public class DataUser 
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(nullable=false, unique=true, name = "user_id")
	private String userId;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name="age")
	private int age;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "registered")
	private String registered;

	@Override
	public String toString() {
		return "DataUser [id=" + id + ", userId=" + userId + ", gender="
				+ gender + ", age=" + age + ", country=" + country
				+ ", registered=" + registered + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegistered() {
		return registered;
	}

	public void setRegistered(String registered) {
		this.registered = registered;
	}

}
