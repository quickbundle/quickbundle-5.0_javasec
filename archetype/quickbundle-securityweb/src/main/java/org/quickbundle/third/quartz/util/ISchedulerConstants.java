package org.quickbundle.third.quartz.util;

import java.util.HashMap;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;

/**
 * @author 白小勇
 */
public interface ISchedulerConstants {

    public static final String REQ_DATA_MAP = "req_data_map";
	
	//Spring配置的Scheduler名字
	String QUARTZ_SHEDULER="QuartzScheduler";
	
    public final static Map<String, String> MAP_TRIGGER_STATE = new HashMap<String, String>(){{
    	put(TriggerState.NONE.name() ,"无" );
    	put(TriggerState.NORMAL.name(), "启用" );
    	put(TriggerState.PAUSED.name(), "暂停" );
    	put(TriggerState.COMPLETE.name(), "完成" );
    	put(TriggerState.ERROR.name(), "错误" );
    	put(TriggerState.BLOCKED.name(), "阻塞" );
    }};
    
    public final static Map<String, String> MAP_MISFIRE_INSTRUCTION = new HashMap<String, String>(){{
    	put(String.valueOf(Trigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY), "IGNORE_MISFIRE");
    	put(String.valueOf(Trigger.MISFIRE_INSTRUCTION_SMART_POLICY), "SMART");
    	put(String.valueOf(CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW), "FIRE_ONCE_NOW");
    	put(String.valueOf(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING), "DO_NOTHING");
    }};
    
    enum Result {
    	OK("OK"),
    	ERROR("ER");
    	private String value;
    	Result(String value) {
    		this.value = value;
    	}
    	public String value() {
    		return value;
    	}
    }
    
	public enum EventType {
		RUN,
		MISFIRE,
		VETO
	}
}