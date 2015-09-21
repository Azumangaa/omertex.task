package com.omertex.task.test.service;

import static org.mockito.Matchers.isA;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.omertex.task.dto.InquiryDTO;
import com.omertex.task.model.Inquiry;
import com.omertex.task.model.Topic;
import com.omertex.task.repository.InquiryRepository;
import com.omertex.task.repository.TopicRepository;
import com.omertex.task.service.RepositoryInquiryAttributeService;
import com.omertex.task.service.RepositoryInquiryService;
import com.omertex.task.service.RepositoryTopicService;
import com.omertex.task.test.controller.TestInquiryController;

public class TestRepositoryInquiryService extends BasicServiceTest
{
    RepositoryInquiryService service = null;
    RepositoryInquiryAttributeService attrService = null;
    RepositoryTopicService topicService = null;
    @Autowired
    TopicRepository topicRepo;
    @Autowired
    InquiryRepository inquiryRepo;


    @Before
    public void setUp ()
    {
	attrService = Mockito.mock (RepositoryInquiryAttributeService.class);
	topicService = Mockito.mock (RepositoryTopicService.class);
	service = new RepositoryInquiryService (attrService, topicService, inquiryRepo);
    }


    @Test
    public void onAddInquiryShouldThrowEmptyDescriptionField ()
    {
	InquiryDTO inquiry = TestInquiryController.createTestInquiryDTO ();
	inquiry.setDescription (null);
	Exception ex = null;
	try
	{
	    service.addInquiry (inquiry);
	}
	catch (Exception e)
	{
	    ex = e;
	}

	Assert.assertNotNull (ex);
	Assert.assertThat (ex.getMessage (), Matchers.equalTo ("Empty description field"));
    }


    @Test
    public void onAddInquiryShouldThrowEmptyCustomerField ()
    {
	InquiryDTO inquiry = TestInquiryController.createTestInquiryDTO ();
	inquiry.setCustomer (null);
	Exception ex = null;
	try
	{
	    service.addInquiry (inquiry);
	}
	catch (Exception e)
	{
	    ex = e;
	}

	Assert.assertNotNull (ex);
	Assert.assertThat (ex.getMessage (), Matchers.equalTo ("Empty customer field"));
    }


    @Test
    public void onAddInquiryShouldThrowWrongTopicId ()
    {
	InquiryDTO inquiry = TestInquiryController.createTestInquiryDTO ();
	Mockito.when (topicService.getTopic (isA (Long.class))).thenReturn (null);
	Exception ex = null;
	try
	{
	    service.addInquiry (inquiry);
	}
	catch (Exception e)
	{
	    ex = e;
	}

	Assert.assertNotNull (ex);
	Assert.assertThat (ex.getMessage (), Matchers.equalTo ("Wrong topicId"));
    }


    @Test
    public void onAddInquiryShouldReturnNewInquiry ()
    {
	InquiryDTO inquiry = TestInquiryController.createTestInquiryDTO ();
	Topic topic = topicRepo.save (Topic.getBuilder ().name ("TestTopic").build ());
	Mockito.when (topicService.getTopic (isA (Long.class))).thenReturn (topic);
	inquiry.setTopicId (topic.getId ());
	Exception ex = null;
	Inquiry newInquiry = null;
	try
	{
	    newInquiry = service.addInquiry (inquiry);
	}
	catch (Exception e)
	{
	    ex = e;
	}

	Assert.assertNull (ex);
	Assert.assertNotNull (newInquiry);
	Assert.assertNotNull (newInquiry.getId ());
	Assert.assertThat (newInquiry.getCustomer (), Matchers.equalTo (inquiry.getCustomer ()));
	Assert.assertThat (newInquiry.getDescription (), Matchers.equalTo (inquiry.getDescription ()));
	Assert.assertEquals (newInquiry.getTopic (), topic);
    }


    @Test
    public void onUpdateInquiryShoudlThrowWrongTopicId ()
    {
	Topic topic = topicRepo.save (Topic.getBuilder ().name ("TestTopic").build ());
	Mockito.when (topicService.getTopic (isA (Long.class))).thenReturn (null);
	Inquiry oldInquiry = inquiryRepo
		.save (Inquiry.getBuilder ().customer ("Test").description ("Test").topic (topic).build ());

	InquiryDTO newData = new InquiryDTO ();
	newData.setTopicId (-1L);

	Exception ex = null;
	try
	{
	    service.updateInquiry (oldInquiry, newData);
	}
	catch (Exception e)
	{
	    ex = e;
	}

	Assert.assertNotNull (ex);
	Assert.assertThat (ex.getMessage (), Matchers.equalTo ("Wrong topicId"));
    }


    @Test
    public void onUpdateInquiryShouldUpdateInquiry ()
    {
	Topic topic = topicRepo.save (Topic.getBuilder ().name ("TestTopic").build ());
	Mockito.when (topicService.getTopic (isA (Long.class))).thenReturn (topic);
	Inquiry oldInquiry = inquiryRepo
		.save (Inquiry.getBuilder ().customer ("testInquiry").description ("Test").topic (topic).build ());

	InquiryDTO newData = TestInquiryController.createTestInquiryDTO ();
	Inquiry updatedInquiry = null;
	Exception ex = null;
	try
	{
	    updatedInquiry = service.updateInquiry (oldInquiry, newData);
	}
	catch (Exception e)
	{
	    ex = e;
	}

	Assert.assertNull (ex);
	Assert.assertNotNull (updatedInquiry);
	Assert.assertNotNull (updatedInquiry.getId ());
	Assert.assertThat (updatedInquiry.getId (), Matchers.equalTo (oldInquiry.getId ()));
	Assert.assertThat (updatedInquiry.getCustomer (), Matchers.equalTo (newData.getCustomer ()));
	Assert.assertThat (updatedInquiry.getDescription (), Matchers.equalTo (newData.getDescription ()));
	Assert.assertEquals (updatedInquiry.getTopic (), topic);
    }
}
