package org.quickbundle.modules.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.quickbundle.modules.log.rmlog.service.IRmLogService;
import org.quickbundle.modules.log.rmlog.vo.RmLogVo;
import org.quickbundle.tools.support.buffer.AbstractTaskQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionLog2DbService extends AbstractTaskQueue<RmLogVo> {
	public ActionLog2DbService() {
		super();
	}
	
	/**
	 * @return 缓冲器名称
	 */
	public String getName() {
		return "业务日志缓冲器";
	}
	
	private IRmLogService logService;
	
	@Autowired
	public void setLogService(IRmLogService logService) {
		this.logService = logService;
	}

	@Override
	protected void flush(Queue<RmLogVo> buf) {
		try {
			List<RmLogVo> lvo = new ArrayList<RmLogVo>();
			RmLogVo vo;
			while((vo=buf.poll()) != null) {
				lvo.add(vo);
			}
			RmLogVo[] vos = lvo.toArray(new RmLogVo[0]);
			logService.insert(vos);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("flush(): " + e.toString());
		}
	}
}