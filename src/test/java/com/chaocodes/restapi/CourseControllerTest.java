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

import com.chaocodes.restapi.model.Course;
import com.chaocodes.restapi.repository.CourseRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CourseControllerTest extends ControllerTest
{
	private MockMvc mockMvc;

	private String baseUrl = "/course";

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private Course testCourse;

	@Before
	public void setup() throws Exception {
		mockMvc = webAppContextSetup(webApplicationContext).build();

		courseRepository.deleteAll();
		testCourse = courseRepository.save(new Course("CS1", "test"));
	}

	@Test
	public void createOneCourse() throws Exception {
		String name = "CS2";
		String description = "testpost";
		Course createCourse = new Course(name, description);
		mockMvc.perform(post(baseUrl)
			.content(json(createCourse))
			.contentType(contentType))
			.andExpect(status().isCreated());
		Course checkCourse = courseRepository.findByName(name);
		Assert.assertNotNull(checkCourse);
		Assert.assertEquals(description, checkCourse.getDescription());
	}

	@Test
	public void readAllCourses() throws Exception {
		mockMvc.perform(get(baseUrl))
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].id", is(testCourse.getId().intValue())))
			.andExpect(jsonPath("$[0].name", is(testCourse.getName())))
			.andExpect(jsonPath("$[0].description", is(testCourse.getDescription())));
	}

	@Test
	public void readOneCourse() throws Exception {
		mockMvc.perform(get(baseUrl + "/" + testCourse.getId()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.id", is(testCourse.getId().intValue())))
			.andExpect(jsonPath("$.name", is(testCourse.getName())))
			.andExpect(jsonPath("$.description", is(testCourse.getDescription())));
	}

	@Test
	public void readOneCourseNotFound() throws Exception {
		mockMvc.perform(get(baseUrl + "/5"))
			.andExpect(status().isNotFound());
	}

	@Test
	public void updateOneCourse() throws Exception {
		Course updateCourse = courseRepository.save(new Course("CS2", "testupdate"));
		Course updatedCourse = new Course("CS3", "testupdated");
		mockMvc.perform(put(baseUrl + "/" + updateCourse.getId())
			.content(json(updatedCourse))
			.contentType(contentType))
			.andExpect(status().isOk());
		Course checkCourse = courseRepository.findOne(updateCourse.getId());
		Assert.assertNotNull(checkCourse);
		Assert.assertEquals(updatedCourse.getName(), checkCourse.getName());
		Assert.assertEquals(updatedCourse.getDescription(), checkCourse.getDescription());
	}

	@Test
	public void deleteOneCourse() throws Exception {
		Course deleteCourse = courseRepository.save(new Course("CS4", "testdelete"));
		mockMvc.perform(delete(baseUrl + "/" + deleteCourse.getId()))
			.andExpect(status().isNoContent());
		Course checkCourse = courseRepository.findOne(deleteCourse.getId());
		Assert.assertNull(checkCourse);
	}
}
