<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.cipher.rmmessage.IRmMessageConstants" %>
<head>
</head>
<body>
<div class="page-sidebar nav-collapse collapse">
			<ul class="page-sidebar-menu">
				<li>
					<div class="sidebar-toggler hidden-phone"></div>
				</li>
				<li>
					<form class="sidebar-search">
						<div class="input-box">
							<a href="javascript:;" class="remove"></a> <input type="text"
								placeholder="Search..." /> <input type="button" class="submit"
								value=" " />
						</div>
					</form>
				</li>
				<li class=" "><a href="<%=request.getContextPath()%>/jsp/cipher_index.jsp"> <i
						class="icon-home"></i> <span class="title">首页</span>
				</a></li>
				<li class="start active "><a href="javascript:;"> <i
						class="icon-cogs"></i> <span class="title">控制台</span> <span
						class="selected"></span>
				</a>
					<ul class="sub-menu">
						<li><a href="<%=request.getContextPath()%>/message"><%=IRmMessageConstants.TABLE_NAME%></a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i
						class="icon-bookmark-empty"></i> <span class="title">功能1</span> <span
						class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="ui_general.html">功能1菜单</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i class="icon-table"></i>
						<span class="title">功能2</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="form_layout.html"> 功能2菜单</a></li>
						<li><a href="form_samples.html"> 功能2菜单</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i
						class="icon-briefcase"></i> <span class="title">功能3</span> <span
						class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="page_timeline.html"> <i class="icon-time">
							</i>功能3菜单
						</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i class="icon-gift"></i>
						<span class="title">功能4</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="extra_profile.html"> 功能4菜单</a></li>
					</ul></li>
					
					<li><a class="active" href="javascript:;"> <i
						class="icon-sitemap"></i> <span class="title">功能5</span> <span
						class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="javascript:;"> 功能5菜单 <span class="arrow"></span>
						</a>
							<ul class="sub-menu">
								<li><a href="#">功能5菜单</a></li>
								<li><a href="#">功能5菜单</a></li>
								<li><a href="#">功能5菜单</a></li>
							</ul></li>
						<li><a href="javascript:;"> 功能5菜单 <span class="arrow"></span>
						</a>
							<ul class="sub-menu">
								<li><a href="#">功能5菜单</a></li>
								<li><a href="#">功能5菜单</a></li>
								<li><a href="#">功能5菜单</a></li>
							</ul></li>
						<li><a href="#"> 功能5菜单 </a></li>
					</ul></li>
			</ul>
			</div>
</body>
</html>