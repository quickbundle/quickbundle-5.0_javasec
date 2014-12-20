package org.quickbundle.modules.log;

public interface ILogConstants {
	/**
	 * 日志表的操作类型定义
	 */
	public enum ActionType {
		INSERT("0", "^insert.*"),
		DELETE("1", "^delete.*"),
		UPDATE("2", "^(update|doUpdate).*"),
		QUERY("3", "^(query|get|find).*");
		private String value;
		private String pattern;
		ActionType(String value_, String pattern_) {
			value = value_;
			pattern = pattern_;
		}
		public String value() {
			return value;
		}
		public String pattern() {
			return pattern;
		}
	}
	
	public final static String ActionTypeDefault = "9";
}
