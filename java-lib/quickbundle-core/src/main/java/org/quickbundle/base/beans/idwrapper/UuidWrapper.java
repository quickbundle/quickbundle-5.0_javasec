package org.quickbundle.base.beans.idwrapper;

import org.quickbundle.base.beans.TableIdRuleVo;
import org.quickbundle.itf.base.IRmIdWrapper;
import org.quickbundle.tools.helper.RmUUIDHelper;

public class UuidWrapper implements IRmIdWrapper {

	protected TableIdRuleVo ruleVo = null;
	
	public UuidWrapper(TableIdRuleVo ruleVo) {
	}

	public void init() {

	}

	public String[] nextValue(int length) {
		String[] result = new String[length];
		for(int i=0; i<length; i++) {
			result[0] = RmUUIDHelper.generateUUID();
		}
		return result;
	}

}
