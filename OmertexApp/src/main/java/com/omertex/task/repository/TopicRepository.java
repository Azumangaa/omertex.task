package com.omertex.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omertex.task.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long>
{
    List<Topic> findByName (String name);
}
