package com.chaocodes.restapi;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.chaocodes.restapi.model.Language;
import com.chaocodes.restapi.repository.LanguageRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class LanguageControllerTest extends ControllerTest
{
	private MockMvc mockMvc;

	private String baseUrl = "/language";

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private Language testLanguage;

	@Before
	public void setup() throws Exception {
		mockMvc = webAppContextSetup(webApplicationContext).build();

		languageRepository.deleteAll();
		testLanguage = languageRepository.save(new Language("Java", 80));
	}

	@Test
	public void createOneLanguage() throws Exception {
		String name = "Python";
		Integer experience = 50;
		Language createLanguage = new Language(name, experience);
		mockMvc.perform(post(baseUrl)
			.content(json(createLanguage))
			.contentType(contentType))
			.andExpect(status().isCreated());
		Language checkLanguage = languageRepository.findByName(name);
		Assert.assertNotNull(checkLanguage);
		Assert.assertEquals(experience, checkLanguage.getExperience());
	}

	@Test
	public void createOneLanguageOutOfRange() throws Exception {
		String name = "Go";
		Integer experience = -10;
		Language createLanguage = new Language(name, experience);
		mockMvc.perform(post(baseUrl)
			.content(json(createLanguage))
			.contentType(contentType))
			.andExpect(status().isBadRequest());
		Language checkLanguage = languageRepository.findByName(name);
		Assert.assertNull(checkLanguage);
	}

	@Test
	public void readAllLanguages() throws Exception {
		mockMvc.perform(get(baseUrl))
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].id", is(testLanguage.getId().intValue())))
			.andExpect(jsonPath("$[0].name", is(testLanguage.getName())))
			.andExpect(jsonPath("$[0].experience", is(testLanguage.getExperience())));
	}

	@Test
	public void readOneLanguage() throws Exception {
		mockMvc.perform(get(baseUrl + "/" + testLanguage.getId()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.id", is(testLanguage.getId().intValue())))
			.andExpect(jsonPath("$.name", is(testLanguage.getName())))
			.andExpect(jsonPath("$.experience", is(testLanguage.getExperience())));
	}

	@Test
	public void readOneLanguageNotFound() throws Exception {
		mockMvc.perform(get(baseUrl + "/5"))
			.andExpect(status().isNotFound());
	}

	@Test
	public void updateOneLanguage() throws Exception {
		Language updateLanguage = languageRepository.save(new Language("C++", 30));
		Language updatedLanguage = new Language("Javascript", 75);
		mockMvc.perform(put(baseUrl + "/" + updateLanguage.getId())
			.content(json(updatedLanguage))
			.contentType(contentType))
			.andExpect(status().isOk());
		Language checkLanguage = languageRepository.findOne(updateLanguage.getId());
		Assert.assertNotNull(checkLanguage);
		Assert.assertEquals(updatedLanguage.getName(), checkLanguage.getName());
		Assert.assertEquals(updatedLanguage.getExperience(), checkLanguage.getExperience());
	}

	@Test
	public void deleteOneLanguage() throws Exception {
		Language deleteLanguage = languageRepository.save(new Language("HTML/CSS", 55));
		mockMvc.perform(delete(baseUrl + "/" + deleteLanguage.getId()))
			.andExpect(status().isNoContent());
		Language checkLanguage = languageRepository.findOne(deleteLanguage.getId());
		Assert.assertNull(checkLanguage);
	}
}
