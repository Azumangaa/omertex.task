package com.omertex.task.inquiry.attribute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.omertex.task.inquiry.attribute.model.InquiryAttribute;
import com.omertex.task.inquiry.model.Inquiry;

public interface InquiryAttributeRepository extends JpaRepository<InquiryAttribute, Long>
{
    List<InquiryAttribute> findByInquiryAndName (Inquiry inquiry, String name);


    @Transactional
    Long deleteByInquiry (Inquiry inquiry);
}
