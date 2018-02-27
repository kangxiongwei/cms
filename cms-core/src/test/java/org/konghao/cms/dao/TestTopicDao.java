package org.konghao.cms.dao;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.konghao.basic.model.Pager;
import org.konghao.basic.test.util.AbstractDbUnitTestCase;
import org.konghao.basic.test.util.TestUtil;
import org.konghao.cms.model.Topic;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestTopicDao extends AbstractDbUnitTestCase{
	@Inject
	private SessionFactory sessionFactory;
	
	@Inject
	private ITopicDao topicDao;
	
	@Before
	public void setUp() throws SQLException, IOException, DatabaseUnitException {
		//此时最好不要使用Spring的Transactional来管理，因为dbunit是通过jdbc来处理connection，再使用spring在一些编辑操作中会造成事务shisu
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		this.backupAllTable();
		IDataSet ds = createDateSet("topic");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
	}
	
	@Test
	public void testFindByCts() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Pager<Topic> ts = topicDao.find(7, null, 1);
		Assert.assertEquals(ts.getTotal(), 2);
		List<Topic> ls = Arrays.asList(new Topic(1,"abababab1","aa|aaa",1,1,"管理员"),
					new Topic(4,"dededede1","",1,1,"文章发布人员"));
		TestUtil.assertListByClz(ls, ts.getDatas(), Topic.class,
				new String[]{"summary","content","user","publishDate",
			"createDate","cname","channel","channelPicId"});
	}


	@Test
	public void testSearchTopicByKeyword() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Pager<Topic> ts = topicDao.searchTopicByKeyword("aa");
		Assert.assertEquals(ts.getTotal(), 1);
		List<Topic> ls = Arrays.asList(new Topic(1,"abababab1","aa|aaa",1,1,"管理员"));
		TestUtil.assertListByClz(ls, ts.getDatas(), Topic.class,
				new String[]{"summary","content","user","publishDate",
			"createDate","cname","channel","channelPicId"});
	}

	@Test
	public void testSearchTopic() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Pager<Topic> ts = topicDao.searchTopic("b");
		Assert.assertEquals(ts.getTotal(), 2);
		List<Topic> ls = Arrays.asList(new Topic(1,"abababab1","aa|aaa",1,1,"管理员"),
				new Topic(3,"cdcdcdcd1","cd",1,0,"管理员"));
		TestUtil.assertListByClz(ls, ts.getDatas(), Topic.class,
				new String[]{"summary","content","user","publishDate",
			"createDate","cname","channel","channelPicId"});
	}

	@Test
	public void testFindRecommendTopic() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Pager<Topic> ts = topicDao.findRecommendTopic(7);
		Assert.assertEquals(ts.getTotal(), 2);
		List<Topic> ls = Arrays.asList(new Topic(1,"abababab1","aa|aaa",1,1,"管理员"),
					new Topic(4,"dededede1","",1,1,"文章发布人员"));
		TestUtil.assertListByClz(ls, ts.getDatas(), Topic.class,
				new String[]{"summary","content","user","publishDate",
			"createDate","cname","channel","channelPicId"});
	}
	
	
	@After
	public void tearDown() throws DatabaseUnitException, SQLException, IOException {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		this.resumeTable();
	}
}
