package com.omertex.task.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omertex.task.dto.InquiryDTO;
import com.omertex.task.model.Inquiry;
import com.omertex.task.model.InquiryAttribute;
import com.omertex.task.model.Topic;
import com.omertex.task.repository.InquiryRepository;

@Service
public class RepositoryInquiryService
{
    private InquiryRepository inquiryRepository;
    private RepositoryInquiryAttributeService inquiryAttributeService;
    private RepositoryTopicService topicService;


    @Autowired
    public RepositoryInquiryService (
RepositoryInquiryAttributeService inquiryAttributeRepository,
	    RepositoryTopicService topicService, InquiryRepository inquiryRepository)
    {
	this.inquiryAttributeService = inquiryAttributeRepository;
	this.topicService = topicService;
	this.inquiryRepository = inquiryRepository;
    }


    public void deleteInquiry (Long inquiryId)
    {
	inquiryRepository.delete (inquiryId);
    }


    public List<Inquiry> getCustomerInquiries (String customerName)
    {
	List<Inquiry> inquiries = inquiryRepository.findByCustomer (customerName);
	return (inquiries.isEmpty ()) ? null : inquiries;
    }


    public Inquiry getInquiryByIdCustomerName (Long inquiryId, String customerName)
    {
	Inquiry inquiry = inquiryRepository.findOne (inquiryId);
	if (inquiry != null)
	{
	if (inquiry.getCustomer ().equals (customerName))
	    return inquiry;
	}
	return null;
    }


    @Transactional (rollbackFor = Exception.class)
    public Inquiry addInquiry (InquiryDTO inquiryData) throws Exception
    {
	String description = inquiryData.getDescription ();
	if (description == null)
	    throw new Exception ("Empty description field");
	
	String customer = inquiryData.getCustomer ();
	if (customer == null)
	    throw new Exception ("Empty customer field");
	
	Topic topic = topicService.getTopic (inquiryData.getTopicId ());
	if (topic == null)
	    throw new Exception ("Wrong topicId");
	
	Inquiry savedInquiry;
	try {
	    savedInquiry = inquiryRepository.save (Inquiry.getBuilder ().customer (customer).description (description).topic (topic)
		    .build ());
	} catch(Exception e) {
	    throw e;
	}
	
	List<InquiryAttribute> savedInquiryAttributes = new ArrayList<InquiryAttribute> (0);
	
	for (InquiryAttribute attr : inquiryData.getAttributes ())
	{
	    attr.setInquiry (savedInquiry);
	    savedInquiryAttributes.add (inquiryAttributeService.add (attr));
	}
	savedInquiry.setInquiryAttributes (savedInquiryAttributes);
	
	return savedInquiry;
    }



    @Transactional
    public Inquiry updateInquiry (Inquiry inquiryToUpdate, InquiryDTO newInquiryData) throws Exception
    {
	if (newInquiryData.getDescription () != null)
	    inquiryToUpdate.setDescription (newInquiryData.getDescription ());

	if (newInquiryData.getCustomer () != null)
	    inquiryToUpdate.setCustomer (newInquiryData.getCustomer ());

	if (newInquiryData.getTopicId () != null)
	{
	    Topic topic = topicService.getTopic (newInquiryData.getTopicId ());
	    if (topic == null)
		throw new Exception ("Wrong topicId");
	    inquiryToUpdate.setTopic (topic);
	}

	/* TODO check inquiry attribute data */
	if (newInquiryData.getAttributes () != null)
	{
	    inquiryAttributeService.deleteWhereInquiry (inquiryToUpdate);
	    List<InquiryAttribute> savedInquiryAttributes = new ArrayList<InquiryAttribute> (0);

	    for (InquiryAttribute attr : newInquiryData.getAttributes ())
	    {
		attr.setInquiry (inquiryToUpdate);
		savedInquiryAttributes.add (inquiryAttributeService.add (attr));
	    }
	    inquiryToUpdate.setInquiryAttributes (savedInquiryAttributes);
	}

	inquiryRepository.save (inquiryToUpdate);

	return null;
    }
}
