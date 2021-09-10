//package com.fonyou;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.util.MimeTypeUtils;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fonyou.entities.Employee;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class CoreApplicationTests {
//
//	private MockMvc mockMvc;
//	
//	public CoreApplicationTests(MockMvc mockMvc) {
//		this.mockMvc = mockMvc;
//	}
//	
//	private ObjectMapper mapper = new ObjectMapper();
//	
//	@Test
//	public void should_No_Save_Employee_When_Mandatory_Field_Is_Empty() throws Exception {
//		
//		Employee newEmployee = Employee.buildFromMandatoryFields(
//				"John", "Doe", null, null);
//		
//		mockMvc.perform(post("/")
//			.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
//			.content(mapper.writeValueAsString(newEmployee))
//			.accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
//		).andExpect(status().is(HttpStatus.OK.value()))
//         .andExpect(header().string("Location", "/api/account/12345"))
//         .andExpect(jsonPath("$.").value("12345")) 
//         .andExpect(jsonPath("$.accountType").value("SAVINGS"))
//         .andExpect(jsonPath("$.balance").value(5000));
//		
//	}
//
//}
