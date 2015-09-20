package com.omertex.task.test.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.omertex.task.model.Inquiry;
import com.omertex.task.model.InquiryAttribute;
import com.omertex.task.model.Topic;
import com.omertex.task.repository.InquiryRepository;
import com.omertex.task.repository.TopicRepository;
import com.omertex.task.service.RepositoryInquiryAttributeService;
import com.omertex.task.test.controller.TestInquiryController;

public class TestRepositoryInquiryAttributeService extends BasicServiceTest
{
    @Autowired
    private RepositoryInquiryAttributeService service;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private InquiryRepository inquiryRepository;

    private Topic testTopic = null;
    private Inquiry testInquiry = null;


    public TestRepositoryInquiryAttributeService ()
    {
    }


    @Before
    public void setUp ()
    {

	testTopic = topicRepository.save (Topic.getBuilder ().name ("TestTopic").build ());
	testInquiry = TestInquiryController.createTestInquiries (1L).get (0);
	testInquiry.setTopic (testTopic);
	testInquiry = inquiryRepository.save (testInquiry);

    }


    @Test
    public void onAddManyShouldReturnNull () throws Exception
    {
	List<InquiryAttribute> attrs = service.addMany (null);

	assertNull (attrs);
    }


    @Test
    public void onAddManyShouldReturnAddedAttributes () throws Exception
    {
	List<InquiryAttribute> attrs = service.addMany (createAttributes (2));

	assertNotNull (attrs);
	assertThat (attrs.size (), equalTo (2));
	assertNotNull (attrs.get (0).getId ());
	assertThat (attrs.get (0).getName (), equalTo ("Attribute0"));
	assertThat (attrs.get (0).getValue (), equalTo ("0"));

	assertNotNull (attrs.get (1).getId ());
	assertThat (attrs.get (1).getName (), equalTo ("Attribute1"));
	assertThat (attrs.get (1).getValue (), equalTo ("1"));
    }


    @Test
    public void onAddShouldThrowErrorWhenNameIsWrong () throws Exception
    {
	Exception ex = null;
	try
	{
	    InquiryAttribute attr = createAttributes (1).get (0);
	    attr.setName (null);
	    service.add (attr);
	}
	catch (Exception e)
	{
	    ex = e;
	}

	assertNotNull (ex);
	assertThat (ex.getMessage (), equalTo ("Wrong attribute data"));
    }


    @Test
    public void onAddShouldThrowErrorWhenValueIsWrong () throws Exception
    {
	Exception ex = null;
	try
	{
	    InquiryAttribute attr = createAttributes (1).get (0);
	    attr.setValue (null);
	    service.add (attr);
	}
	catch (Exception e)
	{
	    ex = e;
	}

	assertNotNull (ex);
	assertThat (ex.getMessage (), equalTo ("Wrong attribute data"));
    }


    @Test
    public void onAddShouldThrowErrorWhenInquiryIsWrong () throws Exception
    {
	Exception ex = null;
	try
	{
	    InquiryAttribute attr = createAttributes (1).get (0);
	    attr.setInquiry (null);
	    service.add (attr);
	}
	catch (Exception e)
	{
	    ex = e;
	}

	assertNotNull (ex);
	assertThat (ex.getMessage (), equalTo ("Wrong attribute data"));
    }


    @Test
    public void onAddShouldReturnInquiryAttribute () throws Exception
    {
	InquiryAttribute attr = createAttributes (1).get (0);
	InquiryAttribute newAttr = service.add (attr);

	assertNotNull (newAttr);
	assertNotNull (newAttr.getId ());
	assertThat (newAttr.getName (), equalTo (attr.getName ()));
	assertThat (newAttr.getValue (), equalTo (attr.getValue ()));
	assertEquals (newAttr.getInquiry (), attr.getInquiry ());
    }


    private List<InquiryAttribute> createAttributes (Integer amount)
    {
	List<InquiryAttribute> attr = new ArrayList<InquiryAttribute> (0);
	for (Integer i = 0; i < amount; i++)
	{
	    attr.add (InquiryAttribute.getBuilder ().name ("Attribute" + i).value ("" + i).inquiry (testInquiry)
		    .build ());
	}
	return attr;
    }

}
