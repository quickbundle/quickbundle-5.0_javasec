----------------解决乱码--------------------
工程支持国际化，所有编码都是UTF-8（以下方案二选一）：
1，eclipse/eclipse.ini，加上-Dfile.encoding=UTF-8 
2，Window-->Preferences-->General-->Workspace-->Text file encoding-->Other，设置UTF-8

---------------------html规范---------------------
#默认
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">

-------------优化服务器启动时间----------------
org.quickbundle.project.init.RmWebApplicationInit#line.74，忽略每次启动检测数据字典初始化	
//codeService.executeInitCodeTypeDataByXml();

将部署文件context.xml或部署上下文参数中的reloadable属性改为false
<Context path="/rmdemo" docBase="D:\apps\quickbundle-securityweb\WebContent" debug="0" reloadable="false" privileged="true">

-----------------Tomcat配置  server.xml------------------
    <Executor name="tomcatThreadPool" namePrefix="catalina-exec-" maxThreads="1500" minSpareThreads="4"/>
    <Connector port="9999" protocol="HTTP/1.1" 
               connectionTimeout="20000" 
               redirectPort="8443" useBodyEncodingForURI="true" executor="tomcatThreadPool" acceptCount="2000"/>

Eclipse.tomcat:
-server -Xms128m -Xmx1024m -XX:MaxPermSize=512M
-server -Xms128m -Xmx1024m -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -DcompilerSourceVM=1.5 -DcompilerTargetVM=1.5 -Djava.specification.version=1.5

catalina.bat
set JAVA_OPTS=%JAVA_OPTS% -server -Xms512m -Xmx1024m -XX:PermSize=512M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError
echo JAVA_OPTS=%JAVA_OPTS%

catalina.sh
JAVA_OPTS="$JAVA_OPTS -server -Xms512m -Xmx1024m -XX:PermSize=256m -XX:MaxPermSize=512m -XX:MaxNewSize=256m -XX:+HeapDumpOnOutOfMemoryError"
echo "JAVA_OPTS="$JAVA_OPTS

-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=3333 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
