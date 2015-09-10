package com.omertex.task.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.omertex.task.model.InquiryAttribute;
import com.omertex.task.utils.InquiryDeserializer;

@JsonDeserialize (using = InquiryDeserializer.class)
public class InquiryDTO
{

    private String description = null;
    private String customer = null;
    private List<InquiryAttribute> attributes = new ArrayList<InquiryAttribute> (0);
    private Long topicId = null;


    public String getDescription ()
    {
	return description;
    }


    public void setDescription (String description)
    {
	this.description = description;
    }


    public String getCustomer ()
    {
	return customer;
    }


    public void setCustomer (String customer)
    {
	this.customer = customer;
    }


    public List<InquiryAttribute> getAttributes ()
    {
	return attributes;
    }


    public void setAttributes (List<InquiryAttribute> attributes)
    {
	this.attributes = attributes;
    }


    public void addAttribute (InquiryAttribute attr)
    {
	this.attributes.add (attr);
    }


    public Long getTopicId ()
    {
	return topicId;
    }


    public void setTopicId (Long topicId)
    {
	this.topicId = topicId;
    }

}
