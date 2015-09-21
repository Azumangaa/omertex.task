package com.omertex.task.test.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.isA;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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

    private MockMvc mvc;
    @Mock
    private RepositoryInquiryService service;
    @InjectMocks
    private InquiryController controller;


    @Before
    public void setUp () throws Exception
    {
	MockitoAnnotations.initMocks (this);
	mvc = MockMvcBuilders.standaloneSetup (controller).build ();
    }

    public static byte[] convertObjectToJsonByte (Object object) throws IOException
    {
	ObjectMapper mapper = new ObjectMapper ();
	mapper.setSerializationInclusion (Include.NON_NULL);
	return mapper.writeValueAsBytes (object);
    }


    public static List<Inquiry> createTestInquiries (Long count)
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


    public static InquiryDTO createTestInquiryDTO ()
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
	Mockito.when (service.addInquiry (isA (InquiryDTO.class))).thenThrow (new Exception ("Test"));

	mvc.perform (MockMvcRequestBuilders.post ("/customers/Test/inquiries").contentType (APPLICATION_JSON_UTF8)
		.content ("{}").accept (MediaType.APPLICATION_JSON)).andDo (MockMvcResultHandlers.print ())
		.andExpect (MockMvcResultMatchers.status ().isUnprocessableEntity ())
		.andExpect (MockMvcResultMatchers.header ().string ("ErrorDescription", "Test"));
    }


    @Test
    public void onAddCustomerInquiryShouldReturnInquiryJsonAndStatusCreated () throws Exception
    {
	InquiryDTO testInquiry = createTestInquiryDTO ();

	Inquiry newInq = createTestInquiries (1L).get (0);

	Mockito.when (service.addInquiry (isA (InquiryDTO.class))).thenReturn (newInq);

	ObjectMapper mapper = new ObjectMapper ();
	byte[] serializedInquiryDTO = mapper.writeValueAsBytes (testInquiry);
	
	mvc.perform (MockMvcRequestBuilders.post ("/customers/Test/inquiries").contentType (APPLICATION_JSON_UTF8)
		.content (serializedInquiryDTO).accept (MediaType.APPLICATION_JSON))
		.andDo (MockMvcResultHandlers.print ()).andExpect (MockMvcResultMatchers.status ().isCreated ())
		.andExpect (MockMvcResultMatchers.jsonPath ("$.id", is (0)))
		.andExpect (MockMvcResultMatchers.jsonPath ("$.description", is ("Test inquiry[0] description")))
		.andExpect (MockMvcResultMatchers.jsonPath ("$.customer", is ("Test")))
		.andExpect (MockMvcResultMatchers.jsonPath ("$.topic.id", is (1)))
		.andExpect (MockMvcResultMatchers.jsonPath ("$.topic.name", is ("Test topic")));
    }


    @Test
    public void onGetCustomerInquiriesShouldJsonArrayOfInquiriesAndStatusOk () throws Exception
    {
	List<Inquiry> testInquiries = createTestInquiries (2L);

	Mockito.when (service.getCustomerInquiries ("Test")).thenReturn (testInquiries);

	mvc.perform (MockMvcRequestBuilders.get ("/customers/Test/inquiries/"))
		.andExpect (MockMvcResultMatchers.status ().isOk ())
		.andExpect (MockMvcResultMatchers.content ().contentType ("application/json;charset=UTF-8"))
		.andExpect (MockMvcResultMatchers.jsonPath ("$").isArray ())
		.andExpect (MockMvcResultMatchers.jsonPath ("$", hasSize (2)))
		.andExpect (MockMvcResultMatchers.jsonPath ("$[0].id", is (0)))
		.andExpect (MockMvcResultMatchers.jsonPath ("$[0].description", is ("Test inquiry[0] description")))
		.andExpect (MockMvcResultMatchers.jsonPath ("$[0].customer", is ("Test")))
		.andExpect (MockMvcResultMatchers.jsonPath ("$[0].topic.id", is (1)))
		.andExpect (MockMvcResultMatchers.jsonPath ("$[0].topic.name", is ("Test topic")))
		.andExpect (MockMvcResultMatchers.jsonPath ("$[1].id", is (1)))
		.andExpect (MockMvcResultMatchers.jsonPath ("$[1].description", is ("Test inquiry[1] description")))
		.andExpect (MockMvcResultMatchers.jsonPath ("$[1].customer", is ("Test")))
		.andExpect (MockMvcResultMatchers.jsonPath ("$[1].topic.id", is (1)))
		.andExpect (MockMvcResultMatchers.jsonPath ("$[1].topic.name", is ("Test topic")));
    }


    @Test
    public void onGetCustomerInquiryShouldReturnInquiryJsonAndStatusOk () throws Exception
    {
	Inquiry testInquirie = createTestInquiries (1L).get (0);

	Mockito.when (service.getInquiryByIdCustomerName (testInquirie.getId (), testInquirie.getCustomer ()))
		.thenReturn (testInquirie);

	mvc.perform (MockMvcRequestBuilders.get ("/customers/Test/inquiries/0"))
		.andExpect (MockMvcResultMatchers.status ().isOk ())
		.andDo (MockMvcResultHandlers.print ())
		.andExpect (MockMvcResultMatchers.content ().contentType ("application/json;charset=UTF-8"))
		.andExpect (MockMvcResultMatchers.jsonPath ("$.id", is (0)))
		.andExpect (MockMvcResultMatchers.jsonPath ("$.description", is ("Test inquiry[0] description")))
		.andExpect (MockMvcResultMatchers.jsonPath ("$.customer", is ("Test")))
		.andExpect (MockMvcResultMatchers.jsonPath ("$.topic.id", is (1)))
		.andExpect (MockMvcResultMatchers.jsonPath ("$.topic.name", is ("Test topic")));
    }


    @Test
    public void onUpdateCustomerInquiryShouldReturnErrorDescriptionHeaderAndStatusUnprocessableEntity ()
	    throws Exception
    {
	Inquiry inquiry = createTestInquiries (1L).get (0);

	Mockito.when (service.updateInquiry (isA (Inquiry.class), isA (InquiryDTO.class)))
		.thenThrow (new Exception ("Test"));
	Mockito.when (service.getInquiryByIdCustomerName (isA (Long.class), isA (String.class)))
		.thenReturn (inquiry);
	mvc.perform (MockMvcRequestBuilders.put ("/customers/Test/inquiries/0").contentType (APPLICATION_JSON_UTF8)
		.content ("{}").accept (MediaType.APPLICATION_JSON)).andDo (MockMvcResultHandlers.print ())
		.andExpect (MockMvcResultMatchers.status ().isUnprocessableEntity ())
		.andExpect (MockMvcResultMatchers.header ().string ("ErrorDescription", "Test"));
    }


    @Test
    public void onUpdateCustomerInquiryShouldReturnInquiryJsonAndStatusOk () throws Exception
    {
	Inquiry inquiry = createTestInquiries (1L).get (0);

	Mockito.when (service.updateInquiry (isA (Inquiry.class), isA (InquiryDTO.class))).thenReturn (inquiry);
	Mockito.when (service.getInquiryByIdCustomerName (isA (Long.class), isA (String.class)))
		.thenReturn (inquiry);

	mvc.perform (MockMvcRequestBuilders.put ("/customers/Test/inquiries/0").contentType (APPLICATION_JSON_UTF8)
		.content ("{}")
.accept (MediaType.APPLICATION_JSON)).andDo (MockMvcResultHandlers.print ())
		.andExpect (MockMvcResultMatchers.status ().isOk ())
		.andExpect (MockMvcResultMatchers.jsonPath ("$.id", is (0)))
		.andExpect (MockMvcResultMatchers.jsonPath ("$.description", is ("Test inquiry[0] description")))
		.andExpect (MockMvcResultMatchers.jsonPath ("$.customer", is ("Test")))
		.andExpect (MockMvcResultMatchers.jsonPath ("$.topic.id", is (1)))
		.andExpect (MockMvcResultMatchers.jsonPath ("$.topic.name", is ("Test topic")));
    }


    @Test
    public void onUpdateCustomerInquiryShouldReturnErrorDescriptionHeaderAndStatusGone () throws Exception
    {
	Mockito.when (service.updateInquiry (isA (Inquiry.class), isA (InquiryDTO.class))).thenReturn (null);

	mvc.perform (MockMvcRequestBuilders.put ("/customers/Test/inquiries/0").contentType (APPLICATION_JSON_UTF8)
		.content ("{}").accept (MediaType.APPLICATION_JSON)).andDo (MockMvcResultHandlers.print ())
		.andExpect (MockMvcResultMatchers.status ().isGone ()).andExpect (MockMvcResultMatchers.header ()
			.string ("ErrorDescription", "Inquiry with id 0 and customer Test doesn't exist"));
    }

    @Test
    public void onDeleteCustomerInquiryShouldReturnErrorDescriptionHeaderAndStatusGone () throws Exception
    {
	Mockito.when (service.getInquiryByIdCustomerName (isA (Long.class), isA (String.class))).thenReturn (null);

	mvc.perform (MockMvcRequestBuilders.delete ("/customers/Test/inquiries/0"))
		.andDo (MockMvcResultHandlers.print ()).andExpect (MockMvcResultMatchers.status ().isGone ())
		.andExpect (MockMvcResultMatchers.header ().string ("ErrorDescription",
			"Error while processing request DELETE:/customer/Test/inquiries/0: No such inquiry, or wrong customer"));
    }


    @Test
    public void onDeleteCustomerInquiryShouldReturnStatusOk () throws Exception
    {
	Mockito.when (service.getInquiryByIdCustomerName (isA (Long.class), isA (String.class)))
		.thenReturn (new Inquiry ());

	mvc.perform (MockMvcRequestBuilders.delete ("/customers/Test/inquiries/0"))
		.andDo (MockMvcResultHandlers.print ()).andExpect (MockMvcResultMatchers.status ().isOk ());
    }

}
