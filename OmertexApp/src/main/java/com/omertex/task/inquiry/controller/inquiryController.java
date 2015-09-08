package com.omertex.task.inquiry.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.omertex.task.customer.model.Customer;
import com.omertex.task.customer.service.RepositoryCustomerService;
import com.omertex.task.inquiry.model.Inquiry;
import com.omertex.task.inquiry.service.RepositoryInquiryService;

@Controller
public class inquiryController
{
    private RepositoryCustomerService customerService;
    private RepositoryInquiryService inquiryService;

    private static final String DATA_FIELD = "data";
    private static final String ERROR_FIELD = "error";


    @Autowired
    public inquiryController (RepositoryCustomerService customerService,
	    RepositoryInquiryService inquiryService)
    {
	// this.jsonView = jsonView;
	this.customerService = customerService;
	this.inquiryService = inquiryService;
    }


    @RequestMapping (value = "/customers/{customerName}/inquiries", method = RequestMethod.GET)
    public void getCustomerInquiries (@PathVariable ("customerName") String customerName, Model model)
    {
	Customer customer = customerService.customerExists (customerName);
	if (customer != null)
	{
	    model.addAttribute ("InquiryList", customer.getInquiries ());
	}
	else
	{
	    String message = "Error while processing request GET:/customer/" + customerName
		    + "/inquiries: No such customer";
	    model.addAttribute (ERROR_FIELD, message);
	}
    }


    @RequestMapping (value = "/customers/{customerName}/inquiries/{inquiryId}", method = RequestMethod.GET)
    public void getCustomerInquirie (@PathVariable ("customerName") String customerName,
	    @PathVariable ("inquiryId") Long inquiryId, Model model)
    {
	Customer customer = customerService.customerExists (customerName);
	Inquiry inquiry = inquiryService.getInquiryById (inquiryId);
	if (customer == null)
	{
	    model.addAttribute (ERROR_FIELD, "Error while processing request GET:/customer/" + customerName + "/inquiries/"
		    + inquiryId + ": No such customer");

	}
	else if (inquiry == null)
	{
	    model.addAttribute (ERROR_FIELD, "Error while processing request GET:/customer/" + customerName + "/inquiries/"
		    + inquiryId + ": No such inquiry");
	}
	else
	{
	    model.addAttribute (DATA_FIELD, inquiry);
	}
    }


    @RequestMapping (value = "/customrs/{customerName}/inquiries", method = RequestMethod.POST)
    public void addCustomerInquiries (@RequestBody Inquiry inquiry,
	    @PathVariable ("customerName") String customerName, HttpServletResponse httpResponse,
 WebRequest request,
	    Model model)
    {
	Customer customer = customerService.customerExists (customerName);
	if (customer == null)
	{
	    model.addAttribute (ERROR_FIELD,
		    "Error while processing request POST:/customer/" + customerName + "/inquiries: No such customer");
	}

	Inquiry newInquiry = null;
	inquiry.setCustomer (customer);
	try
	{
	    newInquiry = inquiryService.addInquiry (inquiry);
	}
	catch (Exception e)
	{
	    String message = "Error while creating new inquiry: [%1$s]";
	    model.addAttribute (ERROR_FIELD, message);
	}

	httpResponse.setStatus (HttpStatus.CREATED.value ());
	httpResponse.setHeader ("Location",
		request.getContextPath () + "/task/customers/" + customerName + "/inquiries" + newInquiry.getId ());

	model.addAttribute (DATA_FIELD, newInquiry);
    }


    @RequestMapping (value = "/customrs/{customerName}/inquiries/{inquiryId}", method = RequestMethod.PUT)
    public void updateCustomerInquirie (@RequestBody Inquiry updatedInquiry,
	    @PathVariable ("customerName") String customerName, @PathVariable ("inquiryId") Long inquiryId,
	    HttpServletResponse httpResponse, Model model)
    {
	Customer customer = customerService.customerExists (customerName);
	if (customer == null)
	{
	    model.addAttribute (ERROR_FIELD,
		    "Error while processing request PUT:/customer/" + customerName + "/inquiries: No such customer");
	}
	Inquiry inquiryToUpdate = inquiryService.getInquiryById (inquiryId);
	if (inquiryToUpdate == null)
	{
	    model.addAttribute (ERROR_FIELD, "Error while processing request PUT:/customer/" + customerName
		    + "/inquiries/"
		    + inquiryId + ": No such inquiry");
	}

	Inquiry inquiry = null;
	updatedInquiry.setId (inquiryId);
	try
	{
	    inquiry = inquiryService.addInquiry (updatedInquiry);
	}
	catch (Exception e)
	{
	    String message = "Error while updating inquiry: [%1$s]";
	    model.addAttribute (ERROR_FIELD, message);
	}

	httpResponse.setStatus (HttpStatus.OK.value ());
	model.addAttribute (DATA_FIELD, inquiry);

    }


    @RequestMapping (value = "/customrs/{customerName}/inquiries/{inquiryId}", method = RequestMethod.DELETE)
    public void deleteCustomerInquirie (@PathVariable ("customerName") String customerName,
	    @PathVariable ("inquiryId") Long inquiryId, HttpServletResponse httpResponse, Model model)
    {
	Customer customer = customerService.customerExists (customerName);
	if (customer == null)
	{
	    model.addAttribute (ERROR_FIELD,
		    "Error while processing request DELETE:/customer/" + customerName + "/inquiries: No such customer");
	}
	Inquiry inquiryToUpdate = inquiryService.getInquiryById (inquiryId);
	if (inquiryToUpdate == null)
	{
	    model.addAttribute (ERROR_FIELD, "Error while processing request DELETE:/customer/" + customerName
		    + "/inquiries/" + inquiryId + ": No such inquiry");
	}

	try
	{
	    inquiryService.deleteInquiry (inquiryId);
	}
	catch (Exception e)
	{
	    String message = "Error while deleting inquiry [%1$s]";
	    model.addAttribute (ERROR_FIELD, String.format (message, e.toString ()));
	}

	httpResponse.setStatus (HttpStatus.OK.value ());
	model.addAttribute (DATA_FIELD, null);
    }

}
