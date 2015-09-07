package com.omertex.task.topic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.omertex.task.topic.model.Topic;
import com.omertex.task.topic.repository.TopicRepository;

@Service
public class RepositoryTopicService
{
    private TopicRepository repository;
    private final Integer recordPerPage = 50;


    @Autowired
    public RepositoryTopicService (TopicRepository repository)
    {
	this.repository = repository;
    }


    public Page<Topic> getTopics (Integer pageNumber)
    {
	PageRequest pageRequest = new PageRequest (pageNumber - 1, recordPerPage, Sort.Direction.DESC, "creationTime");
	return repository.findAll (pageRequest);
    }


    public Topic addTopic (String topicName)
    {
	if (topicExists (topicName))
	    return null;
	else
	{
	    Topic.Builder builder = new Topic.Builder ();
	    Topic topic = builder.name (topicName).build ();
	    return repository.save (topic);
	}
    }


    public boolean topicExists (String topicName)
    {
	List<Topic> topic = repository.findByName (topicName);

	if (topic != null)
	    return true;
	else
	    return false;
    }
}
