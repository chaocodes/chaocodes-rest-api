package com.chaocodes.restapi.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.chaocodes.restapi.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@ApiObject
public class Language
{
	@Id
	@GeneratedValue
	@ApiObjectField(description = "The unique id of the language")
	private Long id;

	@NotEmpty(message = "Name must not be empty.")
	@Column(nullable = false)
	@ApiObjectField(description = "The name of the language", required = true)
	private String name;

	@Range(min=0, max=100, message = "Experience must be between 0 and 100.")
	@ApiObjectField(description = "The experience level of the language", required = false)
	private Integer experience = 0;

	@Column(name = "created_at", nullable = false)
	@JsonIgnore
	@ApiObjectField(description = "When the language was created")
	private Timestamp createdAt = TimeUtil.getTimestampNow();

	@Column(name = "updated_at", nullable = false)
	@JsonIgnore
	@ApiObjectField(description = "When the language was last updated")
	private Timestamp updatedAt = TimeUtil.getTimestampNow();

	protected Language() {}

	public Language(String name, int experience) {
		this.name = name;
		this.experience = experience;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	@JsonProperty
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	@JsonIgnore
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	@JsonIgnore
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
