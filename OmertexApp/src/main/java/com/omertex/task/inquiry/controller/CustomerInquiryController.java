package com.omertex.task.inquiry.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.AutoPopulatingList;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.omertex.task.customer.model.Customer;
import com.omertex.task.customer.repository.CustomerRepository;
import com.omertex.task.inquiry.attribute.model.InquiryAttribute;
import com.omertex.task.inquiry.dto.InquiryForm;
import com.omertex.task.topic.model.Topic;
import com.omertex.task.topic.repository.TopicRepository;

@Controller
public class CustomerInquiryController 
{
	private CustomerRepository customerRepository;
	private TopicRepository topicRepository;
	
	@Autowired
	public CustomerInquiryController(CustomerRepository repository, 
			TopicRepository topicRepository)
	{
		this.customerRepository = repository;
		this.topicRepository = topicRepository;
		
	}
	
	
	@ModelAttribute("inquiryForm")
    public InquiryForm getInquiryForm()
    {
		InquiryForm inquiryForm = new InquiryForm();
		inquiryForm.setAttributes(new AutoPopulatingList(InquiryAttribute.class));
		return inquiryForm;
    }
	
	@RequestMapping(value="/customer/{customerName}/newInquiry", method=RequestMethod.GET)
	public String showInquiryForm( 
			@PathVariable("customerName") String customerName, 
			Model model)
	{
		List<Customer> customers = customerRepository.findByName(customerName);
		if (customers != null && !customers.isEmpty())
		{
			InquiryForm dto = new InquiryForm();
			dto.setCustomer(customers.get(0));
			dto.setAttributes(new AutoPopulatingList(InquiryAttribute.class));
			model.addAttribute("inquiry", dto);

			List<Topic> topics = topicRepository.findAll(); 
			model.addAttribute("topics", topics);
			
			return "/inquiry/newInquiry";
		}
		else
			return "/customer/add";
	}
	
	
	//@RequestMapping(value="")
	
	
	//For ajax append attribute field 
	@RequestMapping(method = RequestMethod.GET, value="/inquiryAttributeRow")
	protected String appendAttributeField(@RequestParam Integer fieldId, ModelMap model)
	{	
		model.addAttribute("attributeNumber", fieldId);
		return "/inquiry/inquiryAttributeRow";
	}
	
}
