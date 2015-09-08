package com.omertex.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omertex.task.model.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>
{
    public List<Inquiry> findByCustomer (String customer);
}
