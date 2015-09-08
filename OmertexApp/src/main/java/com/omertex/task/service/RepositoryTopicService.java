package com.omertex.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omertex.task.model.Topic;
import com.omertex.task.repository.TopicRepository;

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
