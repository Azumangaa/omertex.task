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
    public RepositoryInquiryService (InquiryRepository repository,
	    RepositoryInquiryAttributeService inquiryAttributeRepository, RepositoryTopicService topicService)
    {
	this.inquiryRepository = repository;
	this.inquiryAttributeService = inquiryAttributeRepository;
	this.topicService = topicService;
    }


    public Inquiry getInquiryById (Long inquiryId)
    {
	return inquiryRepository.findOne (inquiryId);
    }


    public Inquiry addInquiry (Inquiry inquiry) throws Exception
    {
	List<InquiryAttribute> attributes = inquiry.getInquiryAttributes ();
	inquiry.setInquiryAttributes (null);
	inquiry = inquiryRepository.saveAndFlush (inquiry);
	if (attributes != null)
	{
	    for (InquiryAttribute inquiryAttribute : attributes)
	    {
		inquiryAttribute.setInquiry (inquiry);
		inquiryAttributeService.add (inquiryAttribute);
	    }
	}
	inquiry.setInquiryAttributes (attributes);
	return inquiry;
    }


    public void deleteInquiry (Long inquiryId) throws Exception
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


    public Inquiry updateInquiry (Inquiry inquiry) throws Exception
    {
	inquiryAttributeService.deleteWhereInquiry (inquiry);
	List<InquiryAttribute> newAttributeList = new ArrayList<InquiryAttribute> (0);
	for (InquiryAttribute attribute : inquiry.getInquiryAttributes ())
	{
	    attribute.setInquiry (inquiry);
	    newAttributeList.add (inquiryAttributeService.add (attribute));
	}
	inquiryRepository.save (inquiry);
	inquiry.setInquiryAttributes (newAttributeList);
	return inquiry;
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
	    throw new Exception ("Wrong inquiry attribute data");
	}
	
	/* TODO check inquiry attribute data */
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
