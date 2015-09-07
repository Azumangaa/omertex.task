package com.omertex.task.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omertex.task.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{
    public List<Customer> findByName (String name);
}
