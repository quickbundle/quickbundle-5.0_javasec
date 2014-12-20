package org.quickbundle.project.serializer;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import org.quickbundle.itf.IPopulateParser;
import org.quickbundle.tools.helper.RmDateHelper;
import org.quickbundle.tools.helper.math.RmNumberHelper;

public class RmPopulateParser implements IPopulateParser {

	public Object parse(Class clazz, Object value) {
		if(value == null) {
			return null;
		}
		Object result = value;
		if(clazz.equals(value.getClass())) {
			return result;
		}
		if (Timestamp.class.equals(clazz)) { //时间戳创建对象
			result = RmDateHelper.getTimestamp(value.toString());
		} else if (Date.class.equals(clazz)) { //SQL日期创建对象
			result = RmDateHelper.getSqlDate(value.toString());
		} else if (BigDecimal.class.equals(clazz)) { //BigDecimal
			//将千分位的","全部替换为""
			result = RmNumberHelper.formatFromThousandth(value.toString());
		} else if (String.class.equals(clazz)) { //字符串
			result = value.toString();
		} else if(long.class.equals(clazz) && value.toString().matches("^\\d{4}-\\d{2}-\\d{2}( \\d{2}:\\d{2}:\\d{2})?(\\.\\d+)?$")) {
			result = RmDateHelper.getTimestamp(value.toString()).getTime();
		} else if (short.class.equals(clazz)
				|| int.class.equals(clazz)
				|| long.class.equals(clazz)
				|| float.class.equals(clazz)
				|| double.class.equals(clazz)) {
			//如果空字符串-->基本类型,忽略
			if(value.toString().length() == 0) {
				result = null;
			}
		}
		return result;
	}

}
