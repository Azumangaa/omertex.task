package com.omertex.task.utils;

import java.io.IOException;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.omertex.task.dto.InquiryDTO;
import com.omertex.task.model.InquiryAttribute;
import com.omertex.task.service.RepositoryTopicService;

public class InquiryDeserializer extends JsonDeserializer<InquiryDTO>
{
    RepositoryTopicService topicService;


    @Autowired
    public InquiryDeserializer (RepositoryTopicService topicService)
    {
	this.topicService = topicService;
    }


    @Override
    public InquiryDTO deserialize (JsonParser jsonParser, DeserializationContext arg1)
	    throws IOException, JsonProcessingException
    {
	ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
	InquiryDTO inquiry = new InquiryDTO ();
	
	if (node.get ("description") != null)
	    inquiry.setDescription (node.get ("description").asText ());

	if (node.get ("customer") != null)
	    inquiry.setCustomer (node.get ("customer").asText ());

	if (node.get ("inquiryAttributes") != null)
	{
	    Iterator<JsonNode> attributes = node.get ("inquiryAttributes").elements ();
	    while (attributes.hasNext ())
	    {
		JsonNode jsonAttr = attributes.next ();
		InquiryAttribute attr = new InquiryAttribute ();
		if (jsonAttr.get ("name") != null)
		    attr.setName (jsonAttr.get ("name").asText ());

		if (jsonAttr.get ("value") != null)
		    attr.setValue (jsonAttr.get ("value").asText ());
		inquiry.addAttribute (attr);
	    }
	}
	
	if (node.get ("topicId") != null)
	    inquiry.setTopicId (node.get ("topicId").asLong ());

	return inquiry;
    }

}
