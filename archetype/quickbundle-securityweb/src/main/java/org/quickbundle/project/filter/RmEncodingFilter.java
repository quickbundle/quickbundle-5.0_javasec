package org.quickbundle.project.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.quickbundle.config.RmConfig;

/**
 * @author   
 * 实现编码过滤器
 */
public class RmEncodingFilter implements Filter {
	private String encoding = RmConfig.getSingleton().getDefaultEncode();
	private FilterConfig filterConfig;

	public RmEncodingFilter() {
		super();
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.encoding = filterConfig.getInitParameter("encoding");
		this.filterConfig = filterConfig;
	}

	/**
	 * 主要处理部分
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		try {
            //Tomcat设置get方式采用和post方式相同的编码 Connector useBodyEncodingForURI="true"
            //设置request编码
            request.setCharacterEncoding(encoding);
			filterChain.doFilter(request, response);
		} catch (ServletException sx) {
			filterConfig.getServletContext().log(sx.getMessage(),sx);
		} catch (IOException iox) {
			filterConfig.getServletContext().log(iox.getMessage());
		}
	}

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}
}