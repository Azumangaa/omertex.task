package com.omertex.task.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omertex.task.model.Inquiry;
import com.omertex.task.model.InquiryAttribute;
import com.omertex.task.repository.InquiryRepository;

@Service
public class RepositoryInquiryService
{
    private InquiryRepository inquiryRepository;
    private RepositoryInquiryAttributeService inquiryAttributeRepository;


    @Autowired
    public RepositoryInquiryService (InquiryRepository repository,
	    RepositoryInquiryAttributeService inquiryAttributeRepository)
    {
	this.inquiryRepository = repository;
	this.inquiryAttributeRepository = inquiryAttributeRepository;
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
		inquiryAttributeRepository.add (inquiryAttribute);
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


    public Inquiry updateInquiry (Inquiry inquiry)
    {
	inquiryAttributeRepository.deleteWhereInquiry (inquiry);
	List<InquiryAttribute> newAttributeList = new ArrayList<InquiryAttribute> (0);
	for (InquiryAttribute attribute : inquiry.getInquiryAttributes ())
	{
	    attribute.setInquiry (inquiry);
	    newAttributeList.add (inquiryAttributeRepository.add (attribute));
	}
	inquiryRepository.save (inquiry);
	inquiry.setInquiryAttributes (newAttributeList);
	return inquiry;
    }
}