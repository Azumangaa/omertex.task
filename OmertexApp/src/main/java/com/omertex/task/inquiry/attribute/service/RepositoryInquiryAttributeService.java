package com.omertex.task.inquiry.attribute.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omertex.task.inquiry.attribute.model.InquiryAttribute;
import com.omertex.task.inquiry.attribute.repository.InquiryAttributeRepository;
import com.omertex.task.inquiry.model.Inquiry;

@Service
public class RepositoryInquiryAttributeService {
	InquiryAttributeRepository repository;
	
	@Autowired
	public RepositoryInquiryAttributeService(InquiryAttributeRepository repository) {
		this.repository = repository;
	}
	
	
	public InquiryAttribute addInquiryAttribute(Inquiry inquiry, String name, String value)
	{
		if ( inquiryAttributeExists(inquiry, name) == false )
		{
			InquiryAttribute attr = InquiryAttribute.getBuilder()
					.inquiry(inquiry)
					.name(name)
					.value(value)
					.build();
			return repository.save(attr);
		}
		else
			return null;
	}
	
	
	public boolean inquiryAttributeExists(Inquiry inquiry, String name)
	{
		List<InquiryAttribute> inquiryAttributes = repository.findByInquiryAndName(inquiry, name);
		if (inquiryAttributes != null)
			return true;
		else
			return false;
	}
}
