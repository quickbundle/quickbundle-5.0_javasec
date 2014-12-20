package org.quickbundle.project.common.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.quickbundle.util.RmSequenceCaseInsensitiveMap;


/**
 * 一个有序的key值忽略大小写的vo，实现了Map接口，key值必须是String，value可以是Object
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmCommonVo implements Cloneable, Serializable, Map {
    /**
     * mapAttribute 表示: 存放属性数据的Map
     */
    private Map<String, Object> mapAttribute;
    
    /**
     * 构造函数: 初始化属性，私有构造函数
     * 
     */
    public RmCommonVo() {
        this.mapAttribute = new RmSequenceCaseInsensitiveMap();
    }
    
    /**
     * 构造函数: 初始化属性，私有构造函数
     * 
     */
    public RmCommonVo(Map<String, Object> m) {
    	this();
    	for(Map.Entry<String, Object> en : m.entrySet()) {
    		put(en.getKey(), en.getValue());
    	}
    }

    /**
     * 功能: 为RmCommonVo增加属性，访问时有序(按属性加入的先后顺序)
     *
     * @param attributeName 属性名称
     * @param attributeValue 属性值
     */
    public void put(String key, Object value) {
    	if(value instanceof BigDecimal) {
    		value = value.toString();
    	}
    	mapAttribute.put(key, value);
    }
    
    /**
     * 功能: 获得RmCommonVo的属性String，null自动过滤为""
     *
     * @param key 属性名称
     * @return
     */
    public String getString(String key) {
        return getString(key, -1);
    }
    
    /**
     * 功能: 获得RmCommonVo的属性String，null自动过滤为""
     *
     * @param key 属性名称
     * @param length 截取前几位
     * @return
     */
    public String getString(String key, int length) {
    	if(mapAttribute.get(key) == null) {
    		return "";
    	} else if(length < 0){
    		return mapAttribute.get(key).toString();
    	} else {
    		String value = mapAttribute.get(key).toString();
    		if(value.length() <= length) {
    			return value;
    		} else {
    			return value.substring(0, length);
    		}
    	}
    }
    
    /**
     * 功能：获得RmCommonVo的属性值
     * 
     * @param key 属性名称
     * @return
     */
    public Object get(String key) {
        return mapAttribute.get(key);
    }
    
    /**
     * 功能: 删除属性
     *
     * @param attributeName 属性名称
     * @return
     */
    public boolean remove(String key) {
    	mapAttribute.remove(key);
        return false;
    }
    
	/**
	 * override method 'equals'
	 * 
	 * @param _other 与本对象比较的其它对象
	 * @return boolean 两个对象的各个属性是否都相等
	 */
	public boolean equals(Object _other) {
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof RmCommonVo)) {
			return false;
		}
		
		final RmCommonVo _cast = (RmCommonVo) _other;
		
		for(String key : mapAttribute.keySet()) {
		    Object thisValue = get(key);
		    if(thisValue == null && _cast.get(key) != null) {
		    	return false;
		    } else if(thisValue != null && !thisValue.equals(_cast.get(key))){
		    	return false;
		    }
		}
		return true;
	}

	/**
	 * override method 'hashCode'
	 * 
	 * @return int Hash码
	 */
	public int hashCode() {
		int _hashCode = 0;
		for(String key : mapAttribute.keySet()) {
		    String thisValue = this.getString(key);
			if (thisValue != null) {
				_hashCode = 29 * _hashCode + thisValue.hashCode();
			}
		}
		return _hashCode;
	}
	
	/**
	 * override method 'toString'
	 * 
	 * @return String 字符串表示
	 */
	public String toString() {
	    int index = 0; 
		StringBuilder rt = new StringBuilder();
		rt.append( super.toString() );
		for(String key : mapAttribute.keySet()) {
		    String thisValue = getString(key);
		    rt.append("\n");
		    rt.append(++index);
		    rt.append(": ");
		    rt.append(key);
		    rt.append("=");
		    rt.append(thisValue);
		}
		rt.append("\n");
		return rt.toString();
	}
	
	/**
	 * override method 'clone'
	 *
	 * @see java.lang.Object#clone()
	 * @return Object 克隆后对象
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException {
        super.clone();
        RmCommonVo vo = new RmCommonVo();
		for(String key : mapAttribute.keySet()) {
		    String thisValue = this.getString(key);
		    vo.put(key, thisValue);
		}
        return vo;
    }

	public void clear() {
		mapAttribute.clear();
	}

	public boolean containsKey(Object key) {
		return mapAttribute.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return mapAttribute.containsValue(value);
	}

	public Set entrySet() {
		return mapAttribute.entrySet();
	}

	public Object get(Object key) {
		return mapAttribute.get(key);
	}

    /**
     * 功能: 为RmCommonVo增加属性，访问时有序(按属性加入的先后顺序)
     *
     * @param key 属性名称
     * @param value 属性值
     */
	public Object put(Object key, Object value) {
		return mapAttribute.put(key == null ? null : key.toString(), value);
	}

	public boolean isEmpty() {
		return mapAttribute.isEmpty();
	}

	public Set keySet() {
		return mapAttribute.keySet();
	}


	public void putAll(Map m) {
		mapAttribute.putAll(m);
	}

	public Object remove(Object key) {
		return mapAttribute.remove(key);
	}

	public int size() {
		return mapAttribute.size();
	}

	public Collection values() {
		return mapAttribute.values();
	}


}