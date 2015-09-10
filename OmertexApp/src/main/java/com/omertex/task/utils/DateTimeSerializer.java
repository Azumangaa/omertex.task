package com.omertex.task.utils;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateTimeSerializer extends JsonSerializer<DateTime>
{

    @Override
    public void serialize (DateTime value, JsonGenerator jgen, SerializerProvider sprovider)
	    throws IOException, JsonProcessingException
    {
	if(value != null)
	{
        	DateTimeFormatter formatter = DateTimeFormat.forPattern ("yyyy-MM-dd HH:mm:ss");
        	String formattedDate = formatter.print (value);
        	jgen.writeString (formattedDate);
	}
	else
		jgen.writeString("null");
    }

}
