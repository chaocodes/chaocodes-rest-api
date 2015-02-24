package com.chaocodes.restapi;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;

public class ControllerTest
{
	protected HttpMessageConverter mappingJackson2HttpMessageConverter;

	protected String contentType = MediaType.APPLICATION_JSON_VALUE;

	@Autowired
	public void setConverters(HttpMessageConverter<?>[] converters) {
		mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
				hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
		Assert.assertNotNull("the JSON message converter must not be null", mappingJackson2HttpMessageConverter);
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
