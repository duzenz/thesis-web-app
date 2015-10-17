package com.duzenz.recommender.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="casebase")
public class Cbr 
{
	@Id
	@Column(nullable=false, unique=true, name = "case_id")
	private String caseId;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name="age")
	private int age;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "solution_id")
	private String solutionId;
	
	@Column(name = "track_id")
	private String trackId;

	
	
	@Override
	public String toString() {
		return "Cbr [caseId=" + caseId + ", gender=" + gender + ", age=" + age
				+ ", country=" + country + ", solutionId=" + solutionId
				+ ", trackId=" + trackId + "]";
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
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

	public String getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(String solutionId) {
		this.solutionId = solutionId;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

}
