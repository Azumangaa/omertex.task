package com.omertex.task.test.service;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.omertex.task.config.PersistenceContext;
import com.omertex.task.config.TaskApplicationContext;
import com.omertex.task.config.WebAppContext;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (loader = AnnotationConfigWebContextLoader.class,
	classes = { WebAppContext.class, PersistenceContext.class, TaskApplicationContext.class })
@WebAppConfiguration
@Rollback
@Transactional (value = "transactionManager")
public abstract class BasicServiceTest
{

}
