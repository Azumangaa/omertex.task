package com.omertex.task.inquiry.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.AutoPopulatingList;

import com.omertex.task.customer.model.Customer;
import com.omertex.task.inquiry.attribute.model.InquiryAttribute;
import com.omertex.task.topic.model.Topic;

public class InquiryForm {
	@NotNull
	private String description;
	private String customer;
	private Long customerId;
	private AutoPopulatingList attributes = null;
	@NotNull
	private Long topicId;
	
	
	public String getDescription() 
	{
		return description;
	}
	
	
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	
	public String getCustomer() 
	{
		return customer;
	}
	
	
	public void setCustomer(Customer customer) 
	{
		this.customer = customer.getName();
	}
		
	
	public AutoPopulatingList<InquiryAttribute> getAttributes() {
		return attributes;
	}


	public void setAttributes(AutoPopulatingList<InquiryAttribute> attributes) {
		this.attributes = attributes;
	}


	public Long getTopic() 
	{
		return topicId;
	}
	
	
	public void setTopic(Long topic) 
	{
		this.topicId = topic;
	}


	public Long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
}
