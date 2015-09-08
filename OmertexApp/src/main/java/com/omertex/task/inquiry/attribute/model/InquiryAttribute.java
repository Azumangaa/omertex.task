package com.omertex.task.inquiry.attribute.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.omertex.task.common.model.BaseEntity;
import com.omertex.task.inquiry.model.Inquiry;
import com.omertex.task.inquiry.repository.InquiryRepository;
import com.omertex.task.serializers.InquiryAttributeSerializer;

@Entity
@JsonSerialize (using = InquiryAttributeSerializer.class)
public class InquiryAttribute extends BaseEntity<Long>
{
    @Override
    public String toString ()
    {
	Long id = (inquiry != null) ? inquiry.getId () : null;
	return "InquiryAttribute [id=" + id + ", name=" + name + ", value=" + value + ", inquiry=" + id + "]";
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column (name = "name", length = 255)
    private String name;

    @Column (name = "value", length = 255)
    private String value;

    @ManyToOne (optional = false)
    @JoinColumn (name = "inquiry_id", insertable = true, referencedColumnName = "id")
    private Inquiry inquiry;


    public void setName (String name)
    {
	this.name = name;
    }


    public void setValue (String value)
    {
	this.value = value;
    }


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
	private InquiryRepository inquiryRepository;


	@Autowired
	public void setInquiryRepository (InquiryRepository inquiryRepository)
	{
	    this.inquiryRepository = inquiryRepository;
	}


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


	public Builder inquiryById (Long id)
	{
	    if (id != null)
		inquiryAttribute.inquiry = inquiryRepository.findOne (id);
	    return this;
	}


	public InquiryAttribute build ()
	{
	    return inquiryAttribute;
	}
    }
}
