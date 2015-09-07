package com.omertex.task.inquiry.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.omertex.task.common.model.BaseEntity;
import com.omertex.task.customer.model.Customer;
import com.omertex.task.inquiry.attribute.model.InquiryAttribute;
import com.omertex.task.topic.model.Topic;

@Entity
public class Inquiry extends BaseEntity<Long> 
{
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	@OneToMany(mappedBy = "inquiry", targetEntity = InquiryAttribute.class)
	private List<InquiryAttribute> inquiryAttributes;
	
	@OneToOne
	private Topic topic;
	
	
	@Override
	public Long getId() {
		return id;
	}
	
}
