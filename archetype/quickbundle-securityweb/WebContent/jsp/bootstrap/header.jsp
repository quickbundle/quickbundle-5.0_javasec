<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<head>
</head>
<body>
<div class="header navbar navbar-inverse navbar-fixed-top">
		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="navbar-inner">
			<div class="container-fluid">
				<!-- BEGIN LOGO -->
				<a class="brand" href="index.html"> <img
					src="<%=request.getContextPath()%>/images/bootstrap/logo.png"
					alt="logo" />
				</a>
				<!-- END LOGO -->
				<!-- BEGIN RESPONSIVE MENU TOGGLER -->
				<a href="javascript:;" class="btn-navbar collapsed"
					data-toggle="collapse" data-target=".nav-collapse"> <img
					src="<%=request.getContextPath()%>/images/bootstrap/menu-toggler.png"
					alt="" />
				</a>
				<ul class="nav pull-right">
					<li class="dropdown" id="header_inbox_bar"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"> <i
							class="icon-envelope"></i> <span class="badge">5</span>
					</a>
						<ul class="dropdown-menu extended inbox">
							<li>
								<p>You have 12 new messages</p>
							</li>
						</ul></li>
					<!-- END INBOX DROPDOWN -->
					<!-- BEGIN USER LOGIN DROPDOWN -->
					<li class="dropdown user"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <img alt=""
							src="<%=request.getContextPath()%>/images/bootstrap/avatar1_small.jpg" />
							<span class="username">Bob Nilson</span> <i
							class="icon-angle-down"></i>
					</a>
						<ul class="dropdown-menu">
							<li><a href="extra_profile.html"><i class="icon-user"></i>个人资料</a></li>
							<li><a href="<%=request.getContextPath()%>/RmLoginAction.do?cmd=logout&toUrl=/project/sample/login/login.jsp"><i class="icon-key"></i>退出</a></li>
						</ul></li>
					<!-- END USER LOGIN DROPDOWN -->
				</ul>
				<!-- END TOP NAVIGATION MENU -->
			</div>
		</div>
		<!-- END TOP NAVIGATION BAR -->
	</div>
	
</body>
</html>