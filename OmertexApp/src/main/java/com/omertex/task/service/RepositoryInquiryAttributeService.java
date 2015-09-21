package com.omertex.task.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omertex.task.model.Inquiry;
import com.omertex.task.model.InquiryAttribute;
import com.omertex.task.repository.InquiryAttributeRepository;

@Service
public class RepositoryInquiryAttributeService
{
    InquiryAttributeRepository repository;
    private static final Logger logger_c = Logger.getLogger (RepositoryInquiryAttributeService.class);


    @Autowired
    public RepositoryInquiryAttributeService (InquiryAttributeRepository repository)
    {
	this.repository = repository;
    }



    public List<InquiryAttribute> addMany (List<InquiryAttribute> attributes)
    {
	if (attributes != null)
	    return repository.save (attributes);
	return null;
    }


    public InquiryAttribute add (InquiryAttribute inquiryAttribute) throws Exception
    {
	try
	{
	    return repository.saveAndFlush (inquiryAttribute);
	}
	catch (Exception e)
	{
	    throw new Exception ("Wrong attribute data");
	}

    }


    public void deleteWhereInquiry (Inquiry inquiry)
    {
	if (inquiry != null && inquiry.getId () != null)
	{
	    List<InquiryAttribute> attrs = repository.findByInquiry (inquiry);
	    for (InquiryAttribute inquiryAttribute : attrs)
	    {
		logger_c.debug ("deleting" + inquiryAttribute.toString ());
		repository.delete (inquiryAttribute.getId ());
	    }
	}
    }
}
