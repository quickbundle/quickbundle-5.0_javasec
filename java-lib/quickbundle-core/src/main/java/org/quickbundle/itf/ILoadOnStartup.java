package org.quickbundle.itf;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ILoadOnStartup {
    public abstract void init(HttpServlet servlet);

    public abstract void service(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)  throws ServletException;

    public abstract void destroy(HttpServlet servlet);
}
