package com.omertex.task.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omertex.task.customer.dto.CustomerForm;
import com.omertex.task.customer.model.Customer;
import com.omertex.task.customer.repository.CustomerRepository;

@Service
public class RepositoryCustomerService
{

    private CustomerRepository repository;


    @Autowired
    public RepositoryCustomerService (CustomerRepository repository)
    {
	this.repository = repository;
    }


    public Customer addCustomer (CustomerForm dto)
    {
	Customer customer = customerExists (dto.getName ());
	if (customer != null)
	    return customer;
	else
	{
	    customer = Customer.getBuilder ().name (dto.getName ()).build ();
	    return repository.save (customer);
	}
    }


    private Customer customerExists (String name)
    {
	List<Customer> customer = repository.findByName (name);
	if (customer != null && !customer.isEmpty ())
	    return customer.get (0);
	else
	    return null;

    }

}
