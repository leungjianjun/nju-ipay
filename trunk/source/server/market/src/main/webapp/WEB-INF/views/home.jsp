<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="textml; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>iPay商场管理系统</title>
<script type="text/javascript" src="../resources/js/manage.js"></script>
<script type="text/javascript" src="../resources/js/eventhandle.js"></script>
<script type="text/javascript" src="../resources/js/jquery-1.6.js"></script>
<link rel="stylesheet" href="../resources/css/home_style.css"
	type="text/css" />
</head>
<body>
	<div id="whole_body">
	<div id="top">
		<div id="logo">
			<img src="../resources/image/Logo.png" width="145" height="30" alt="" />
		</div>
		<div id="xx">
			<div id="hy">
				<img src="../resources/image/marketmanage.png" alt="" />
			</div>
			<div id="topTags">
				<ul>
				</ul>
			</div>
		</div>
		<div id="hy_login">
			<form action="">
				<select name="languague" onchange="changelan(this.value)">
					<option value="Chinese">中文</option>
					<option value="English">English</option>
				</select>
			</form>
		</div>
	</div>
	<!---->
	<div id="zhong">
		<div id="zb">
			<div id="shb">
				<ul>
					<li class="aa" onmouseover="this.className='bb'"
						onmouseout="this.className='aa'"><a href="#">选项1</a>
					</li>
					<li class="cc" onmouseover="this.className='dd'"
						onmouseout="this.className='cc'"><a href="#">选项2</a>
					</li>
				</ul>
			</div>
			<div id="sys" onclick="showhide1()">
				<img id="leftMenuPic_img" src="../resources/image/icon02.gif"
					width="11" height="6" alt="" /> 系统管理
			</div>
			<div id="leftMenu">
				<div id="leftMenuPic">
					<img src="../resources/image/mun_bg.gif" alt="" />
				</div>
				<ul id="leftMenuItem">
					<li><a href="#">购物资料</a>
					</li>
					<li><a href="#">记录管理</a>
					</li>
					<li><a href="#">交易管理</a>
					</li>
					<li><a href="#">商品管理</a>
					</li>
					<li><a href="#">银行管理</a>
					</li>
					<li><a href="#">系统管理</a>
					</li>
					<li><a href="#">帮助信息</a>
					</li>
				</ul>
			</div>
			<div id="sys2" onclick="showhide2()">
				<img src="../resources/image/icon01.gif" width="11" height="6"
					alt="" /> 一键链接
			</div>
			<div id="linkmenu">
				<ul>
					<li><a href="http://www.citibank.com"><img
							src="../resources/image/link1.PNG" alt="" /> </a></li>
					<li><a href="http://www.citibank.com.cn"><img
							src="../resources/image/link2.PNG" alt="" /> </a></li>
				</ul>
			</div>
		</div>
		<div id="content">
			<div id="sm">
				你好，××× <span>点击更多 </span>
			</div>
			<div id="welcome" class="content" style="display: block;">
				<div align="center1">
					<p>&nbsp;</p>
					<p>
						<strong>欢迎使用商场管理系统！</strong>
					</p>
					<p>&nbsp;</p>
				</div>
			</div>
			<div id="c0" class="content">购物资料</div>
			<div id="c1" class="content">购物管理</div>
			<div id="c2" class="content">记录管理</div>
			<div id="c3" class="content">交易管理</div>
			<div id="c4" class="content">商品管理</div>
			<div id="c5" class="content">系统管理</div>
			<div id="c6" class="content">帮助信息</div>			
		</div>
	</div>
	</div>
</body>
</html>

