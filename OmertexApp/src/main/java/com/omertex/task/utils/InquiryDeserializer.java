package com.omertex.task.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.omertex.task.model.Inquiry;
import com.omertex.task.model.InquiryAttribute;
import com.omertex.task.model.Topic;
import com.omertex.task.service.RepositoryTopicService;

public class InquiryDeserializer extends JsonDeserializer<Inquiry>
{
    RepositoryTopicService topicService;


    @Autowired
    public InquiryDeserializer (RepositoryTopicService topicService)
    {
	this.topicService = topicService;
    }


    @Override
    public Inquiry deserialize (JsonParser jsonParser, DeserializationContext arg1)
	    throws IOException, JsonProcessingException
    {
	ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
	Long id = null;
	String description = null;
	String customer = null;
        if ( node.get ("id") != null)
	    id = node.get ("id").asLong ();

	if (node.get ("description") != null)
	    description = node.get ("description").asText ();

	if (node.get ("customer") != null)
	    customer = node.get ("customer").asText ();

	List<InquiryAttribute> inquiryAttributes = new ArrayList<InquiryAttribute> (0);

	if (node.get ("inquiryAttributes") != null)
	{
	    Iterator<JsonNode> attributes = node.get ("inquiryAttributes").elements ();
	    while (attributes.hasNext ())
	    {
		JsonNode attr = attributes.next ();
		String attrName = null;
		String attrValue = null;
		if (attr.get ("name") != null)
		    attrName = attr.get ("name").asText ();

		if (attr.get ("value") != null)
		    attrValue = attr.get ("value").asText ();
		inquiryAttributes.add (InquiryAttribute.getBuilder ().name (attrName).value (attrValue).build ());
	    }
	}
	if (node.get ("topic") != null)
	{
	    Topic topic = null;
	    Long topicId;
	    if (node.get ("topic").get ("id") != null)
		topicId = node.get ("topic").get ("id").asLong ();
	}
	Topic topic = topicService.getTopic (node.get ("topic").get ("id").asLong ());

	return Inquiry.getBuilder ().id (id).customer (customer).description (description)
		.inquiryAttributes (inquiryAttributes).topic (topic).build ();
    }

}
