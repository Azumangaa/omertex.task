package com.omertex.task.inquiry.attribute.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omertex.task.inquiry.attribute.model.InquiryAttribute;
import com.omertex.task.inquiry.attribute.repository.InquiryAttributeRepository;
import com.omertex.task.inquiry.model.Inquiry;

@Service
public class RepositoryInquiryAttributeService
{
    InquiryAttributeRepository repository;


    @Autowired
    public RepositoryInquiryAttributeService (InquiryAttributeRepository repository)
    {
	this.repository = repository;
    }


    public InquiryAttribute addInquiryAttribute (Inquiry inquiry, String name, String value)
    {
	InquiryAttribute attr = inquiryAttributeExists (inquiry, name);
	if (attr == null)
	{
	    attr = InquiryAttribute.getBuilder ().inquiry (inquiry).name (name).value (value).build ();
	    return repository.save (attr);
	}
	else
	    return attr;
    }


    public InquiryAttribute inquiryAttributeExists (Inquiry inquiry, String name)
    {
	List<InquiryAttribute> inquiryAttributes = repository.findByInquiryAndName (inquiry, name);
	if (inquiryAttributes != null)
	    return inquiryAttributes.get (0);
	else
	    return null;
    }


    public List<InquiryAttribute> addMany (List<InquiryAttribute> attributes)
    {
	if (attributes != null)
	    return repository.save (attributes);
	return null;
    }


    public InquiryAttribute add (InquiryAttribute inquiryAttribute)
    {
	if (inquiryAttribute != null)
	    return repository.saveAndFlush (inquiryAttribute);
	return null;
    }


    public Long deleteWhereInquiry (Inquiry inquiry)
    {
	if (inquiry != null)
	    return repository.deleteByInquiry (inquiry);
	return null;
    }
}
