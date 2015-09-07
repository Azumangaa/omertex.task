package com.omertex.task.inquiry.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @Column (name = "description")
    private String description;

    @ManyToOne (optional = false)
    @JoinColumn (name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany (mappedBy = "inquiry", targetEntity = InquiryAttribute.class, cascade = CascadeType.ALL,
	    fetch = FetchType.EAGER)
    private List<InquiryAttribute> inquiryAttributes;

    @OneToOne
    private Topic topic;


    @Override
    public Long getId ()
    {
	return id;
    }


    public String getDescription ()
    {
	return description;
    }


    public Customer getCustomer ()
    {
	return customer;
    }


    public List<InquiryAttribute> getInquiryAttributes ()
    {
	return inquiryAttributes;
    }


    public Topic getTopic ()
    {
	return topic;
    }


    public static Builder getBuilder ()
    {
	return new Builder ();
    }

    public static class Builder
    {
	private Inquiry inquiry;


	public Builder ()
	{
	    this.inquiry = new Inquiry ();
	}


	public Builder description (String description)
	{
	    inquiry.description = description;
	    return this;
	}


	public Builder customer (Customer customer)
	{
	    inquiry.customer = customer;
	    return this;
	}


	public Builder inquiryAttributes (List<InquiryAttribute> inquiryAttributes)
	{
	    inquiry.inquiryAttributes = inquiryAttributes;
	    return this;
	}


	public Builder topic (Topic topic)
	{
	    inquiry.topic = topic;
	    return this;
	}


	public Inquiry build ()
	{
	    return inquiry;
	}
    }
}
