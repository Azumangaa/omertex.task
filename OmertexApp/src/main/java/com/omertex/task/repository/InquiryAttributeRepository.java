package com.omertex.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omertex.task.model.Inquiry;
import com.omertex.task.model.InquiryAttribute;

public interface InquiryAttributeRepository extends JpaRepository<InquiryAttribute, Long>
{
    List<InquiryAttribute> findByInquiry (Inquiry inquiry);
}
