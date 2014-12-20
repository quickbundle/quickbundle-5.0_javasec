package org.quickbundle.base.vo;

import java.io.Serializable;

import org.quickbundle.tools.helper.RmVoHelper;

public class RmValueObject implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * override method 'equals'
     * 
     * @param other 与本对象比较的其它对象
     * @return boolean 两个对象的各个属性是否都相等
     */
    public boolean equals(Object other) {
        return RmVoHelper.voEquals(this, other);
    }

    /**
     * override method 'hashCode'
     * 
     * @return int Hash码
     */
    public int hashCode() {
        return RmVoHelper.voHashCode(this);
    }
    
    /**
     * override method 'clone'
     *
     * @see java.lang.Object#clone()
     * @return Object 克隆后对象
     */
    public Object clone() {
        return RmVoHelper.voClone(this);
    }

    /**
     * override method 'toString'
     * 
     * @return String 字符串表示
     */
    public String toString() {
        return super.toString() + ":" + RmVoHelper.voToString(this);
    }
}
