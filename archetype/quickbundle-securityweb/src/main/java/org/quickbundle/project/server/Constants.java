/*
 * Licensed to JumpMind Inc under one or more contributor 
 * license agreements.  See the NOTICE file distributed
 * with this work for additional information regarding 
 * copyright ownership.  JumpMind Inc licenses this file
 * to you under the GNU Lesser General Public License (the
 * "License"); you may not use this file except in compliance
 * with the License. 
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see           
 * <http://www.gnu.org/licenses/>.
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License. 
 */
package org.quickbundle.project.server;

/**
 * 
 */
final public class Constants {

	private Constants() {
	}

	public static final String SYS_PROP_STANDALONE_WEB = "symmetric.standalone.web";

	public static final String SYS_PROP_WEB_DIR = "symmetric.default.web.dir";

	public static final String SYS_PROP_DEFAULT_HTTP_PORT = "symmetric.default.http.port";

	public static final String SYS_PROP_DEFAULT_HTTPS_PORT = "symmetric.default.https.port";

	public static final String SYS_PROP_CREATE_JMX_SERVER = "symmetric.default.create.jmx.server";

	public static final String ENCODING = "UTF-8";

	public static final String OVERRIDE_PROPERTIES_FILE_PREFIX = "symmetric.override.properties.file.";

	public static final String OVERRIDE_PROPERTIES_FILE_1 = OVERRIDE_PROPERTIES_FILE_PREFIX
			+ "1";

	public static final String SYSPROP_KEYSTORE = "sym.keystore.file";
	
    public static final String SYSPROP_KEYSTORE_TYPE = "sym.keystore.type";
    
    public static final String SYSPROP_KEYSTORE_PASSWORD = "javax.net.ssl.keyStorePassword";
    
    public static final String KEYSTORE_PASSWORD = "changeit";

    public final static String JMX_HTTP_CONSOLE_ENABLED = "jmx.http.console.for.embedded.webserver.enabled";
    
    public final static String JMX_HTTP_CONSOLE_LOCALHOST_ENABLED = "jmx.http.console.localhost.only.enabled";
    
    public static final String EMBEDDED_WEBSERVER_DEFAULT_ROLE="symmetric";
    
    static final public String LOG4J_DEFAULT_CONFIGURATION_KEY="log4j.configuration";
    
}