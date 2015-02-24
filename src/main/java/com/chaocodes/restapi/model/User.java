package com.chaocodes.restapi.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.chaocodes.restapi.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@ApiObject
public class User
{
	@Id
	@GeneratedValue
	@ApiObjectField(description = "The unique id of the user")
	private Long id;

	@NotEmpty(message = "Email must not be empty.") @Email(message = "Email must be a valid email address.")
	@Column(nullable = false)
	@ApiObjectField(description = "The email of the user", required = true)
	private String email;

	@NotEmpty(message = "Username must not be empty.")
	@Column(nullable = false)
	@ApiObjectField(description = "The username of the user", required = true)
	private String username;

	@Column(nullable = false)
	@ApiObjectField(description = "The owner status of the user")
	private boolean owner = false;

	@Column(name = "created_at", nullable = false)
	@JsonIgnore
	@ApiObjectField(description = "When the user was created")
	private Timestamp createdAt = TimeUtil.getTimestampNow();

	@Column(name = "updated_at", nullable = false)
	@JsonIgnore
	@ApiObjectField(description = "When the user was last updated")
	private Timestamp updatedAt = TimeUtil.getTimestampNow();

	protected User() {}

	public User(String email, String username) {
		this.email = email;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
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
