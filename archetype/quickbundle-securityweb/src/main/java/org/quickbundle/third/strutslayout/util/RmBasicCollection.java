package org.quickbundle.third.strutslayout.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.third.strutslayout.collection.RmCollectionTag;

import fr.improve.struts.taglib.layout.collection.CollectionItemTag;
import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.util.BasicCollection;

public class RmBasicCollection extends BasicCollection {
	boolean beginRealOutput = false;
	List<StringBuffer> lThLocked = new ArrayList<StringBuffer>();
	List<StringBuffer> lTdsLocked = new ArrayList<StringBuffer>();
	
	List<StringBuffer> lTh = new ArrayList<StringBuffer>();
	List<StringBuffer> lTds = new ArrayList<StringBuffer>();
	StringBuffer sbEmpty = null;
	/**
	 * Display a special message if the collection is empty.
	 */
	public void doPrintEmptyCollection(StringBuffer out_buffer_, String in_message) {
		StringBuffer out_buffer = new StringBuffer();
	    out_buffer.append("<tr><td colspan=\"");
	    out_buffer.append(collectionTag.getHeaders().size());
	    out_buffer.append("\" class=\"");
	    out_buffer.append(styleClass);
	    out_buffer.append("\">&nbsp;");
	    out_buffer.append(in_message);
	    out_buffer.append("</td></tr>");
	    sbEmpty = out_buffer;
	}	
	/**
	 * Overrides the display ot the title.
	 * PENDING: use a nice layout.
	 */
	public void doPrintTitle(StringBuffer buffer, String title) {
		if (title!=null) {
			if (styleClass!=null) {
			    buffer.append("<p class=\"");
			    buffer.append(styleClass);
			    buffer.append("\">");
			}
			buffer.append(title);
			if (styleClass!=null) {
				buffer.append("</p>");								
			}
			//buffer.append("</td></tr><tr align=\"center\"><td colspan=\"2\">");
		}
	}	
	/**
	 * Override doStartPanel
	 */
	public void doStartPanel(StringBuffer buffer, String align, String width) {
		if (collectionTag.getWidth()!=null || collectionTag.getHeight()!=null) {
			buffer.append("<div id=\"div_layout\" style=\"");
			if (collectionTag.getWidth()!=null) {
				buffer.append("width:");
				String thisWidth = collectionTag.getWidth();
				if("100%".equals(thisWidth)) {
					thisWidth = IGlobalConstants.JSP_LAYOUT_WIDTH;
				}
				buffer.append(thisWidth);
				buffer.append(";");	
				if(!Boolean.parseBoolean(((RmCollectionTag)collectionTag).getLineWrap())) {
					buffer.append("overflow-x:auto;");
					buffer.append("overflow-y:auto;");
					buffer.append("padding-bottom:16px;");
				} else {
					buffer.append("overflow-x:hidden;");
				}
			}	
			if (collectionTag.getHeight()!=null) {
				buffer.append("height:");
				buffer.append(collectionTag.getHeight());
				buffer.append(";");	
				buffer.append("overflow-y:auto;");
			}			
			buffer.append("\">");
		}
		buffer.append("\n\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"");
		
		if (align!=null) {
			buffer.append(" align=\"");
			buffer.append(align);
			buffer.append("\"");
		}
		if (width!=null) { 
			buffer.append(" width=\"");
			buffer.append(width);
			buffer.append("\"");
		}
		if (styleClass!=null) {
			buffer.append(" class=\"");
			buffer.append(styleClass);
			buffer.append("\"");
		}
		buffer.append(">");
	}	
	/**
	 * Prepare to display the headers.
	 */
	public void doStartHeaders(StringBuffer out_buffer) {
		if(!beginRealOutput) {
			return;
		}
		out_buffer.append("<tr>");
	}
	public void doStartHeadersLocked(StringBuffer out_buffer) {
		if(!beginRealOutput) {
			return;
		}
		out_buffer.append("<tr style=\"position:relative;top:expression(this.offsetParent.scrollTop);\">");
	}
	/**
	 * Display a header.
	 */
	public void doPrintHeader(StringBuffer out_buffer, String in_header, String in_width, String in_sortUrl) {
		out_buffer.append("<th");
		if (styleClass!=null) {
			out_buffer.append(" class=\"");
		    out_buffer.append(styleClass);
		    out_buffer.append("\"");
		}
		if (in_width!=null) {
			out_buffer.append(" width=\"");
			out_buffer.append(in_width);	
			out_buffer.append("\"");
		}
		out_buffer.append(">");
		out_buffer.append(doPrintSortUrl(in_header, in_sortUrl));
		out_buffer.append("</th>");
	}
	
	/**
	 * Display an hyperlink to sort the column. 
	 */
	protected String doPrintSortUrl(String in_header, String in_sortUrl) {
		if (in_sortUrl == null) {
			return in_header;
		} else {
            String rm_order_str = "RM_NOT_EXIST_COLUMN";
            String tempIn_sortUrl = "";
            try {
                tempIn_sortUrl = in_sortUrl.substring(in_sortUrl.indexOf("RM_ORDER_STR=") + "RM_ORDER_STR=".length());
                if(tempIn_sortUrl.indexOf("&") > -1) {
                    tempIn_sortUrl = tempIn_sortUrl.substring(0, tempIn_sortUrl.indexOf("&"));
                }
                if(tempIn_sortUrl.indexOf(" ") > -1) {
                    tempIn_sortUrl = tempIn_sortUrl.substring(0, tempIn_sortUrl.indexOf(" "));
                }
                HttpServletRequest request = RmProjectHelper.getCurrentThreadRequest();
                rm_order_str = request.getParameter("RM_ORDER_STR").trim();
                if(rm_order_str.indexOf(" ") > -1) {
                    rm_order_str = rm_order_str.substring(0, rm_order_str.indexOf(" "));
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            StringBuffer lc_tempBuffer = new StringBuffer();
            lc_tempBuffer.append(in_header);  
            lc_tempBuffer.append("<a href=\""); //不留&nbsp;
            lc_tempBuffer.append(in_sortUrl);
            lc_tempBuffer.append("\">");
            if(rm_order_str == null || rm_order_str.trim().length() == 0 || !tempIn_sortUrl.trim().toLowerCase().matches("(\\w*?[, ])?" + rm_order_str.trim().toLowerCase() + "$")) {
                lc_tempBuffer.append("<font class=\"sort_none\" title=\"未对本列排序\">&#9633;</font>");
            } else if (in_sortUrl.trim().toLowerCase().indexOf(rm_order_str.trim().toLowerCase() + " desc")!=-1) {
                lc_tempBuffer.append("<font class=\"sort_asc\" title=\"当前是升序\">&#9650;</font>");
            } else{
                lc_tempBuffer.append("<font class=\"sort_desc\" title=\"当前是降序\">&#9660;</font>");
            }
            lc_tempBuffer.append("</a>");
			return lc_tempBuffer.toString();
		}
	}
	 
	/**
	 * Finish to render the headers.
	 */	
	public void doEndHeaders(StringBuffer out_buffer) {
		if(!beginRealOutput) {
			return;
		}
		out_buffer.append("\n</tr>");
	}
	public void doEndHeadersLocked(StringBuffer out_buffer) {
		if(!beginRealOutput) {
			return;
		}
		out_buffer.append("\n</tr>");
	}
	/**
	 * Prepare to render a line.
	 */	
	public void doStartItems(StringBuffer out_buffer) {
		if(!beginRealOutput) {
			return;
		}
		out_buffer.append("\n<tr");
		boolean lc_onclick = false;		
		if (collectionTag.getOnRowClick()!=null) {
			out_buffer.append(" onclick=\"");
			out_buffer.append(Expression.evaluate(collectionTag.getOnRowClick(), pageContext));
			out_buffer.append("\"");
			lc_onclick = true;
		}
		if (collectionTag.getOnRowDblClick()!=null) {
			out_buffer.append(" ondblclick=\"");
			out_buffer.append(Expression.evaluate(collectionTag.getOnRowDblClick(), pageContext));
			out_buffer.append("\"");
			lc_onclick = true;
		}
		if (collectionTag.getOnRowMouseOver()!=null) {
			out_buffer.append(" onmouseover=\"");
			out_buffer.append(Expression.evaluate(collectionTag.getOnRowMouseOver(), pageContext));
			out_buffer.append("\"");
			lc_onclick = true;
		}
		if (collectionTag.getOnRowMouseOut()!=null) {
			out_buffer.append(" onmouseout=\"");
			out_buffer.append(Expression.evaluate(collectionTag.getOnRowMouseOut(), pageContext));
			out_buffer.append("\"");
		}		
		if (lc_onclick) {
			out_buffer.append(" style=\"cursor:pointer;\"");			
		}
		out_buffer.append(">");
	}
	/**
	 * Render an element in the line.
	 */
	public void doPrintItem(StringBuffer out_buffer_, String in_item, String[] in_styleClass, String in_id) {
		StringBuffer out_buffer = new StringBuffer();
		out_buffer.append("\n\t<td");
		if (in_styleClass[0] != null) {
			out_buffer.append(" class=\"");
			out_buffer.append(in_styleClass[0]);
			out_buffer.append("\"");
		}
		if (in_styleClass.length>1 || !Boolean.parseBoolean(((RmCollectionTag)collectionTag).getLineWrap())) {
			out_buffer.append(" style=\"");
			//如果存在widthUnlocked，则强制不折行
			if(!Boolean.parseBoolean(((RmCollectionTag)collectionTag).getLineWrap())) {
				out_buffer.append("white-space:nowrap;");
			}
			//增大左右间距
			{
				out_buffer.append("padding: 0px 3px 0px 3px;");
			}
			for (int i = 0; i < in_styleClass.length-1; i++) {
				out_buffer.append(in_styleClass[i+1]);
			}
			out_buffer.append("\"");
		}
		
		Integer lc_int = (Integer) pageContext.getAttribute(CollectionItemTag.SPAN_KEY);
		if (lc_int!=null && lc_int.intValue()!=1) {
			out_buffer.append(" rowspan=\"");
			out_buffer.append(lc_int.intValue());
			out_buffer.append("\"");
		} 
		
		out_buffer.append(">");
		
		if (in_id!=null) {
			out_buffer.append("<div id=\"");
			out_buffer.append(in_id);
			out_buffer.append("\">");
		}		
		
		out_buffer.append(in_item);
		
		if (in_id!=null) {
			out_buffer.append("</div>");
		}

		out_buffer.append("</td>");
		//分别存储的列
		if(RmCollectionTag.tlItemTag.get() != null && Boolean.parseBoolean(RmCollectionTag.tlItemTag.get().getLocked())) { //锁定
			lTdsLocked.add(out_buffer);
		} else { //未锁定
			lTds.add(out_buffer);
		}
		RmCollectionTag.tlItemTag.remove();
	}
	/**
	 * Finish to render a line.
	 */	
	public void doEndItems(StringBuffer out_buffer) {
		if(!beginRealOutput) {
			return;
		}
		out_buffer.append("\n</tr>");
	}
	public void doEndPanel(StringBuffer buffer) {
		beginRealOutput = true;
		buffer.append("\n\t\t<tr>");
		if(lThLocked.size() > 0) {
			buffer.append("\n\t\t\t<td valign=\"top\"");
			buffer.append(">");
			appendColumn(buffer, lThLocked, lTdsLocked, false, Boolean.parseBoolean(((RmCollectionTag)collectionTag).getHeadLocked()));
			buffer.append("\n\t\t\t</td>");
		}
		if(lTh.size() > 0) {
			buffer.append("\n\t\t\t<td align=\"center\" valign=\"top\" ");
			buffer.append(">");
			//定义横向拖动的容器
			if(lThLocked.size() > 0) {
				buffer.append("<div id=\"strutslayout_div_scroll\" style=\"");
				buffer.append("float:left;");
				buffer.append("position:relative;");
				buffer.append("overflow-x:scroll;");
//				buffer.append("width:expression(this.parentNode.parentNode.parentNode.parentNode.parentNode.clientWidth - this.parentNode.parentNode.parentNode.children[0].children[0].clientWidth);");
//				buffer.append("width:expression(this.parentNode.offsetWidth);");
//				buffer.append("width:30%");
				buffer.append("\">");

			}
			appendColumn(buffer, lTh, lTds, true, Boolean.parseBoolean(((RmCollectionTag)collectionTag).getHeadLocked()));
			if(lThLocked.size() > 0) {
				buffer.append("</div>");
			}
			buffer.append("\n\t\t\t</td>");
		}
		buffer.append("\n\t\t</tr>\n\t</table>\n");
		if (collectionTag.getWidth()!=null || collectionTag.getHeight()!=null) {
			buffer.append("</div>");	
		}		
	}
	
	//追加未处理的列
	private void appendColumn(StringBuffer buffer, List<StringBuffer> listTh, List<StringBuffer> listTds, boolean appendEmpty, boolean headLocked) {
		buffer.append("\n\t\t\t\t<table width=\"100%\" cellspacing=\"1\"");
		buffer.append(" border=\"0\"");
		if (collectionTag.getStyleId()!=null) {
			buffer.append(" id=\"");
			buffer.append(collectionTag.getStyleId());
			buffer.append("\"");	
		}
		buffer.append(">\n");
		//开始th
		{
			if(headLocked) {
				doStartHeadersLocked(buffer);
			} else {
				doStartHeaders(buffer);
			}
			for(StringBuffer sb : listTh) {
				buffer.append(sb);
			}
			if(headLocked) {
				doEndHeadersLocked(buffer);
			} else {
				doEndHeaders(buffer);
			}
		}
		if(appendEmpty && sbEmpty != null) {
			buffer.append(sbEmpty);
		}
		//开始td
		{
			int index = 0;
			for(StringBuffer sb : listTds) {
				if(index % listTh.size() == 0) {
					doStartItems(buffer);
				}
				buffer.append(sb);
				if(index % listTh.size() == listTh.size()-1) {
					doEndItems(buffer);
				}
				index ++;
			}
		}
		buffer.append("\n\t\t\t\t</table>");

	}
	
	public void endFooter(StringBuffer in_buffer) {
		if(!beginRealOutput) {
			return;
		}
		in_buffer.append("</tr>\n");

	}
	public void printFooterElement(StringBuffer in_buffer, String in_element, int in_span) {
		in_buffer.append("<td ");
		if (in_span>1) {
			in_buffer.append(" colspan=\"");
			in_buffer.append(in_span);
			in_buffer.append("\"");
		}
		in_buffer.append(">");
		in_buffer.append(in_element);
		in_buffer.append("</td>");

	}
	public void startFooter(StringBuffer in_buffer) {
		if(!beginRealOutput) {
			return;
		}
		in_buffer.append("<tr>");
	}
	
	// ------------------- Math data renderer -----------------------
		
	/**
	 * Print end of math data
	 */
	public void endMathData(StringBuffer in_buffer) {
		in_buffer.append("</tr>\n");
	}
	
	public void renderMathData(StringBuffer in_buffer, String in_element, int in_span, String in_resultId, String in_styleClass) {
		in_buffer.append("<td");
		if (in_styleClass!=null){
			in_buffer.append(" class=\"");
			in_buffer.append(in_styleClass);
			in_buffer.append("\"");
		}
		if (in_span>1){
			in_buffer.append(" colspan=\"");
			in_buffer.append(in_span);
			in_buffer.append("\"");
		}
		if (in_resultId!=null && in_resultId.length()!=0){
			in_buffer.append(" id=\"" + in_resultId + "\"");
		}
		in_buffer.append(">");
		in_buffer.append(in_element == null ? "" : in_element);
		in_buffer.append("</td>\n");
	}
	
	public void startMathData(StringBuffer in_buffer) {
		in_buffer.append("<tr>\n");
	}
	
	// ---------------------- Multi level header renderer ---------------------
	
	public void renderMultiLevelHeader(StringBuffer in_buffer, String in_title, String in_sortUrl, String in_styleClass, int in_colspan, int in_rowspan, String in_width) {
		renderMultiLevelHeader(in_buffer, in_title, in_sortUrl, in_styleClass, in_colspan, in_rowspan, in_width, false);
	}
	
	
	public void renderMultiLevelHeader(StringBuffer in_buffer_, String in_title, String in_sortUrl, String in_styleClass, int in_colspan, int in_rowspan, String in_width, boolean locked) {
		StringBuffer in_buffer = new StringBuffer(); 
		in_buffer.append("\n\t<th");
		if (in_colspan!=1) {
			in_buffer.append(" colspan=\"");
			in_buffer.append(in_colspan);
			in_buffer.append("\"");
		}
		if (in_styleClass!=null) {
			in_buffer.append(" class=\"");
			in_buffer.append(in_styleClass);
			in_buffer.append("\"");
		}
		if (in_rowspan!=1) {
			in_buffer.append(" rowspan=\"");
			in_buffer.append(in_rowspan);
			in_buffer.append("\"");
		}
		if (in_width!=null) {
			in_buffer.append(" width=\"");
			in_buffer.append(in_width);
			in_buffer.append("\"");
		}
		in_buffer.append(">");
		in_buffer.append(doPrintSortUrl(in_title, in_sortUrl));
		in_buffer.append("</th>");
		if(locked) { //存储锁定的列
			lThLocked.add(in_buffer);
		} else {
			lTh.add(in_buffer);
		}
	}

	public void endMultiLevelHeaderRow(StringBuffer in_buffer) {
		if(!beginRealOutput) {
			return;
		}
		in_buffer.append("\n</tr>\n");
	}
	public void startMultiLevelHeaderRow(StringBuffer in_buffer) {
		if(!beginRealOutput) {
			return;
		}
		in_buffer.append("\n<tr valign=\"top\">");
	}
}