package org.quickbundle.third.strutslayout.collection;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.collection.CollectionItemTag;

public class RmCollectionItemTag extends CollectionItemTag {
	
	protected boolean locked = false;
	
	public String getLocked() {
		return String.valueOf(locked);
	}

	public void setLocked(String locked_) {
		this.locked = "true".equalsIgnoreCase(locked_);
	}

	public int doEndLayoutTag() throws JspException {
		RmCollectionTag.tlItemTag.set(this);
		return super.doEndLayoutTag();
	}
}
