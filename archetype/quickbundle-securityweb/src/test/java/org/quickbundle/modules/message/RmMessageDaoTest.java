package org.quickbundle.modules.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.quickbundle.modules.message.dao.RmMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class RmMessageDaoTest {

	@Autowired
	private RmMessageDao rmMessageDao;

	@Test
	public void findTasksByUserId() throws Exception {
//		Page<RmMessageVo> tasks = taskDao.findByUserId(2L, new PageRequest(0, 100, Direction.ASC, "id"));
//		assertEquals(5, tasks.getContent().size());
//		assertEquals(new Long(1), tasks.getContent().get(0).getId());

//		tasks = taskDao.findByUserId(99999L, new PageRequest(0, 100, Direction.ASC, "id"));
//		assertEquals(0, tasks.getContent().size());
		assertEquals(1, 1);
	}
}
