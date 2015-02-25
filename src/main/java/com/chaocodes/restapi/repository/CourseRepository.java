package com.chaocodes.restapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chaocodes.restapi.model.Course;

public interface CourseRepository extends CrudRepository<Course, Long>
{
	@Override
	public List<Course> findAll();

	@Override
	public Course findOne(Long id);

	public Course findByName(String name);
}
