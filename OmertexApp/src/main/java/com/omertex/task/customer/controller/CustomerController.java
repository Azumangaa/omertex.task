package com.omertex.task.customer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.omertex.task.customer.dto.CustomerForm;
import com.omertex.task.customer.service.RepositoryCustomerService;

@Controller
@SessionAttributes ("customer")
public class CustomerController
{
    private RepositoryCustomerService service;


    @Autowired
    public CustomerController (RepositoryCustomerService service)
    {
	this.service = service;
    }


    @RequestMapping (value = "/customer/add", method = RequestMethod.GET)
    public String showCustomerForm (Model model)
    {
	model.addAttribute ("customer", new CustomerForm ());
	return "/customer/add";
    }


    @RequestMapping (value = "/customer/add", method = RequestMethod.POST)
    public String addNewCustomer (@Valid @ModelAttribute ("customer") CustomerForm dto, BindingResult result)
    {
	if (result.hasErrors ())
	    return "/customer/add";
	service.addCustomer (dto);
	return "redirect:/customer/" + dto.getName () + "/newInquiry";
    }

}
