package com.omertex.task.test.inquiry.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import com.omertex.task.inquiry.controller.InquiryController;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class TestInquiryController {
	
	@Test
	public void shouldShowInquiries() throws Exception
	{
		InquiryController controller = new InquiryController();
		MockMvc mvc = standaloneSetup(controller)
				.setSingleView( new InternalResourceView("/WEB-INF/jsp/inquiries.jsp"))
				.build();
		
		mvc.perform(get("/inquiries"))
			.andExpect(view().name("inquiries"));
		
	}
}
