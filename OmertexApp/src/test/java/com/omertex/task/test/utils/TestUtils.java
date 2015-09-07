package com.omertex.task.test.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.omertex.task.topic.model.Topic;

public class TestUtils
{
    public static Page<Topic> createTopicPage ()
    {
	final List<Topic> topics = createTopicList (50);

	Page<Topic> page = new Page<Topic> ()
	{

	    @Override
	    public int getNumber ()
	    {
		// TODO Auto-generated method stub
		return 0;
	    }


	    @Override
	    public int getSize ()
	    {
		// TODO Auto-generated method stub
		return 0;
	    }


	    @Override
	    public int getTotalPages ()
	    {
		// TODO Auto-generated method stub
		return 0;
	    }


	    @Override
	    public int getNumberOfElements ()
	    {
		// TODO Auto-generated method stub
		return 50;
	    }


	    @Override
	    public long getTotalElements ()
	    {
		// TODO Auto-generated method stub
		return 50;
	    }


	    @Override
	    public boolean hasPreviousPage ()
	    {
		// TODO Auto-generated method stub
		return false;
	    }


	    @Override
	    public boolean isFirstPage ()
	    {
		// TODO Auto-generated method stub
		return false;
	    }


	    @Override
	    public boolean hasNextPage ()
	    {
		// TODO Auto-generated method stub
		return false;
	    }


	    @Override
	    public boolean isLastPage ()
	    {
		// TODO Auto-generated method stub
		return true;
	    }


	    @Override
	    public Pageable nextPageable ()
	    {
		// TODO Auto-generated method stub
		return null;
	    }


	    @Override
	    public Pageable previousPageable ()
	    {
		// TODO Auto-generated method stub
		return null;
	    }


	    @Override
	    public Iterator<Topic> iterator ()
	    {
		// TODO Auto-generated method stub
		return null;
	    }


	    @Override
	    public List<Topic> getContent ()
	    {
		return topics;
	    }


	    @Override
	    public boolean hasContent ()
	    {
		// TODO Auto-generated method stub
		return true;
	    }


	    @Override
	    public Sort getSort ()
	    {
		// TODO Auto-generated method stub
		return null;
	    }

	};
	return page;
    }


    public static List<Topic> createTopicList (int i)
    {
	List<Topic> topics = new ArrayList<Topic> ();
	for (int x = 0; x < i; x++)
	{
	    topics.add (Topic.getBuilder ().name ("Topic " + i).build ());
	}
	return topics;
    }
}
