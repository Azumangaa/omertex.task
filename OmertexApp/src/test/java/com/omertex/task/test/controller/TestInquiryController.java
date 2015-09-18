package com.omertex.task.test.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omertex.task.config.PersistenceContext;
import com.omertex.task.config.TaskApplicationContext;
import com.omertex.task.config.WebAppContext;
import com.omertex.task.controller.InquiryController;
import com.omertex.task.dto.InquiryDTO;
import com.omertex.task.model.Inquiry;
import com.omertex.task.model.Topic;
import com.omertex.task.service.RepositoryInquiryService;

@ContextConfiguration (classes = { WebAppContext.class, PersistenceContext.class, TaskApplicationContext.class })
public class TestInquiryController
{
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType (MediaType.APPLICATION_JSON.getType (),
	    MediaType.APPLICATION_JSON.getSubtype (), Charset.forName ("utf8"));


    public static byte[] convertObjectToJsonByte (Object object) throws IOException
    {
	ObjectMapper mapper = new ObjectMapper ();
	mapper.setSerializationInclusion (Include.NON_NULL);
	return mapper.writeValueAsBytes (object);
    }


    public List<Inquiry> createTestInquiries (Long count)
    {
	List<Inquiry> testInquiries = new ArrayList<Inquiry> (0);
	for (Long i = 0L; i < count; i++)
	{
	    Inquiry.Builder builder = Inquiry.getBuilder ();
	    builder.id (i).description ("Test inquiry[" + i + "] description").customer ("Test")
		    .topic (Topic.getBuilder ().id (1L).name ("Test topic").build ());
	    testInquiries.add (builder.build ());
	}
	return testInquiries;
    }


    public InquiryDTO createTestInquiryDTO ()
    {
	InquiryDTO inquiryDTO = new InquiryDTO ();
	inquiryDTO.setCustomer ("Test");
	inquiryDTO.setDescription ("Test inquiry[0] description");
	inquiryDTO.setTopicId (1L);
	return inquiryDTO;
    }


    @Test
    public void onAddCustomerInquiryShouldReturnErrorDescriptionHeaderAndStatusUnprocessableEntity () throws Exception
    {
	RepositoryInquiryService mockInquiryService = mock (RepositoryInquiryService.class);
	InquiryController controller = new InquiryController (mockInquiryService);
	MockMvc mvc = standaloneSetup (controller).build ();
	when (mockInquiryService.addInquiry (isA (InquiryDTO.class))).thenThrow (new Exception ("Test"));

	mvc.perform (post ("/customers/Test/inquiries").contentType (APPLICATION_JSON_UTF8).content ("{}")
		.accept (MediaType.APPLICATION_JSON)).andDo (print ()).andExpect (status ().isUnprocessableEntity ())
		.andExpect (header ().string ("ErrorDescription", "Test"));
    }


    @Test
    public void onAddCustomerInquiryShouldReturnInquiryJsonAndStatusCreated () throws Exception
    {
	InquiryDTO testInquiry = createTestInquiryDTO ();

	RepositoryInquiryService mockInquiryService = mock (RepositoryInquiryService.class);
	InquiryController controller = new InquiryController (mockInquiryService);
	MockMvc mvc = standaloneSetup (controller).build ();
	Inquiry newInq = createTestInquiries (1L).get (0);
	when (mockInquiryService.addInquiry (isA (InquiryDTO.class))).thenReturn (newInq);

	ObjectMapper mapper = new ObjectMapper ();
	byte[] serializedInquiryDTO = mapper.writeValueAsBytes (testInquiry);
	
	mvc.perform (post ("/customers/Test/inquiries/0").contentType (APPLICATION_JSON_UTF8)
		.content (serializedInquiryDTO).accept (MediaType.APPLICATION_JSON)).andDo (print ())
		.andExpect (status ().isCreated ()).andExpect (jsonPath ("$.id", is (0)))
		.andExpect (jsonPath ("$.description", is ("Test inquiry[0] description")))
		.andExpect (jsonPath ("$.customer", is ("Test"))).andExpect (jsonPath ("$.topic.id", is (1)))
		.andExpect (jsonPath ("$.topic.name", is ("Test topic")));
    }


    @Test
    public void onGetCustomerInquiriesShouldJsonArrayOfInquiriesAndStatusOk () throws Exception
    {
	List<Inquiry> testInquiries = createTestInquiries (2L);
	RepositoryInquiryService mockInquiryService = mock (RepositoryInquiryService.class);
	InquiryController controller = new InquiryController (mockInquiryService);
	MockMvc mvc = standaloneSetup (controller).build ();
	when (mockInquiryService.getCustomerInquiries ("Test")).thenReturn (testInquiries);

	mvc.perform (get ("/customers/Test/inquiries/")).andExpect (status ().isOk ())
		.andExpect (content ().contentType ("application/json;charset=UTF-8"))
		.andExpect (jsonPath ("$").isArray ()).andExpect (jsonPath ("$", hasSize (2)))
		.andExpect (jsonPath ("$[0].id", is (0)))
		.andExpect (jsonPath ("$[0].description", is ("Test inquiry[0] description")))
		.andExpect (jsonPath ("$[0].customer", is ("Test"))).andExpect (jsonPath ("$[0].topic.id", is (1)))
		.andExpect (jsonPath ("$[0].topic.name", is ("Test topic"))).andExpect (jsonPath ("$[1].id", is (1)))
		.andExpect (jsonPath ("$[1].description", is ("Test inquiry[1] description")))
		.andExpect (jsonPath ("$[1].customer", is ("Test"))).andExpect (jsonPath ("$[1].topic.id", is (1)))
		.andExpect (jsonPath ("$[1].topic.name", is ("Test topic")));
    }


