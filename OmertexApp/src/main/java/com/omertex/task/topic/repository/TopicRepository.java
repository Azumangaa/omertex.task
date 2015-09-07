package com.omertex.task.topic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omertex.task.topic.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer> 
{
	List<Topic> findByName(String name);
}
