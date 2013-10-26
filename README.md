qb-archetype-security骨架工程安全加强版
================================================
A set of safe archetype with "Build Security In", 安全强化的JavaEE版

quickbundle-javawebsecurity
------------------------------------------------
对JavaEE标准版，以“Build Security In”的思想彻底重构之后的注重安全的应用骨架。适用场景：对安全有要求的企业应用、互联网应用后端。
### 特性
* 以JavaEE标准版(quickbundle-rmwebdemo)为基础 [【参考】](https://github.com/quickbundle/qb-archetype/blob/master/README.md)<br/>
* 集成Spring Security 3.1
* 以WebGoat-5.4[【参考】](https://www.owasp.org/index.php/Webgoat)为样本，全面修复可能存在缺陷的写法。
* 并针对OWASP Top 10[【参考】](https://www.owasp.org/index.php/Category:OWASP_Top_Ten_Project)做框架代码安全加固（重点是A3-跨站脚本-CSS，A4-不安全的直接对象引用，A7-功能级访问控制缺失，A8-跨站请求伪造-CSRF）
* 针对集成部署环节，形成安全实施模板（重点是A5-安全配置错误，A6-敏感信息泄漏），并编写ruby脚本做自动化检查。
适用场景：对安全有要求的企业应用、互联网应用后端。

		
quickbundle-phonegapsecurity
------------------------------------------------
### 特性
* 以phonegap版(quickbundle-phonegapdemo)为基础 [【参考】](https://github.com/quickbundle/qb-archetype/blob/master/README.md)<br/>
* 针对2013 OWASP Mobile Top 10[【参考】](https://www.owasp.org/index.php/OWASP_Mobile_Security_Project#tab=Top_Ten_Mobile_Risks)做框架代码安全加固

适用场景：对安全有要求的跨平台移动解决方案。
