逐鹿安全应用框架
================================================
安全强化的Java Web版(后端+PC端)，Mobile版。

quickbundle-javawebsecurity
------------------------------------------------
对JavaEE标准版，以“Build Security In”的思想彻底重构之后的注重安全的Java Web应用框架。适用场景：对安全有要求的企业应用、互联网应用后端。
### 特性
* 以JavaEE标准版(quickbundle-rmwebdemo)为基础 [【参考】](https://github.com/quickbundle/qb-archetype/blob/master/README.md)
* 集成Spring Security 3.1[【参考】](http://docs.spring.io/spring-security/site/features.html)，兼容数据安全中间件Ralasafe v2[【参考】](http://http://www.ralasafe.cn/)
* 以WebGoat-5.4[【参考】](https://www.owasp.org/index.php/Webgoat)为样本全面修复可能存在缺陷的写法，并针对OWASP Top 10[【参考】](https://www.owasp.org/index.php/Category:OWASP_Top_Ten_Project)做框架代码安全加固，重点是：
		A3-跨站脚本-CSS
		A4-不安全的直接对象引用
		A7-功能级访问控制缺失
		A8-跨站请求伪造-CSRF

* 针对设计和开发阶段，整理一套以"Build Security In"为指导的风险分析规范，以及按具体业务做设计的方法论体系。
* 针对集成部署阶段，形成一套混淆编译规范及工具[【参考】](http://tools.pediy.com/decompilers.htm)，形成一套完整的安全实施过程模板（重点加固A5-安全配置错误，A6-敏感信息泄漏，涵盖SSL、PKI配置模板）。
* 针对运维阶段，编写一套ruby脚本做自动化的配置漏洞检查，并以主流漏洞扫描器(如AppScan、Acunetix WVS等)做辅助验证。
适用场景：对安全有要求的企业应用、互联网应用后端。

		
quickbundle-phonegapsecurity
------------------------------------------------
### 特性
* 以phonegap版(quickbundle-phonegapdemo)为基础 [【参考】](https://github.com/quickbundle/qb-archetype/blob/master/README.md)
* 针对2013 OWASP Mobile Top 10[【参考】](https://www.owasp.org/index.php/OWASP_Mobile_Security_Project#tab=Top_Ten_Mobile_Risks)做框架代码安全加固。
* 形成混淆编译的操作工具及规范[【参考】](http://bbs.pediy.com/showthread.php?t=137112)。

适用场景：对安全有要求的跨平台移动解决方案。
