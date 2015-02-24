package com.chaocodes.restapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chaocodes.restapi.model.User;

public interface UserRepository extends CrudRepository<User, Long>
{
	@Override
	public List<User> findAll();

	@Override
	public User findOne(Long id);

	public User findByUsername(String username);

	public User findByOwner(boolean owner);
}