    @Test
    public void onGetCustomerInquiryShouldReturnInquiryJsonAndStatusOk () throws Exception
    {
	Inquiry testInquirie = createTestInquiries (1L).get (0);
	RepositoryInquiryService mockInquiryService = mock (RepositoryInquiryService.class);
	InquiryController controller = new InquiryController (mockInquiryService);
	MockMvc mvc = standaloneSetup (controller).build ();
	when (mockInquiryService.getInquiryByIdCustomerName (testInquirie.getId (), testInquirie.getCustomer ()))
		.thenReturn (testInquirie);

	mvc.perform (get ("/customers/Test/inquiries/0")).andExpect (status ().isOk ())
.andDo (print ())
		.andExpect (content ().contentType ("application/json;charset=UTF-8"))
		.andExpect (jsonPath ("$.id", is (0)))
		.andExpect (jsonPath ("$.description", is ("Test inquiry[0] description")))
		.andExpect (jsonPath ("$.customer", is ("Test"))).andExpect (jsonPath ("$.topic.id", is (1)))
		.andExpect (jsonPath ("$.topic.name", is ("Test topic")));
    }


    @Test
    public void onUpdateCustomerInquiryShouldReturnErrorDescriptionHeaderAndStatusUnprocessableEntity ()
	    throws Exception
    {
	RepositoryInquiryService mockInquiryService = mock (RepositoryInquiryService.class);
	InquiryController controller = new InquiryController (mockInquiryService);
	MockMvc mvc = standaloneSetup (controller).build ();
	Inquiry inquiry = createTestInquiries (1L).get (0);

	when (mockInquiryService.updateInquiry (isA (Inquiry.class), isA (InquiryDTO.class)))
		.thenThrow (new Exception ("Test"));
	when (mockInquiryService.getInquiryByIdCustomerName (isA (Long.class), isA (String.class)))
		.thenReturn (inquiry);
	mvc.perform (put ("/customers/Test/inquiries/0").contentType (APPLICATION_JSON_UTF8).content ("{}")
		.accept (MediaType.APPLICATION_JSON)).andDo (print ()).andExpect (status ().isUnprocessableEntity ())
		.andExpect (header ().string ("ErrorDescription", "Test"));
    }


    @Test
    public void onUpdateCustomerInquiryShouldReturnInquiryJsonAndStatusOk () throws Exception
    {
	RepositoryInquiryService mockInquiryService = mock (RepositoryInquiryService.class);
	InquiryController controller = new InquiryController (mockInquiryService);
	MockMvc mvc = standaloneSetup (controller).build ();

	Inquiry inquiry = createTestInquiries (1L).get (0);

	when (mockInquiryService.updateInquiry (isA (Inquiry.class), isA (InquiryDTO.class))).thenReturn (inquiry);
	when (mockInquiryService.getInquiryByIdCustomerName (0L, "Test")).thenReturn (inquiry);

	mvc.perform (put ("/customer/Test/inquiries/0").contentType (APPLICATION_JSON_UTF8)
		.content ("{\"description\":\"Test\"}")
		.accept (MediaType.APPLICATION_JSON)).andDo (print ()).andExpect (status ().isOk ())
		.andExpect (status ().isCreated ()).andExpect (jsonPath ("$.id", is (0)))
		.andExpect (jsonPath ("$.description", is ("Test inquiry[0] description")))
		.andExpect (jsonPath ("$.customer", is ("Test"))).andExpect (jsonPath ("$.topic.id", is (1)))
		.andExpect (jsonPath ("$.topic.name", is ("Test topic")));
    }


    @Test
    public void onUpdateCustomerInquiryShouldReturnrrorDescriptionHeaderAndStatusGone () throws Exception
    {
	RepositoryInquiryService mockInquiryService = mock (RepositoryInquiryService.class);
	InquiryController controller = new InquiryController (mockInquiryService);
	MockMvc mvc = standaloneSetup (controller).build ();
	when (mockInquiryService.updateInquiry (isA (Inquiry.class), isA (InquiryDTO.class))).thenReturn (null);

	mvc.perform (put ("/customers/Test/inquiries/0").contentType (APPLICATION_JSON_UTF8).content ("{}")
		.accept (MediaType.APPLICATION_JSON)).andDo (print ()).andExpect (status ().isGone ())
		.andExpect (header ().string ("ErrorDescription", "Inquiry with id 0 and customer Test doesn't exist"));
    }

}
