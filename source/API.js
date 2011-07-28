﻿//=================================================================
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
//  http://code.google.com/p/nju-ipay/source/browse/trunk/source/API.js
//=================================================================
// url控制访问说明
/*
 *  /user/**	只要是用户就能访问
 *  /client/**	只有客户才能访问
 *  /market/**	只有商场才能访问
 *  /admin/**	只有管理员才能访问
 */
//=================================================================
/**
 * 登录
 * 
 * url https://xxx.xxx.xxx.xxx:8443/j_spring_security_check
 * 方法：post
 * data j_username={account}&j_password={password}
 * 
 * 说明：1.登录使用ssl。2.密码不需要在客户端加密。3.json里面不需要任何数据
 * 服务器原理：登录时有spring security处理安全问题，用户的权限，登录状态都由其控制，所以登录的时候会怪怪的
 * 
 */
var client_login = {}

var result_client_login = {
		status:true
}

var result_client_login = {
		status:false
}

/**
 * 登出
 * 
 * url http://xxx.xxx.xxx.xxx:8080/client/logout
 * 方法:get
 * 说明:1.如无特别说明,任何http连接都可以使用https连接,你可以设置客户端在任何时候都使用https连接
 *      2.登出就是删除session,如果session失效,你看到的应该是session失效的页面
 * 
 */
var client_logout = {}

var result_client_logout = {
		status:true
}

//================================================================
//进入商店

/**
 * 搜索商店
 * 
 * url http://xxx.xxx.xxx.xxx:8080/client/searchMarket?name={name}&page={pageNum}
 * 方法 get
 * 说明:1.不用打全名就能出现提示,实时提示商场名 2.每页显示10个,pageNum从1开始,搜索结果有可能小于10个
 */
var search_market = {}

var result_search_market = {
		markets:[{id:123,name:"苏果超市",location:"南京仙林大道。。。"},
		         {},
		         {},
		         //...
		         ]
}

/**
 * (extention)
 * 说明:通过连入商场的网络从而使用商场ip访问服务器,根据服务器记录的ip返回商场id
 */
var find_market_by_ip = {}

/*
 * 在搜索商店完毕后，用户点击某个商店就会进入商场详细信息的页面，
 * 这时就意味着用户已经确认在这个商场购物，要把商场id要用于整个
 * 购买过程。如果用户点返回，可以重新选择进入哪个商店。但是一旦
 * 购物车里有商品的时候要确认才能退出
 */

/**
 * 商店详情
 * 
 * url http://xxx.xxx.xxx.xxx:8080/client/MarketInfo?id={market id}
 * 方法：get
 * 说明
 */

var get_marketInfo = {}

var result_get_marketInfo = {
	name:"",
	location:"",
	introduction:"",
	servicePhone:"",
	complainPhone:"",
	createDate:""//是商场的注册时间，不是创建时间
}

/**
 * 特价商品
 * 
 * url http://xxx.xxx.xxx.xxx:8080/client/MarketSpecialProducs?id={market id}&page={pageNum}
 * 方法：get
 * 说明：1.特价商品是商场放在门口的那些商品，往往要帮某个品牌推销，或者清仓的那种 2 每页10个
 */
var get_market_specialProducts = {}

var result_get_market_specialProducts = {
		specialsProducts:[{name:"",oldPrice:12.5,nowPrice:8.5,adWords:"",pid:123},
		                  {},//分别是：商品名，原价，现价，广告语，商品id（不是特价商品id）
		                  {}]
}

/**
 * 热门商品
 * 
 * url http://xxx.xxx.xxx.xxx:8080/client/MarketHotProducts?id={market id}&page={pageNum}
 * 方法 get
 * 说明：1.热门商品就是卖的最多的商品
 */
var get_market_hotProducts = {}

var result_get_market_hotProducts = {
		hotProducts:[{},
		             {},
		             {}]
}

//=================================================================

//=================================================================











//=================================================================