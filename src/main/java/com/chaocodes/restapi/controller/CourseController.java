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
import com.chaocodes.restapi.model.Course;
import com.chaocodes.restapi.repository.CourseRepository;
import com.chaocodes.restapi.util.TimeUtil;

@RestController
@RequestMapping("/course")
@Api(name = "course services", description = "Course API Endpoints")
public class CourseController
{
	private final CourseRepository courseRepository;

	@Autowired
	public CourseController(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	private void checkCourseExists(Long courseId, Course course) {
		if (course == null) {
			throw new ResourceNotFoundException("Course " + courseId + " could not be found.");
		}
	}

	@RequestMapping(method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiMethod(description = "Creates a new course")
	public @ApiResponseObject Course createOneCourse(@ApiBodyObject @RequestBody @Valid Course course) {
		return courseRepository.save(course);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiMethod(description = "Reads all courses")
	public @ApiResponseObject List<Course> readAllCourses() {
		return courseRepository.findAll();
	}

	@RequestMapping(value = "/{courseId}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiMethod(description = "Reads a course")
	public @ApiResponseObject Course readOneCourse(@ApiPathParam(name = "courseId", description="The course ID") @PathVariable Long courseId) {
		Course course = courseRepository.findOne(courseId);
		checkCourseExists(courseId, course);
		return course;
	}

	@RequestMapping(value = "/{courseId}", method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiMethod(description = "Updates a course")
	public @ApiResponseObject Course updateOneCourse(@ApiPathParam(name = "courseId", description="The course ID") @PathVariable Long courseId,
			@ApiBodyObject @RequestBody @Valid Course course) {
		Course update = courseRepository.findOne(courseId);
		checkCourseExists(courseId, update);
		update.setName(course.getName());
		update.setDescription(course.getDescription());
		update.setUpdatedAt(TimeUtil.getTimestampNow());
		return courseRepository.save(update);
	}

	@RequestMapping(value = "/{courseId}",
			method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiMethod(description = "Deletes a course")
	public void deleteOneCourse(@ApiPathParam(name = "courseId", description="The course ID") @PathVariable Long courseId) {
		Course delete = courseRepository.findOne(courseId);
		checkCourseExists(courseId, delete);
		courseRepository.delete(delete);
	}
}
