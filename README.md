                   == quickbundle 5.0 - JavaSec doc ==

- [Introduction 介绍](#introduction)
    - [Version 版本](#version)
    - [Changelog](Changelog.md)
- [Supported 兼容性](#supported-web-browsers)
- [Contributing 出一份力](#contributing)
- [Issues 发现问题](#issues)
- [Installation 安装](#installation)
- [Quick Start 快速上手](#quick-start)
- [Architecture 架构](#architecture)
- [Web Security Web安全性](#web-security)
- [Maintenance 运维](#maintenance)
    - [Creating Backups](#creating-backups)
    - [Restoring Backups](#restoring-backups)
    - [Automated Backups](#automated-backups)
    - [Shell Access](#shell-access)
- [Mobile](#mobile)
- [Upgrading 更新](#upgrading)
- [Announcements](https://github.com/quickbundle/javasec/issues/1)
- [References](#references)

# Introduction

JavaSec是一个以“快速开发”为目标的Web/Mobile应用开发平台，同时大幅增强了Web安全性。

#### 功能优势
* 支持父子表、多对多表的智能关系分析及生成
* JavaEE标准版的代码生成器，可以做企业应用、互联网后端、2B的门户、及网站前台(需要单独设计页面)
* 跨平台的Mobile端的代码生成器(即将发布)，用一套代码通吃iOS各版本、Android各版本、winphone，可用于企业移动端应用、2C的轻应用
* 安全强化JavaEE版的代码生成器(即将发布)，吸纳了“Build Security In” + OWASP + MASS，适用于对安全有要求的应用开发

#### 革命性的代码生成器新理念
* 支持本地模板源和远程模板源的扩展，这样能引入模板供应商(Template Provider)，使得代码生成以在线服务的方式提供
* 模板虚拟机的设计，可以扩展为支持多样的Model格式、多样的模板引擎、多样的处理流程


#### 架构及技术优势
* 松耦合的设计，不仅支持生成Java工程，也可以生成大多数语言和框架的工程，无需改代码，只需要加模板
* xslt 2.0语法格式的模板，功能强大，前景好（因为html5的xml良构会带来xslt的第二春）
* 纯maven + 纯OSGI的架构，高度组件化，支持一键编译

## Version

Current Version: **5.0.0**


# Supported

####Web Browsers
- Chrome (Latest stable version)
- Firefox (Latest released version)
- Safari 7+ (Know problem: required fields in html5 do not work)
- Opera (Latest released version)
- IE 10+

####Java
- Java 5.0 or greater

####Database
- Mysql
- Oracle
- Sql Server
- H2

####Middleware
- Tomcat
- WebSphere Application Server
- Weblogic

# Contributing

If you find this image useful here's how you can help:

- Send a Pull Request with your awesome new features and bug fixes
- Help new users with [Issues](https://github.com/quickbundle/javasec/issues) they may encounter

# Issues

If you found a issue, please post a issue request on the [issues](https://github.com/quickbundle/javasec/issues) page.

In your issue report please make sure you provide the following information:

- The host distribution, environment and release version.
- The log infomations.

# Installation

##编译打包
#### 一键编译quickbundle-5.0.0插件的方式一（推荐）
eclipse/plugins目录格式，直接复制到Eclipse下，安装快

		1，git clone https://github.com/quickbundle/javasec
		2，先安装基础jar包和必要组件到$M2_REPO。
			mvn install 【第二次执行可以加上离线参数-o，加快编译，即mvn install -o】如果报错，请执行多次
		3，打包。
			mvn package
		4，安装插件包。
			复制build/build-securityweb/target/eclipse目录到$ECLIPSE_HOME/links/org.quickbundle目录
		5，重启Eclipse即可
		
#### 一键编译quickbundle-5.0.0插件的方式二(不推荐)
updatesite格式的安装版，安装到Eclipse时较慢

		1，使用Linux下的ln -s(或windows下的junction)，把qb-archetype/quickbundle-rmwebdemo目录软链接到qb-core/eclipse-plugin/quickbundle-gp/t/j1目录。
		2，安装maven-3.0.5，在qb-core目录下，执行mvn install，即可安装到$M2_REPO/org/quickbundle/org.quickbundle.mda.updatesite/4.0.0/org.quickbundle.mda.updatesite-4.0.0.zip。

# Quick Start

Download lastest release.


# Architecture

##基础jar包和Eclipse插件源代码模块介绍

#### 包含基础jar包5个:
		java-lib/quickbundle-tools  基础工具jar包，是一些其它quickbundle-xxx.jar的基础
		java-lib/quickbundle-core  核心jar包，依赖于Spring
		java-lib/quickbundle-mybatis  mybatis扩展
		java-lib/quickbundle-springweb  spring mvc扩展
		java-lib/quickbundle-struts  struts1 扩展
	
#### Eclipse插件
		eclipse-plugin/org.quickbundle.mda.libs  osgi下的jar包接入点
		eclipse-plugin/quickbundle-gp  项目生成器
		eclipse-plugin/quickbundle-mvm  模型虚拟机
		eclipse-plugin/quickbundle-gc  代码生成器
		
		eclipse-plugin/org.quickbundle.mda.feature  Eclipse插件组合的feature工程
		eclipse-plugin/org.quickbundle.mda.updatesite  Eclipse插件的在线/离线安装包的组合工程
##骨架工程介绍
目前包含：JavaEE标准版、跨平台的Mobile端phonegap版

####quickbundle-securityweb

* JavaEE 2.5项目骨架，Maven规范
* 主框架是jQuery-1.9 + Html4 + Spring MVC 3.2 + Spring 3.2 + MyBatis 3.2
* 集成了Jackson-2.1(Json) + Apache CXF-2.5(web service) + JFreeChart-1.0(图表) + JasperReport-4.7(报表) + mail-1.4(邮件) + jxl-2.6(Excel) + dom4j-1.6(xml) + slf4j-1.7(日志) + jython-2.7(Python运行库)
* 内置组织权限(设计思想源自Martin Fowler的《分析模式》) + 分布式调度(基于quartz-2.1增强了管理界面) + 编码数据管理 + 附件管理 + 业务日志 + 业务锁
* 待增加：Activiti-5.13(工作流引擎，增强了组织适配器、流程管理器等) + MuleESB-3.4(ESB企业服务总线) + Drools-6.0(规则引擎)

适用场景：企业应用、互联网应用后端。

#Web Security
##逐鹿安全应用框架

安全强化的Java Web版(后端+PC端)，Mobile版。  
对JavaEE标准版，以“Build Security In”的思想彻底重构之后的注重安全的Java Web应用框架。适用场景：对安全有要求的企业应用、互联网应用后端。

#### 特性
* 以JavaEE标准版(quickbundle-rmwebdemo)为基础 [【引用】](https://github.com/quickbundle/qb-archetype/blob/master/README.md)，以OWASP Java Project[【参考】](https://www.owasp.org/index.php/Category:OWASP_Java_Project)为指导做安全方面的深度重构。
* 集成Spring Security 3.1[【参考】](http://docs.spring.io/spring-security/site/features.html)，兼容数据安全中间件Ralasafe v2[【参考】](http://http://www.ralasafe.cn/)
* 导入ESAPI-2.1的安全规范写法并精心定制，并以WebGoat-5.4[【参考】](https://www.owasp.org/index.php/Webgoat)为样本全面修复可能存在的安全缺陷，并针对OWASP Top 10[【参考】](https://www.owasp.org/index.php/Category:OWASP_Top_Ten_Project)做框架代码安全加固，重点是：

		A1-注入
		A2-失效的身份认证及会话管理
		A3-跨站脚本-CSS
		A4-不安全的直接对象引用
		A7-功能级访问控制缺失
		A8-跨站请求伪造-CSRF
		ESAPI-2.1加固

* 针对设计和开发阶段，整理一套以"Build Security In"为指导的风险分析规范，以及按具体业务做设计的方法论体系。
* 针对集成部署阶段，形成一套混淆编译规范及工具[【参考】](http://tools.pediy.com/decompilers.htm)，形成一套完整的安全实施过程模板，重点是：

		A5-安全配置错误
		A6-敏感信息泄漏
		混淆编译规范及工具
		Linux主机加固、中间件加固
		涵盖SSL、PKI的安全配置模板

* 针对运维阶段，编写一套ruby脚本做自动化的配置漏洞检查，并以主流漏洞扫描器(如AppScan、Acunetix WVS等)做辅助验证。并部署一套重构后的安全应用框架实例到靶机，持续攻防测试并持续改进。
* 将上述应用安全框架成果，提炼到基于MDA的代码生成器模板[【引用】](https://github.com/quickbundle/qb-core/tree/master/eclipse-plugin)中，并发布为Eclipse plugin形式(支持Eclipse3.7及以上)，“逐鹿安全应用框架”的用户(应用开发者)可以快速构建并生成Java应用代码。

# Mobile
## quickbundle-phonegapsecurity特性
* mobile appliation archetype, corporate with java-archetype backend server<br/>
* 提供一个跨平台移动终端方案，一套代码支持iOS、Android、Windows Phone等移动平台
* 主框架是PhoneGap-2.0 + jQuery-1.5 + jQuery Mobile-1.0 + jQuery JSONP-2.4 + Html5

适用场景：要求跨平台的移动端接入。

#### 如何一键编译phonegap：
		1，打开~/.m2/settings.xml，加入配置：
			<servers>
				<server>
					<id>phonegap-build</id>
					<username>yourmail@gmail.com</username>
					<password>yourpassword******</password> 
				</server>
			</servers>
		............
			<profiles>
				<profile>
					<id>dev</id>
					<properties>
						<phonegap-build.server>phonegap-build</phonegap-build.server>
					</properties>
		......
		2，免费build.phonegap.com帐号只能上传一个private app，因此执行mvn phonegap-build:scorch清理掉已存在的private app（谨慎操作）
		3，cd /quickbundle-phonegapdemo> mvn clean install -Dmaven.test.skip=true
		4，apk、xap等文件，在$M2_REPO/org/quickbundle/quickbundle-phonegapdemo/4.0.0

#### 安全特性
* 以phonegap版(quickbundle-phonegapdemo)为基础 [【引用】](https://github.com/quickbundle/qb-archetype/blob/master/README.md)
* 针对2013 OWASP Mobile Top 10[【参考】](https://www.owasp.org/index.php/OWASP_Mobile_Security_Project#tab=Top_Ten_Mobile_Risks)做框架代码安全加固。
* 形成混淆编译的操作工具及规范[【参考】](http://bbs.pediy.com/showthread.php?t=137112)。

# Upgrading



# References


