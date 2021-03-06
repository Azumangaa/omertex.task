package com.omertex.task.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Topic extends BaseEntity<Long>
{
    @Id
    @GeneratedValue
    private Long id;

    @Column (name = "name")
    private String name;


    public Topic ()
    {

    }


    @Override
    public Long getId ()
    {
	return id;
    }


    public String getName ()
    {
	return name;
    }


    public static Builder getBuilder ()
    {
	return new Builder ();
    }

    public static class Builder
    {
	private Topic topic;


	public Builder ()
	{
	    topic = new Topic ();
	}


	public Builder name (String name)
	{
	    this.topic.name = name;
	    return this;
	}


	public Builder id (Long id)
	{
	    this.topic.id = id;
	    return this;
	}

	public Topic build ()
	{
	    return topic;
	}
    }
}
