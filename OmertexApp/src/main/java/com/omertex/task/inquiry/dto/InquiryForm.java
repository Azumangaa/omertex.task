package com.omertex.task.inquiry.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import com.omertex.task.customer.model.Customer;
import com.omertex.task.inquiry.attribute.model.InquiryAttribute;

public class InquiryForm
{
    @NotNull
    private String description;
    private String customer;
    private Long customerId;
    @SuppressWarnings ("rawtypes")
    private List attributes = LazyList.decorate (new ArrayList (),
	    FactoryUtils.instantiateFactory (InquiryAttribute.class));
    @NotNull
    private Long topicId;


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


    public void setCustomer (Customer customer)
    {
	this.customer = customer.getName ();
	this.customerId = customer.getId ();
    }





    public Long getTopic ()
    {
	return topicId;
    }


    public void setTopic (Long topic)
    {
	this.topicId = topic;
    }


    public Long getCustomerId ()
    {
	return customerId;
    }


    public void setCustomerId (Long customerId)
    {
	this.customerId = customerId;
    }


    @SuppressWarnings ("rawtypes")
    public List getAttributes ()
    {
	return attributes;
    }


    @SuppressWarnings ("rawtypes")
    public void setAttributes (List attributes)
    {
	this.attributes = attributes;
    }
}
