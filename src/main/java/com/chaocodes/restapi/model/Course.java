package com.chaocodes.restapi.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.chaocodes.restapi.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@ApiObject
public class Course
{
	@Id
	@GeneratedValue
	@ApiObjectField(description = "The unique id of the course")
	private Long id;

	@NotEmpty(message = "Name must not be empty.")
	@Column(nullable = false)
	@ApiObjectField(description = "The name of the course", required = true)
	private String name;

	@NotEmpty(message = "Description must not be empty.")
	@Column(nullable = false)
	@ApiObjectField(description = "The description of the course", required = true)
	private String description;

	@Column(name = "created_at", nullable = false)
	@JsonIgnore
	@ApiObjectField(description = "When the course was created")
	private Timestamp createdAt = TimeUtil.getTimestampNow();

	@Column(name = "updated_at", nullable = false)
	@JsonIgnore
	@ApiObjectField(description = "When the course was last updated")
	private Timestamp updatedAt = TimeUtil.getTimestampNow();

	protected Course() {}

	public Course(String name, String description) {
		this.name = name;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
