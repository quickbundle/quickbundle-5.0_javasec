/*
 * $Id: ActionServlet.java 592630 2007-11-07 06:47:58Z pbenedict $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.quickbundle.third.struts.action;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.CharacterConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.struts.action.ActionServlet;
import org.quickbundle.tools.support.path.RmPathHelper;
import javax.servlet.ServletException;

import java.io.File;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p><strong>ActionServlet</strong> provides the "controller" in the
 * Model-View-Controller (MVC) design pattern for web applications that is
 * commonly known as "Model 2".  This nomenclature originated with a
 * description in the JavaServerPages Specification, version 0.92, and has
 * persisted ever since (in the absence of a better name).</p>
 *
 * <p>Generally, a "Model 2" application is architected as follows:</p>
 *
 * <ul>
 *
 * <li>The user interface will generally be created with server pages, which
 * will not themselves contain any business logic. These pages represent the
 * "view" component of an MVC architecture.</li>
 *
 * <li>Forms and hyperlinks in the user interface that require business logic
 * to be executed will be submitted to a request URI that is mapped to this
 * servlet.</li>
 *
 * <li>There can be <b>one</b> instance of this servlet class, which receives
 * and processes all requests that change the state of a user's interaction
 * with the application. The servlet delegates the handling of a request to a
 * {@link RequestProcessor} object. This component represents the "controller"
 * component of an MVC architecture. </li>
 *
 * <li>The <code>RequestProcessor</code> selects and invokes an {@link Action}
 * class to perform the requested business logic, or delegates the response to
 * another resource.</li>
 *
 * <li>The <code>Action</code> classes can manipulate the state of the
 * application's interaction with the user, typically by creating or modifying
 * JavaBeans that are stored as request or session attributes (depending on
 * how long they need to be available). Such JavaBeans represent the "model"
 * component of an MVC architecture.</li>
 *
 * <li>Instead of producing the next page of the user interface directly,
 * <code>Action</code> classes generally return an {@link ActionForward} to
 * indicate which resource should handle the response. If the
 * <code>Action</code> does not return null, the <code>RequestProcessor</code>
 * forwards or redirects to the specified resource (by utilizing
 * <code>RequestDispatcher.forward</code> or <code>Response.sendRedirect</code>)
 * so as to produce the next page of the user interface.</li>
 *
 * </ul>
 *
 * <p>The standard version of <code>RequestsProcessor</code> implements the
 * following logic for each incoming HTTP request. You can override some or
 * all of this functionality by subclassing this object and implementing your
 * own version of the processing.</p>
 *
 * <ul>
 *
 * <li>Identify, from the incoming request URI, the substring that will be
 * used to select an action procedure.</li>
 *
 * <li>Use this substring to map to the Java class name of the corresponding
 * action class (an implementation of the <code>Action</code> interface).
 * </li>
 *
 * <li>If this is the first request for a particular <code>Action</code>
 * class, instantiate an instance of that class and cache it for future
 * use.</li>
 *
 * <li>Optionally populate the properties of an <code>ActionForm</code> bean
 * associated with this mapping.</li>
 *
 * <li>Call the <code>execute</code> method of this <code>Action</code> class,
 * passing on a reference to the mapping that was used, the relevant form-bean
 * (if any), and the request and the response that were passed to the
 * controller by the servlet container (thereby providing access to any
 * specialized properties of the mapping itself as well as to the
 * ServletContext). </li>
 *
 * </ul>
 *
 * <p>The standard version of <code>ActionServlet</code> is configured based
 * on the following servlet initialization parameters, which you will specify
 * in the web application deployment descriptor (<code>/WEB-INF/web.xml</code>)
 * for your application.  Subclasses that specialize this servlet are free to
 * define additional initialization parameters. </p>
 *
 * <ul>
 *
 * <li><strong>config</strong> - Comma-separated list of context-relative
 * path(s) to the XML resource(s) containing the configuration information for
 * the default module.  (Multiple files support since Struts 1.1)
 * [/WEB-INF/struts-config.xml].</li>
 *
 * <li><strong>config/${module}</strong> - Comma-separated list of
 * Context-relative path(s) to the XML resource(s) containing the
 * configuration information for the module that will use the specified prefix
 * (/${module}). This can be repeated as many times as required for multiple
 * modules. (Since Struts 1.1)</li>
 *
 * <li><strong>configFactory</strong> - The Java class name of the
 * <code>ModuleConfigFactory</code> used to create the implementation of the
 * ModuleConfig interface. </li>
 *
 * <li><strong>convertNull</strong> - Force simulation of the Struts 1.0
 * behavior when populating forms. If set to true, the numeric Java wrapper
 * class types (like <code>java.lang.Integer</code>) will default to null
 * (rather than 0). (Since Struts 1.1) [false] </li>
 *
 * <li><strong>rulesets </strong> - Comma-delimited list of fully qualified
 * classnames of additional <code>org.apache.commons.digester.RuleSet</code>
 * instances that should be added to the <code>Digester</code> that will be
 * processing <code>struts-config.xml</code> files.  By default, only the
 * <code>RuleSet</code> for the standard configuration elements is loaded.
 * (Since Struts 1.1)</li>
 *
 * <li><strong>validating</strong> - Should we use a validating XML parser to
 * process the configuration file (strongly recommended)? [true]</li>
 *
 * <li><strong>chainConfig</strong> - Comma-separated list of either
 * context-relative or classloader path(s) to load commons-chain catalog
 * definitions from.  If none specified, the default Struts catalog that is
 * provided with Struts will be used.</li>
 *
 * </ul>
 *
 * @version $Rev: 592630 $ $Date: 2005-10-14 19:54:16 -0400 (Fri, 14 Oct 2005)
 *          $
 */
