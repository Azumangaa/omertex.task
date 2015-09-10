package com.omertex.task.test.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.omertex.task.config.PersistenceContext;
import com.omertex.task.config.TaskApplicationContext;
import com.omertex.task.config.WebAppContext;
import com.omertex.task.controller.InquiryController;
import com.omertex.task.model.Inquiry;
import com.omertex.task.model.Topic;
import com.omertex.task.service.RepositoryInquiryService;

@ContextConfiguration (classes = { WebAppContext.class, PersistenceContext.class, TaskApplicationContext.class })
public class TestInquiryController
{

    @Test
    public void shouldShowAllInquiriesForUser () throws Exception
    {
	List<Inquiry> testInquiries = createTestInquiries (2L);
	RepositoryInquiryService mockInquiryService = mock (RepositoryInquiryService.class);
	InquiryController controller = new InquiryController (mockInquiryService);
	MockMvc mvc = standaloneSetup (controller).build ();
	when (mockInquiryService.getCustomerInquiries ("Test")).thenReturn (testInquiries);
	
	mvc.perform (get ("/customers/Test/inquiries/")).andExpect (status ().isOk ())
		.andExpect (content ().contentType ("application/json;charset=UTF-8"))
		.andExpect (jsonPath ("$").isArray ())
.andExpect (jsonPath ("$", hasSize (2)))
		.andExpect (jsonPath ("$[0].id", is (0)))
		.andExpect (jsonPath ("$[0].description", is ("Test inquiry[0] description")))
		.andExpect (jsonPath ("$[0].customer", is ("Test"))).andExpect (jsonPath ("$[0].topic.id", is (0)))
		.andExpect (jsonPath ("$[0].topic.name", is ("Test topic")))
.andExpect (jsonPath ("$[1].id", is (1)))
		.andExpect (jsonPath ("$[1].description", is ("Test inquiry[1] description")))
		.andExpect (jsonPath ("$[1].customer", is ("Test"))).andExpect (jsonPath ("$[1].topic.id", is (0)))
		.andExpect (jsonPath ("$[1].topic.name", is ("Test topic")));
    }


    @Test
    public void shouldShowSingleInquiry () throws Exception
    {
	Inquiry testInquirie = createTestInquiries (1L).get (0);
	RepositoryInquiryService mockInquiryService = mock (RepositoryInquiryService.class);
	InquiryController controller = new InquiryController (mockInquiryService);
	MockMvc mvc = standaloneSetup (controller).build ();
	when (mockInquiryService.getInquiryByIdCustomerName (testInquirie.getId (), testInquirie.getCustomer ()))
		.thenReturn (testInquirie);

	mvc.perform (get ("/customers/Test/inquiries/0")).andExpect (status ().isOk ())
		.andExpect (content ().contentType ("application/json;charset=UTF-8"))
		.andExpect (jsonPath ("$.id", is (0)))
		.andExpect (jsonPath ("$.description", is ("Test inquiry[0] description")))
		.andExpect (jsonPath ("$.customer", is ("Test"))).andExpect (jsonPath ("$.topic.id", is (0)))
		.andExpect (jsonPath ("$.topic.name", is ("Test topic")));
    }


    @Test
    public void shouldAddNewInquiry () throws Exception
    {
	Inquiry testInquirie = createTestInquiries (1L).get (0);
	RepositoryInquiryService mockInquiryService = mock (RepositoryInquiryService.class);
	InquiryController controller = new InquiryController (mockInquiryService);
	MockMvc mvc = standaloneSetup (controller).build ();
    }

    public List<Inquiry> createTestInquiries (Long count)
    {
	List<Inquiry> testInquiries = new ArrayList<Inquiry> (0);
	for (Long i = 0L; i < count; i++)
	{
	    Inquiry.Builder builder = Inquiry.getBuilder ();
	    builder.id (i).description ("Test inquiry[" + i + "] description").customer ("Test")
		    .topic (Topic.getBuilder ().id (0L).name ("Test topic").build ());
	    testInquiries.add (builder.build ());
	}
	return testInquiries;
    }
}
