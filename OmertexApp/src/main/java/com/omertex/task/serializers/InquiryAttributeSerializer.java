package com.omertex.task.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.omertex.task.inquiry.attribute.model.InquiryAttribute;

public class InquiryAttributeSerializer extends JsonSerializer<InquiryAttribute>
{

    @Override
    public void serialize (InquiryAttribute value, JsonGenerator jgen, SerializerProvider sprovider)
	    throws IOException, JsonProcessingException
    {
	jgen.writeStartObject ();
	jgen.writeNumberField ("id", value.getId ());
	jgen.writeStringField ("name", value.getName ());
	jgen.writeStringField ("value", value.getValue ());
	jgen.writeEndObject ();
    }

}
