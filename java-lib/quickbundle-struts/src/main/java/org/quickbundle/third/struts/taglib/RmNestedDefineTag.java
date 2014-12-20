package org.quickbundle.third.struts.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.nested.bean.NestedDefineTag;

public class RmNestedDefineTag extends NestedDefineTag {
    /**
     * Retrieve the required property and expose it as a scripting variable.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
        // Enforce restriction on ways to declare the new value
        int n = 0;

        if (this.body != null) {
            n++;
        }

        if (this.name != null) {
            n++;
        }

        if (this.value != null) {
            n++;
        }

        if (n > 1) {
            JspException e =
                new JspException(messages.getMessage("define.value", id));

            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        // Retrieve the required property value
        Object value = this.value;

        if ((value == null) && (name != null)) {
            value =
                TagUtils.getInstance().lookup(pageContext, name, property, scope);
        }

        if ((value == null) && (body != null)) {
            value = body;
        }

        if (value == null) {
        	//QB-RM
//            JspException e =
//                new JspException(messages.getMessage("define.null", id));
//
//            TagUtils.getInstance().saveException(pageContext, e);
//            throw e;
        }

        // Expose this value as a scripting variable
        int inScope = PageContext.PAGE_SCOPE;

        try {
            if (toScope != null) {
                inScope = TagUtils.getInstance().getScope(toScope);
            }
        } catch (JspException e) {
            //log.warn("toScope was invalid name so we default to PAGE_SCOPE", e);
        }

        pageContext.setAttribute(id, value, inScope);

        // Continue processing this page
        return (EVAL_PAGE);
    }
}