public class RmActionServlet extends ActionServlet {

	/**
     * <p>Initialize other global characteristics of the controller
     * servlet.</p>
     *
     * @throws ServletException if we cannot initialize these resources
     */
    protected void initOther()
        throws ServletException {
        String value;

        value = getServletConfig().getInitParameter("config");

        if (value != null) {
            //QB-RM add *.xml
            if(value.trim().length() > 0 && value.indexOf("*.xml") > -1){ //
                String finalValue = "";
                String[] aValue = value.trim().split(",");
                for (int j = 0; j < aValue.length; j++) {
                    String path = aValue[j];
                    if(path.trim().length() == 0) {
                        continue;
                    }
                    //判断当前这行是否*.xml
                    if(path.trim().endsWith("*.xml")) {
                        File fWarDirStr = RmPathHelper.getWarDir();
                        File fPath = new File(fWarDirStr + File.separator + (path.substring(0, path.length()-"*.xml".length())));
                        for (int i = 0; i < fPath.listFiles().length; i++) {
                            File fPathXml = fPath.listFiles()[i];
                            if(fPathXml.isFile() && fPathXml.toString().toLowerCase().endsWith(".xml")) {
                                String newPath = fPathXml.getAbsolutePath().substring((int)fWarDirStr.getAbsoluteFile().toString().length()).replaceAll("\\\\", "/");
                                finalValue += newPath + ",";
                            }
                        } 
                    } else {
                        finalValue += path + ",";
                    }
                }
                if(finalValue.endsWith(",")) {
                    finalValue = finalValue.substring(0, finalValue.length() - ",".length());
                }
                config = finalValue;
            } else {
                config = value;
            }
        }

        // Backwards compatibility for form beans of Java wrapper classes
        // Set to true for strict Struts 1.0 compatibility
        value = getServletConfig().getInitParameter("convertNull");

        if ("true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value)
            || "on".equalsIgnoreCase(value) || "y".equalsIgnoreCase(value)
            || "1".equalsIgnoreCase(value)) {
            convertNull = true;
        }

        if (convertNull) {
            ConvertUtils.deregister();
            ConvertUtils.register(new BigDecimalConverter(null),
                BigDecimal.class);
            ConvertUtils.register(new BigIntegerConverter(null),
                BigInteger.class);
            ConvertUtils.register(new BooleanConverter(null), Boolean.class);
            ConvertUtils.register(new ByteConverter(null), Byte.class);
            ConvertUtils.register(new CharacterConverter(null), Character.class);
            ConvertUtils.register(new DoubleConverter(null), Double.class);
            ConvertUtils.register(new FloatConverter(null), Float.class);
            ConvertUtils.register(new IntegerConverter(null), Integer.class);
            ConvertUtils.register(new LongConverter(null), Long.class);
            ConvertUtils.register(new ShortConverter(null), Short.class);
        }
    }
}
