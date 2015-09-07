package com.omertex.task.customer.dto;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

public class CustomerForm
{
    @NotEmpty
    @Size (max = 100)
    private String name;


    public String getName ()
    {
	return name;
    }


    public void setName (String name)
    {
	this.name = name;
    }


    @Override
    public String toString ()
    {
	return new ToStringBuilder (this).append ("name", name).toString ();
    }
}
