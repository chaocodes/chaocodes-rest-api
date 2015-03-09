package com.chaocodes.restapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.chaocodes.restapi.model.Language;

public interface LanguageRepository extends CrudRepository<Language, Long>
{
	@Override
	public List<Language> findAll();

	@Override
	public Language findOne(Long id);

	public Language findByName(String name);
}
