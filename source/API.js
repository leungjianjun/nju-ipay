//=================================================================
//客户端和服务器交互API（ljj维护，大家评审）
//=================================================================
//说明：
//-----------------------------------------------------------------
//1.除个别请求使用post外，其他请求都是使用get请求。
//2.与个人信息有关的请求使用https，普通请求使用http
//3.客户端post给服务器的数据格式为json，服务器返回给客户端的数据格式也是json
//  http请求头注意使用Accept	application/json, text/javascript, *（/）*
//4.客户端和服务器端登录后使用session来保持登录，30分钟不进行任何操作，session
//  就失效，所以客户端要定时链接一个激活session的url
//5.客户端和服务器端的交互错误处理就采用http规范
//	  400 Bad request（错误请求）
//　　401.1 Logon failed（登录失败）
//　　401.2 Logon failed due to server configuration（由于服务器配置，登录失败）
//　　401.3 Unauthorized due to ACL on resource（由于资源上的 ACL，未授权）
//　　401.4 Authorization failed by filter（由于筛选器，授权失败）
//　　401.5 Authorization failed by ISAPI/CGI application （由于 ISAPI/CGI 应用程序，授权失败）
//　　403.1 Execute access forbidden（执行访问被禁止）
//　　403.2 Read access forbidden（读取访问被禁止）
//　　403.3 Write access forbidden（写入访问被禁止）
//　　403.4 SSL required（要求 SSL ）
//　　403.5 SSL 128 required（要求 SSL 128）
//　　403.6 IP address rejected （IP 地址被拒绝）
//　　403.7 Client certificate required（要求客户证书）
//　　403.8 Site access denied（站点访问被拒绝）
//　　403.9 Too many users（用户太多）
//　　403.10 Invalid configuration（无效的配置）
//　　403.11 Password change（密码更改）
//　　403.12 Mapper denied access（映射程序拒绝访问）
//　　403.13 Client certificate revoked（客户证书被取消）
//　　403.14 Directory listing denied（目录列表被拒绝）
//　　403.15 Client Access Licenses exceeded（超出客户访问许可证）
//　　403.16 Client certificate untrusted or invalid（客户证书不受信任或无效）
//　　403.17 Client certificate has expired or is not yet valid（客户证书已过期或无效）
//　　404 Not found（没有找到）
//　　404.1 Site not found（站点没有找到）
//　　405 Method not allowed（不允许使用该方法）
//　　406 Not acceptable（不接受）
//　　407 Proxy authentication required（要求代理身份验证）
//　　412 Precondition Failed（前提条件不正确）
//　　414 Request-URL too long（请求的 URL 太长）
//　　500 Internal server error（内部服务器错误）
//　　500.12 Application restarting（应用程序重新启动）
//　　500.13 Server too busy（服务器太忙）
//　　500.15 Requests for Global.asa not allowed（不允许请求 Global.asa）
//　　500-100.asp ASP 错误
//　　501 Not implemented（没有实施）
//　　502 Bad gateway（错误网关）
//    ...
//6.银行的安全交互不包括在此内
//7.更新比较频繁,请经常update这个文件或者访问在线地址
//=================================================================

//=================================================================

//=================================================================