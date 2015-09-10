package com.omertex.task.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.omertex.task.model.Inquiry;
import com.omertex.task.service.RepositoryInquiryService;


@RestController
public class InquiryController
{
    private RepositoryInquiryService inquiryService;

    private static final String DATA_FIELD = "data";
    private static final String ERROR_FIELD = "error";


    @Autowired
    public InquiryController (RepositoryInquiryService inquiryService)
    {
	this.inquiryService = inquiryService;
    }


    @RequestMapping (value = "/customers/{customerName}/inquiries", method = RequestMethod.GET)
    @ResponseBody
    public List<Inquiry> getCustomerInquiries (@PathVariable ("customerName") String customerName, Model model,
	    HttpServletResponse httpResponse
	    )
    {
	List<Inquiry> inquiries = inquiryService.getCustomerInquiries (customerName);

	if (inquiries != null)
	{
	    return inquiries;
	}
	else
	{
	    httpResponse.setStatus (HttpStatus.NO_CONTENT.value ());
	    return null;
	}
    }


    @RequestMapping (value = "/customers/{customerName}/inquiries/{inquiryId}", method = RequestMethod.GET)
    @ResponseBody
    public Inquiry getCustomerInquirie (@PathVariable ("customerName") String customerName,
	    @PathVariable ("inquiryId") Long inquiryId, Model model, HttpServletResponse httpResponse)
    {
	Inquiry inquiry = inquiryService.getInquiryByIdCustomerName (inquiryId, customerName);
	if (inquiry == null)
	{
	    httpResponse.setStatus (HttpStatus.NO_CONTENT.value ());
	    return null;
	}
	else
	{
	    return inquiry;
	}
    }


    @CrossOrigin
    @RequestMapping (value = "/customers/{customerName}/inquiries", method = RequestMethod.POST)
    public Inquiry addCustomerInquiries (@RequestBody Inquiry inquiry,
	    @PathVariable ("customerName") String customerName, HttpServletResponse httpResponse,
 WebRequest request,
	    Model model)
    {

	Inquiry newInquiry = null;
	inquiry.setCustomer (customerName);
	try
	{
	    newInquiry = inquiryService.addInquiry (inquiry);
	    httpResponse.setStatus (HttpStatus.CREATED.value ());
	    httpResponse.setHeader ("Location", request.getContextPath () + "/task/customers/" + customerName
		    + "/inquiries/" + newInquiry.getId ());
	    httpResponse.setCharacterEncoding ("application/json; UTF-8");
	    return newInquiry;
	}
	catch (Exception e)
	{
	    httpResponse.setStatus (HttpStatus.UNPROCESSABLE_ENTITY.value ());
	    return null;
	}
    }


    @RequestMapping (value = "/customers/{customerName}/inquiries/{inquiryId}", method = RequestMethod.PUT)
    public void updateCustomerInquirie (@RequestBody Inquiry updatedInquiry,
	    @PathVariable ("customerName") String customerName, @PathVariable ("inquiryId") Long inquiryId,
	    HttpServletResponse httpResponse, Model model)
    {
	Inquiry inquiryToUpdate = inquiryService.getInquiryByIdCustomerName (inquiryId, customerName);
	if (inquiryToUpdate == null)
	{
	    model.addAttribute (ERROR_FIELD, "Error while processing request PUT:/customer/" + customerName
		    + "/inquiries/"
 + inquiryId + ": No such inquiry, or wrong customer variable");
	}

	Inquiry inquiry = null;
	inquiryToUpdate.setDescription (updatedInquiry.getDescription ());
	inquiryToUpdate.setTopic (updatedInquiry.getTopic ());
	inquiryToUpdate.setInquiryAttributes (updatedInquiry.getInquiryAttributes ());
	try
	{
	    inquiry = inquiryService.updateInquiry (inquiryToUpdate);
	}
	catch (Exception e)
	{
	    String message = "Error while updating inquiry: [%1$s]";
	    model.addAttribute (ERROR_FIELD, message);
	}

	httpResponse.setStatus (HttpStatus.OK.value ());
	model.addAttribute (DATA_FIELD, inquiry);

    }


    @RequestMapping (value = "/customers/{customerName}/inquiries/{inquiryId}", method = RequestMethod.DELETE)
    public void deleteCustomerInquirie (@PathVariable ("customerName") String customerName,
	    @PathVariable ("inquiryId") Long inquiryId, HttpServletResponse httpResponse, Model model)
    {
	Inquiry inquiryToUpdate = inquiryService.getInquiryByIdCustomerName (inquiryId, customerName);
	if (inquiryToUpdate == null)
	{
	    model.addAttribute (ERROR_FIELD, "Error while processing request DELETE:/customer/" + customerName
		    + "/inquiries/" + inquiryId + ": No such inquiry, or wrong customer variable");
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
