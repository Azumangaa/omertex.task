package com.omertex.task.inquiry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InquiryController
{
    @RequestMapping (value = "/inquiries/{pageNumber}", method = RequestMethod.GET)
    public String showInquiries (@PathVariable (value = "pageNumber") Integer pageNumber)
    {
	return "inquiries";
    }
}
