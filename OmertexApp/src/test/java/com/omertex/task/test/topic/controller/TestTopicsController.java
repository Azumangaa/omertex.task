package com.omertex.task.test.topic.controller;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import com.omertex.task.config.WebAppContext;
import com.omertex.task.test.utils.TestUtils;
import com.omertex.task.topic.controller.TopicsController;
import com.omertex.task.topic.model.Topic;
import com.omertex.task.topic.service.RepositoryTopicService;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ContextConfiguration (classes = WebAppContext.class)
public class TestTopicsController
{

    @Test
    public void shouldShowTopics () throws Exception
    {
	Page<Topic> page = TestUtils.createTopicPage ();

	RepositoryTopicService service = mock (RepositoryTopicService.class);
	when (service.getTopics (1)).thenReturn (page);

	TopicsController controller = new TopicsController (service);

	MockMvc mvc = standaloneSetup (controller).setSingleView (new InternalResourceView ("/WEB-INF/jsp/topics.jsp"))
		.build ();

	mvc.perform (get ("/topics/1")).andExpect (view ().name ("topics"))
		.andExpect (model ().attributeExists ("topicList"))
		.andExpect (model ().attribute ("topicList", hasItems (page.getContent ().toArray ())))
		.andExpect (model ().attributeExists ("currentPage"))
		.andExpect (model ().attribute ("currentPage", page.getNumber () + 1))
		.andExpect (
			model ().attributeExists ("beginPage"))
		.andExpect (model ().attribute ("beginPage", Math.max (1, page.getNumber () + 1 - 5)))
		.andExpect (model ().attributeExists ("endPage")).andExpect (model ().attribute ("endPage",
			Math.min (Math.max (1, page.getNumber () + 1 - 5) + 10, page.getTotalPages ())));
    }

}
