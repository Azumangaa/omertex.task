package com.omertex.task.inquiry.attribute.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.omertex.task.common.model.BaseEntity;
import com.omertex.task.inquiry.model.Inquiry;

@Entity
public class InquiryAttribute extends BaseEntity<Long>
{
    @Id
    @GeneratedValue
    private Long id;

    @Column (name = "name", length = 255)
    private String name;

    @Column (name = "value", length = 255)
    private String value;


    public void setName (String name)
    {
	this.name = name;
    }


    public void setValue (String value)
    {
	this.value = value;
    }

    @ManyToOne (optional = false)
    @JoinColumn (name = "inquiry_id", nullable = false)
    private Inquiry inquiry;



    public InquiryAttribute ()
    {
    }


    @Override
    public Long getId ()
    {
	return id;
    }


    public String getName ()
    {
	return name;
    }


    public String getValue ()
    {
	return value;
    }


    public Inquiry getInquiry ()
    {
	return inquiry;
    }


    public void setInquiry (Inquiry inquiry)
    {
	this.inquiry = inquiry;
    }


    public static Builder getBuilder ()
    {
	return new Builder ();
    }

    public static class Builder
    {
	private InquiryAttribute inquiryAttribute;


	public Builder ()
	{
	    this.inquiryAttribute = new InquiryAttribute ();
	}


	public Builder name (String name)
	{
	    inquiryAttribute.name = name;
	    return this;
	}


	public Builder value (String value)
	{
	    inquiryAttribute.value = value;
	    return this;
	}


	public Builder inquiry (Inquiry inquiry)
	{
	    inquiryAttribute.inquiry = inquiry;
	    return this;
	}


	public InquiryAttribute build ()
	{
	    return inquiryAttribute;
	}
    }
}
