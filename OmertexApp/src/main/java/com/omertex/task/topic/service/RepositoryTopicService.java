package com.omertex.task.topic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omertex.task.topic.model.Topic;
import com.omertex.task.topic.repository.TopicRepository;

@Service
public class RepositoryTopicService
{
    private TopicRepository repository;


    @Autowired
    public RepositoryTopicService (TopicRepository repository)
    {
	this.repository = repository;
    }


    public Topic getTopic (Long id)
    {
	return repository.findOne (id);
    }

}
