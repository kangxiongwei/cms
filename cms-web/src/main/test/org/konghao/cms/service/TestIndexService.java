package org.konghao.cms.service;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.konghao.basic.model.SystemContext;
import org.konghao.cms.service.IIndexService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestIndexService {
	@Inject
	private IIndexService indexService;
	private String rp = "D:\\teach_source\\class2\\j2EE\\cms_2013\\cms-web\\src\\main\\webapp";
	@Test
	public void testGenerateTop() {
		SystemContext.setRealPath(rp);
		indexService.generateTop();
	}
	
	@Test
	public void testGenerateBody() {
		SystemContext.setRealPath(rp);
		indexService.generateBody();
	}
}
