package com.chaocodes.restapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chaocodes.restapi.exception.ResourceNotFoundException;
import com.chaocodes.restapi.model.Language;
import com.chaocodes.restapi.repository.LanguageRepository;
import com.chaocodes.restapi.util.TimeUtil;

@RestController
@RequestMapping("/language")
@Api(name = "language services", description = "Language API Endpoints")
public class LanguageController
{
	private final LanguageRepository languageRepository;

	@Autowired
	public LanguageController(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}

	private void checkLanguageExists(Long languageId, Language language) {
		if (language == null) {
			throw new ResourceNotFoundException("Language " + languageId + " could not be found.");
		}
	}

	@RequestMapping(method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiMethod(description = "Creates a new language")
	public @ApiResponseObject Language createOneLanguage(@ApiBodyObject @RequestBody @Valid Language language) {
		return languageRepository.save(language);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiMethod(description = "Reads all languages")
	public @ApiResponseObject List<Language> readAllLanguages() {
		return languageRepository.findAll();
	}

	@RequestMapping(value = "/{languageId}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiMethod(description = "Reads a language")
	public @ApiResponseObject Language readOneLanguage(@ApiPathParam(name = "languageId", description="The language ID") @PathVariable Long languageId) {
		Language language = languageRepository.findOne(languageId);
		checkLanguageExists(languageId, language);
		return language;
	}

	@RequestMapping(value = "/{languageId}", method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiMethod(description = "Updates a language")
	public @ApiResponseObject Language updateOneLanguage(@ApiPathParam(name = "languageId", description="The language ID") @PathVariable Long languageId,
			@ApiBodyObject @RequestBody @Valid Language language) {
		Language update = languageRepository.findOne(languageId);
		checkLanguageExists(languageId, update);
		update.setName(language.getName());
		update.setExperience(language.getExperience());
		update.setUpdatedAt(TimeUtil.getTimestampNow());
		return languageRepository.save(update);
	}

	@RequestMapping(value = "/{languageId}",
			method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiMethod(description = "Deletes a language")
	public void deleteOneLanguage(@ApiPathParam(name = "languageId", description="The language ID") @PathVariable Long languageId) {
		Language delete = languageRepository.findOne(languageId);
		checkLanguageExists(languageId, delete);
		languageRepository.delete(delete);
	}
}
