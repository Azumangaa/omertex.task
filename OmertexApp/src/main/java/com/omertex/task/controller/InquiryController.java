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

import com.omertex.task.dto.InquiryDTO;
import com.omertex.task.model.Inquiry;
import com.omertex.task.service.RepositoryInquiryService;


@RestController
public class InquiryController
{
    private RepositoryInquiryService inquiryService;

    private static final String ERROR_HEADER = "ErrorDescription";


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
    public Inquiry getCustomerInquiry (@PathVariable ("customerName") String customerName,
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
    @ResponseBody
    @RequestMapping (value = "/customers/{customerName}/inquiries", method = RequestMethod.POST)
    public Inquiry addCustomerInquiry (@RequestBody InquiryDTO inquiryData,
	    @PathVariable ("customerName") String customerName, HttpServletResponse httpResponse,
 WebRequest request,
	    Model model)
    {

	Inquiry newInquiry = null;
	inquiryData.setCustomer (customerName);
	try
	{
	    newInquiry = inquiryService.addInquiry (inquiryData);
	    httpResponse.setStatus (HttpStatus.CREATED.value ());
	    httpResponse.setHeader ("Location", request.getContextPath () + "/task/customers/" + customerName
		    + "/inquiries/" + newInquiry.getId ());
	    httpResponse.setCharacterEncoding ("application/json; UTF-8");
	    return newInquiry;
	}
	catch (Exception e)
	{
	    httpResponse.setStatus (HttpStatus.UNPROCESSABLE_ENTITY.value ());
	    String message = e.getMessage ();
	    httpResponse.setHeader (ERROR_HEADER, "" + message);
	    return null;
	}
    }


    @RequestMapping (value = "/customers/{customerName}/inquiries/{inquiryId}", method = RequestMethod.PUT)
    public Inquiry updateCustomerInquiry (@RequestBody InquiryDTO newInquiryData,
	    @PathVariable ("customerName") String customerName, @PathVariable ("inquiryId") Long inquiryId,
	    HttpServletResponse httpResponse, Model model)
    {
	Inquiry inquiryToUpdate = inquiryService.getInquiryByIdCustomerName (inquiryId, customerName);

	if (inquiryToUpdate == null)
	{
	    httpResponse.setStatus (HttpStatus.GONE.value ());
	    httpResponse.setHeader (ERROR_HEADER,
		    "Inquiry with id " + inquiryId + " and customer " + customerName + " doesn't exist");
	    return null;
	}

	Inquiry inquiry = null;

	try
	{
	    inquiry = inquiryService.updateInquiry (inquiryToUpdate, newInquiryData);
	}
	catch (Exception e)
	{
	    httpResponse.addHeader (ERROR_HEADER, e.getMessage ());
	    httpResponse.setStatus (HttpStatus.UNPROCESSABLE_ENTITY.value ());
	    return null;
	}

	httpResponse.setStatus (HttpStatus.OK.value ());
	return inquiry;

    }


    @RequestMapping (value = "/customers/{customerName}/inquiries/{inquiryId}", method = RequestMethod.DELETE)
    public void deleteCustomerInquiry (@PathVariable ("customerName") String customerName,

    @PathVariable ("inquiryId") Long inquiryId, HttpServletResponse httpResponse, Model model)
    {
	Inquiry inquiryToUpdate = inquiryService.getInquiryByIdCustomerName (inquiryId, customerName);
	if (inquiryToUpdate == null)
	{
	    httpResponse.setStatus (HttpStatus.GONE.value ());
	    httpResponse.addHeader (ERROR_HEADER, "Error while processing request DELETE:/customer/" + customerName
		    + "/inquiries/" + inquiryId + ": No such inquiry, or wrong customer");
	    return;
	}

	inquiryService.deleteInquiry (inquiryId);

	httpResponse.setStatus (HttpStatus.OK.value ());
    }

}
