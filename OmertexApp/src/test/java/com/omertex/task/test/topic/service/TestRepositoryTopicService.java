package com.omertex.task.test.topic.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.omertex.task.test.utils.TestUtils;
import com.omertex.task.topic.model.Topic;
import com.omertex.task.topic.repository.TopicRepository;
import com.omertex.task.topic.service.RepositoryTopicService;

public class TestRepositoryTopicService {

	@Test
	public void testGetTopics() {
		Page<Topic> page = TestUtils.createTopicPage();
		TopicRepository repo = mock(TopicRepository.class);
		PageRequest pageRequest = new PageRequest(0, 
				50, 
				Sort.Direction.DESC, "creationTime" );
		when(repo.findAll(pageRequest)).thenReturn(page);
		RepositoryTopicService service = new RepositoryTopicService(repo);
		
		Page<Topic> page2 =  service.getTopics(1);
		assertEquals(page2, page);
	}
	
	
	@Test
	public void testAddDuplicateTopic()
	{
		TopicRepository repo = mock(TopicRepository.class);
		Topic topic = Topic.getBuilder().name("Test").build();
		when(repo.findByName("Test")).thenReturn(new ArrayList<Topic>());
		when(repo.save(topic)).thenReturn(topic);
		
		RepositoryTopicService service = new RepositoryTopicService(repo);
		Topic topic2 = service.addTopic("Test");
		assertEquals(topic2, null);
	}
	
}
