package com.omertex.task.inquiry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.omertex.task.customer.model.Customer;
import com.omertex.task.customer.repository.CustomerRepository;
import com.omertex.task.inquiry.dto.InquiryForm;
import com.omertex.task.inquiry.model.Inquiry;
import com.omertex.task.inquiry.service.RepositoryInquiryService;
import com.omertex.task.topic.model.Topic;
import com.omertex.task.topic.repository.TopicRepository;

@Controller
public class CustomerInquiryController
{
    private CustomerRepository customerRepository;
    private RepositoryInquiryService inquiryService;
    private TopicRepository topicRepository;


    @Autowired
    public CustomerInquiryController (CustomerRepository repository, TopicRepository topicRepository,
	    RepositoryInquiryService inquiryService)
    {
	this.customerRepository = repository;
	this.topicRepository = topicRepository;
	this.inquiryService = inquiryService;

    }


    @ModelAttribute ("inquiryForm")
    public InquiryForm getInquiryForm ()
    {
	InquiryForm inquiryForm = new InquiryForm ();
	return inquiryForm;
    }


    @RequestMapping (value = "/customer/{customerName}/newInquiry", method = RequestMethod.GET)
    public String showInquiryForm (@PathVariable ("customerName") String customerName, Model model)
    {
	List<Customer> customers = customerRepository.findByName (customerName);
	if (customers != null && !customers.isEmpty ())
	{
	    InquiryForm dto = getInquiryForm ();
	    dto.setCustomer (customers.get (0));
	    model.addAttribute ("inquiry", dto);
	    
	    List<Topic> topics = topicRepository.findAll ();
	    model.addAttribute ("topics", topics);

	    return "/inquiry/newInquiry";
	}
	else
	    return "/customer/add";
    }


    @RequestMapping (value = "/customer/{customerName}/inquiry", method = RequestMethod.POST)
    public String saveInquiry (@ModelAttribute ("inquiry") InquiryForm inquiryData,
	    @PathVariable ("customerName") String customerName, BindingResult result)
    {
	// if (result.hasErrors ())
	// return "/customer/" + customerName + "/newInquiry";
	Inquiry inquiry = inquiryService.addInquiry (inquiryData);
	return "redirect:/customer/" + customerName + "/inquiry/" + inquiry.getId ();
    }

    /*
     * @RequestMapping (value = "/customer/{customerName}/inquiry/{inquiryId}",
     * method = RequestMethod.GET) public String showSingleInquiry
     * (@PathVariable ("customerName") String customerName, @PathVariable
     * ("inquiryId") Long inquiryId, Model model) {
     * 
     * return }
     */


    // For ajax append attribute field
    @RequestMapping (method = RequestMethod.GET, value = "/inquiryAttributeRow")
    protected String appendAttributeField (@RequestParam Integer fieldId, ModelMap model)
    {
	model.addAttribute ("attributeNumber", fieldId);
	return "/inquiry/inquiryAttributeRow";
    }

}
