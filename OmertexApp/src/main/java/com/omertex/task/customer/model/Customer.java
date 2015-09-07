package com.omertex.task.customer.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.omertex.task.common.model.BaseEntity;
import com.omertex.task.inquiry.model.Inquiry;

@Entity
public class Customer extends BaseEntity<Long>
{
    @Id
    @GeneratedValue
    private Long id;

    @Column (name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @OneToMany (mappedBy = "customer", targetEntity = Inquiry.class, cascade = CascadeType.ALL)
    private List<Inquiry> inquiries;


    @Override
    public Long getId ()
    {
	return id;
    }


    public String getName ()
    {
	return name;
    }


    public static Builder getBuilder ()
    {
	return new Builder ();
    }

    public static class Builder
    {
	private Customer customer;
	// @Autowired
	// private RepositoryInquiryService;


	public Builder ()
	{
	    customer = new Customer ();
	}


	public Builder name (String name)
	{
	    customer.name = name;
	    return this;
	}


	public Customer build ()
	{
	    return customer;
	}
    }

}
