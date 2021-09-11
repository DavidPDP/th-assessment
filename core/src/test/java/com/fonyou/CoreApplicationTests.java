package com.fonyou;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fonyou.entities.Employee;

@SpringBootTest
@AutoConfigureMockMvc
class CoreApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	@BeforeAll
	static void setUp() {
		// Config to new mapper instance can serialize/deserialize dates.
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setSerializationInclusion(Include.NON_NULL);
	}
	
	@Test
	public void should_No_Save_Employee_When_Mandatory_Field_Is_Null() throws Exception {
		
		// Given
		var nullEmployees = List.of(
			Employee.buildFromMandatoryFields(null, "test", LocalDate.now(), BigDecimal.ONE),
			Employee.buildFromMandatoryFields("test", null, LocalDate.now(), BigDecimal.ONE),
			Employee.buildFromMandatoryFields("test", "test", null, BigDecimal.ONE),
			Employee.buildFromMandatoryFields("test", "test", LocalDate.now(), null)
		);
		
		for (Employee nullEmployee : nullEmployees) {
			
			// When
			var result = mockMvc.perform(post("/employees")
				.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(nullEmployee))
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
			);
							
			// Then
			result.andExpect(status().isOk())
				  .andExpect(jsonPath("$.error").value("no required field can be null."));
		}
		
	}
		
}
