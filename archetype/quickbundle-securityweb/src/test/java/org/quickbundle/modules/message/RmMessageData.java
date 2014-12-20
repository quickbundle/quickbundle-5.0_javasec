package org.quickbundle.modules.message;

import java.util.HashMap;
import java.util.List;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.modules.message.dao.RmMessageDao;
import org.quickbundle.modules.message.vo.RmMessageVo;
import org.quickbundle.project.test.RmWebTestCase;

/**
 * Task相关实体测试数据生成.
 * 
 * @author calvin
 */
public class RmMessageData {

	public static RmMessageVo randomTask() {
		RmMessageVo message = new RmMessageVo();
		message.setBiz_keyword("hello");
		return message;
	}

	public static void main(String[] args) {
		RmWebTestCase.init();
		RmMessageDao dao = RmBeanFactory.getBean(RmMessageDao.class);
		List result = dao.search(new HashMap<String, Object>() {
			{
				this.put("biz_keyword", "abc");
				this.put("send_id", "111");
			}
		}, null, 1, 100);
		System.out.println(result);
		// System.out.println(dao.getCount("1=1"));
		// {
		// List<RmMessageVo> lvo = new ArrayList<RmMessageVo>();
		// {
		// RmMessageVo vo = new RmMessageVo();
		// vo.setBiz_keyword("aaaaaa");
		// vo.setRecord_id("1111");
		// vo.setOwner_org_id("11111");
		// vo.setSender_id("11111");
		// lvo.add(vo);
		// }
		// {
		// RmMessageVo vo = new RmMessageVo();
		// vo.setBiz_keyword("bbbbb");
		// vo.setRecord_id("2222");
		// vo.setOwner_org_id("22222");
		// vo.setSender_id("22222");
		// lvo.add(vo);
		// }
		// System.out.println(dao.insert(lvo.get(0)));
		// System.out.println(dao.insert(lvo.toArray(new RmMessageVo[0])));
		// }
		// System.out.println(dao.list("", null, 1, 99, true));
		// System.out.println(dao.get("1000300100000000004"));
		//
	}
}
