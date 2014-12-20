<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<HTML>
<HEAD>
<TITLE>javascript打印-打印页面设置-打印预览代码</TITLE>
<META http-equiv=Content-Type content="text/html; charset=UTF-8" />
<SCRIPT language=javascript> 
    function printsetup(){ 
    // 打印页面设置 
    wb.execwb(8,1); 
    } 
    function printpreview(){ 
    // 打印页面预览 
    	wb.execwb(7,1);  
    } 

    function printit() 
    { 
	    if (confirm('确定打印吗？')) { 
	    	wb.execwb(6,6) 
	    } 
    } 
    </SCRIPT>
<style type="text/css" media=print>
.noprint{display : none }
</style>

</HEAD>
<BODY>
<DIV align=center>
<OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>
法律是一系列的规则，通常需要经由一套制度来落实。法律以各种方式影响着每个人的日常生活与整个社会。古希腊哲学家亚里斯多德于西元前350年写道：“法治比任何一个人的统治来得更好。”在不同的地方，法律体系会以不同的方式来阐述人们的法律权利与义务。一种较一般的区分为大陆法系和英美法系两种。而有些国家则会以他们的宗教法条为其法律的基础。学者们从许多不同的角度来研究自然法，包括从法制史和哲学，或从社会科学的方面来探讨。法律的研究来自于对为何平等、公正和正义等问题的讯问。落实法律最重要的制度有司法、立法、行政和其官僚、军事和警力、法律职业以及公民社会等。
<p class="noprint">
<INPUT onclick=javascript:printit() type=button value=打印 name=button_print /> 
<INPUT onclick=javascript:printsetup(); type=button value=打印页面设置 name=button_setup /> 
<INPUT onclick=javascript:printpreview(); type=button value=打印预览 name=button_show />
</p>
</DIV>
</BODY>
</HTML>