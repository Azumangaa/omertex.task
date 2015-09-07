package com.omertex.task.topic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.omertex.task.topic.model.Topic;
import com.omertex.task.topic.service.RepositoryTopicService;

@Controller
public class TopicsController 
{
	private RepositoryTopicService service;
	
	@Autowired
	public TopicsController(RepositoryTopicService service) {
		this.service = service;
	}
	
	
	@RequestMapping(value="/topics/{pageNumber}", method=RequestMethod.GET)
	public String showTopics(
			Model model,
			@PathVariable("pageNumber") Integer pageNumber)
	{
		Page<Topic> page = service.getTopics(1);
		List<Topic> content = page.getContent();
		Integer current = page.getNumber() + 1; 
		Integer begin = Math.max(1, current - 5); 
		Integer end =  Math.min(begin + 10, page.getTotalPages());
		
		model.addAttribute("topicList", content);
		model.addAttribute("currentPage", current);
		model.addAttribute("beginPage", begin);
		model.addAttribute("endPage", end);
		
		return "topics";
	}
}
