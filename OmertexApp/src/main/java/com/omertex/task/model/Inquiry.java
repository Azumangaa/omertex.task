package com.omertex.task.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Inquiry extends BaseEntity<Long>
{
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @NotNull
    @Column (name = "description", nullable = false)
    private String description;

    @NotEmpty
    @NotNull
    @Column (name = "customer", nullable = false)
    private String customer;

    @OneToMany (mappedBy = "inquiry", targetEntity = InquiryAttribute.class, cascade = CascadeType.ALL,
	    orphanRemoval = true, fetch = FetchType.EAGER)
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


    public String getCustomer ()
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


    public void setId (Long id)
    {
	this.id = id;
    }


    public void setDescription (String description)
    {
	this.description = description;
    }


    public void setCustomer (String customer)
    {
	this.customer = customer;
    }


    public void setInquiryAttributes (List<InquiryAttribute> inquiryAttributes)
    {
	this.inquiryAttributes = inquiryAttributes;
    }


    public void setTopic (Topic topic)
    {
	this.topic = topic;
    }


    @Override
    public String toString ()
    {
	return "Inquiry [id=" + id + ", description=" + description + ", customer=" + customer + ", inquiryAttributes="
		+ inquiryAttributes + ", topic=" + topic + "]";
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


	public Builder customer (String customer)
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


	public Builder id (Long id)
	{
	    inquiry.id = id;
	    return this;
	}


	public Inquiry build ()
	{
	    return inquiry;
	}
    }
}
