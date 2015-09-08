package com.omertex.task.inquiry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omertex.task.inquiry.attribute.repository.InquiryAttributeRepository;
import com.omertex.task.inquiry.model.Inquiry;
import com.omertex.task.inquiry.repository.InquiryRepository;
import com.omertex.task.topic.repository.TopicRepository;

@Service
public class RepositoryInquiryService
{
    private InquiryRepository inquiryRepository;


    @Autowired
    public RepositoryInquiryService (InquiryRepository repository,
	    TopicRepository topicRepository, InquiryAttributeRepository inquiryAttributeRepository)
    {
	this.inquiryRepository = repository;
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
}
