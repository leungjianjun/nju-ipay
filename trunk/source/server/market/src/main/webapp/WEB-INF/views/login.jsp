<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="textml; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统登入</title>
		<script language="javascript" src="js/zsedu.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="x-ua-compatible" content="ie=7" />
		<title>iPay商场服务管理系统</title>
		<style type="text/css" title="">
			<!--
			*{margin:0;padding:0;}
			body{ background:url(../resources/image/login_bg.jpg) no-repeat center top #04305d;}

			.login {width:350px; height:192px; margin:168px auto 0 auto; padding:29px 20px 10px 20px; text-align:center;}

			img{vertical-align:bottom;}

			a img{border:0;}

			.cy { width:100%; margin:20px auto; font-size:12px; color:#FFF; line-height:25px; font-weight:normal; text-align:center;}

			form { border:0;}

			input { height:22px; background:none; font-size:14px; font-weight:bold; text-align:left; line-height:20px; border:1px #666 solid; margin:0; padding:0;}

			.input_s01 { width:232px; }

			.input_s02 { width:100px; }

			.input_s03 {  width:232px;}

			a { font-size:12px; color:#000; text-decoration:none;}

			a:hover { font-size:12px; color:#09F; text-decoration:underline;}

			td { padding:4px 0;}

			//html{filter:gray;}

			-->
		</style>
		<script type="text/javascript">			
			function onload() {
				document.all.nickname.focus();
			}
			function login() {
				//var username = document.all.nickname.value;
				//var password = document.all.userpsw.value;
				var username=document.getElementById("nickname").value;
				var password=document.getElementById("userpsw").value;

				if(username=="") {
					alert("请输入用户名!");
					document.getElementById("nickname").focus();
					return;
				}

				if(password=="") {
					alert("请输入密码!");
					document.getElementById("userpsw").focus();
					return;
				}
				var sysid = document.all.sysid.value;
				if(sysid==1) {
					//选择身份为客户时对应的Action
				} else if(sysid==2) {
					//选择身份为商场时对应的Action
				}
				form1.submit();
			}

			ns4=(document.layers)?true:false
			ie4=(document.all)?true:false

			function keyDown(e) {
				if(ns4) {
					var nKey=e.which;//var ieKey=0
				}
				if(ie4) {
					var ieKey=event.keyCode;
				}
				if(nKey==0||ieKey==13) {
					if(document.all.nickname.value=="") {
						alert("请输入用户名!");
						document.all.nickname.focus();
						return false;
					} else if(document.all.userpsw.value=="") {
						alert("请输入密码!");
						document.all.userpsw.focus();
						return false;
					} else {
						login();
					}
				}
			}

			document.onkeydown=keyDown
			if(ns4)
				document.captureEvents(Event.KEYDOWN);			
		</script>
	</head>
	<body onload="onload()">
		<div class="login">
			<form id="form1" name="form1" method="post" action="">
				<input type="hidden" name="type" id="type" value="1" />
				<input type="hidden" name="userpwd" id="userpwd" value="" />
				<input type="hidden" name="password" id="password" value="" />
				<input type="hidden" name="pwd" id="pwd" value="" />
				<input type="hidden" name="uName" id="uName" value="" />
				<input type="hidden" name="userName" id="userName" value="" />
				<input type="hidden" name="loginName" id="loginName" value="" />
				<input type="hidden" name="username" id="username" value="" />
				<input type="hidden" name="loginname" id="loginname" value="" />
				<input type="hidden" name="pass" id="pass" value="" />
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="right" style="font-size:14px; font-weight:bold; color:#333;">帐　号：</td>
						<td align="left">
						<input type="text" name="nickname" id="nickname" class="input_s01" />
						</td>
					</tr>
					<tr>

						<td align="right" style="font-size:14px; font-weight:bold; color:#333;">密　码：</td>
						<td align="left">
						<input type="password" name="userpsw" id="userpsw" class="input_s01" />
						</td>
					</tr>
					<tr>
						<td align="right" style="font-size:14px; font-weight:bold; color:#333;">我是：</td>
						<td align="left">
						<select name="sysid" id="sysid">
							<option value="1" >客户</option>
							<option value="2" >商场</option>
						</select>
						</td>
					</tr>
					<tr>

						<td align="right" style="font-size:14px; font-weight:bold; color:#333;"></td>
						<td align="left">&nbsp;</td>
					</tr>
					<tr>
						<td align="right"></td>
						<td align="left">
						<img src="../resources/image/btn_login.jpg" width="93" height="30" onclick="login()" style="cursor:pointer"/>
						&nbsp;
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="cy">
			<h2>Nanjing Univercity PopCorn</h2>
			<br/>
			<h2>Copyright@Popcorn2011</h2>
		</div>
	</body>
</html>


