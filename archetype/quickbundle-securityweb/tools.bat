@ECHO OFF
set QB_HOME=%CD%
CLS
COLOR 0A
GOTO MENU
:MENU
ECHO. 
ECHO.=-=-=-=-=-=-=-=-=-=Available command 可用的命令=-=-=-=-=-=-=-=-=-=
ECHO.
ECHO.                       1  Compile 编译
ECHO.
ECHO.                       2  Test 测试
ECHO. 
ECHO.                       3  Clean 清理
ECHO. 
ECHO.                       4  Package 打war包
ECHO. 
ECHO.                       5  Site 生成项目报告
ECHO. 
ECHO.                       6  Eclipse 升级为Eclipse工程
ECHO. 
ECHO.                       7  Tomcat 生成Tomcat热部署文件
ECHO. 
ECHO.                       8  Execute all 执行全部命令
ECHO.
ECHO.                       9  Exit 退出
ECHO.
CHOICE /C 123456789 /N /M "Please choice 请选择操作:"
IF ERRORLEVEL 1 IF NOT ERRORLEVEL 2 GOTO SERVICE1
IF ERRORLEVEL 2 IF NOT ERRORLEVEL 3 GOTO SERVICE2
IF ERRORLEVEL 3 IF NOT ERRORLEVEL 4 GOTO SERVICE3
IF ERRORLEVEL 4 IF NOT ERRORLEVEL 5 GOTO SERVICE4
IF ERRORLEVEL 5 IF NOT ERRORLEVEL 6 GOTO SERVICE5
IF ERRORLEVEL 6 IF NOT ERRORLEVEL 7 GOTO SERVICE6
IF ERRORLEVEL 7 IF NOT ERRORLEVEL 8 GOTO SERVICE7
IF ERRORLEVEL 8 IF NOT ERRORLEVEL 9 GOTO SERVICE8
IF ERRORLEVEL 9 IF NOT ERRORLEVEL 10 GOTO END
PAUSE

:SERVICE1
call mvn compile
GOTO MENU

:SERVICE2
call mvn test
GOTO MENU

:SERVICE3
call mvn clean
GOTO MENU

:SERVICE4
call mvn package
GOTO MENU

:SERVICE5
call mvn site
GOTO MENU

:SERVICE6
call mvn eclipse:eclipse
GOTO MENU

:SERVICE7
call mvn compile exec:java -Dexec.mainClass="org.quickbundle.third.middleware.BuildTomcatContextXml"
GOTO MENU

:SERVICE8
call mvn compile test clean package site eclipse:eclipse exec:java -Dexec.mainClass="org.quickbundle.third.middleware.BuildTomcatContextXml"
GOTO MENU

PAUSE
:END
