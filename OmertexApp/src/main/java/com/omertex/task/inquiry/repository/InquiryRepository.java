package com.omertex.task.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omertex.task.inquiry.model.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>
{

}
