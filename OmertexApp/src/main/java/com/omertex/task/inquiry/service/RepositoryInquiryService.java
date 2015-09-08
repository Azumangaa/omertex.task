package com.omertex.task.inquiry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omertex.task.customer.model.Customer;
import com.omertex.task.customer.repository.CustomerRepository;
import com.omertex.task.inquiry.attribute.model.InquiryAttribute;
import com.omertex.task.inquiry.attribute.repository.InquiryAttributeRepository;
import com.omertex.task.inquiry.dto.InquiryForm;
import com.omertex.task.inquiry.model.Inquiry;
import com.omertex.task.inquiry.model.Inquiry.Builder;
import com.omertex.task.inquiry.repository.InquiryRepository;
import com.omertex.task.topic.model.Topic;
import com.omertex.task.topic.repository.TopicRepository;

@Service
public class RepositoryInquiryService
{
    private InquiryRepository inquiryRepository;
    private CustomerRepository customerRepository;
    private TopicRepository topicRepository;
    private InquiryAttributeRepository inquiryAttributeRepository;


    @Autowired
    public RepositoryInquiryService (InquiryRepository repository, CustomerRepository customerRepository,
	    TopicRepository topicRepository, InquiryAttributeRepository inquiryAttributeRepository)
    {
	this.inquiryRepository = repository;
	this.customerRepository = customerRepository;
	this.topicRepository = topicRepository;
	this.inquiryAttributeRepository = inquiryAttributeRepository;
    }


    @SuppressWarnings ("unchecked")
    public Inquiry addOrUpdateInquiry (InquiryForm inquiryData)
    {

	Builder inquiryBuilder = Inquiry.getBuilder ();
	Customer customer = customerRepository.findOne (inquiryData.getCustomerId ());
	Topic topic = topicRepository.findOne (inquiryData.getTopic ());
	inquiryBuilder.id (inquiryData.getId ());
	Inquiry inquiry = inquiryRepository.save (inquiryBuilder.customer (customer)
		.topic (topic)
		.description (inquiryData.getDescription ()).build ());
	
	if (inquiryData.getAttributes () != null)
	{
	for (InquiryAttribute attr : (List<InquiryAttribute>) inquiryData.getAttributes ())
	    {
		attr.setInquiry (inquiry);
		inquiryAttributeRepository.save (attr);
	    }
	}
	return inquiry;
    }


    public Inquiry getInquiryById (Long inquiryId)
    {
	return inquiryRepository.findOne (inquiryId);
    }


    public Inquiry addInquiry (Inquiry inquiry) throws Exception
    {
	return inquiryRepository.save (inquiry);
    }


    public void deleteInquiry (Long inquiryId) throws Exception
    {
	inquiryRepository.delete (inquiryId);
    }

}
