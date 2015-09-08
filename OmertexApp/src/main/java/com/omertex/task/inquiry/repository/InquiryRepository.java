package com.omertex.task.inquiry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omertex.task.inquiry.model.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>
{
    public List<Inquiry> findByCustomer (String customer);
}
