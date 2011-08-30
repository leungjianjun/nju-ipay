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
// 错误统一处理风格
/*
 * 凡是请求错误的不管是客户端发送数据错误,服务器内部错误,网络问题的错误
 * 统一使用http的错误机制,这时客户端接收到的status code将不会是200(200
 * 是状态正常码),而是其他的数字,所以可以通过检查状态码决定错误类型.
 * 
 * 服务器出现任何的错误,只要能返回链接的,都会以json风格,在error里说明错
 * 误发生的具体原因.当然,如果你的错误是无法连接网络,服务器怎么能返回错误
 * 说"你的网络存在问题"呢,所以有些错误必定只能有客户端来说明
 */
//=================================================================
//用户相关

/**
 * 登录
 * 
 * url https://xxx.xxx.xxx.xxx:8443/j_spring_security_check
 * 方法：post
 * controller:spring security
 * 说明：1.登录使用ssl。2.密码不需要在客户端加密。3.已经spring security的部分代码重写，可以使用json登录
 * 
 */
var client_login = {
	account:"ljj",
	password:"123456"
}

var result_client_login = {
		status:true
}

//400 Bad request错误
var error_client_login = {//查看上面统一的错误处理风格
		status:false,
		error:"用户名或密码错误"//国际化问题没解决
}

/**
 * 登出
 * 
 * url http://xxx.xxx.xxx.xxx:8080/client/logout
 * 方法:get
 * controller:userController
 * 说明:1.如无特别说明,任何http连接都可以使用https连接,你可以设置客户端在任何时候都使用https连接
 *      2.登出就是删除session,如果session失效,你看到的应该是session失效的页面
 * 
 */
var client_logout = {}

var result_client_logout = {
		status:true
}

/**
 * 查看个人信息
 * 
 * url https://xxx.xxx.xxx.xxx:8443/client/GetInfo
 * 方法:get
 * controller:clientController
 */
var get_client_info = {}

var result_get_client_info = {
	account:"",
	realname:"",
	phonenum:""
}

/**
 * 设置跟人信息
 * 
 * url https://xxx.xxx.xxx.xxx:8443/client/SetInfo
 * 方法:post
 * controller:clientController
 * 
 */
var set_client_info = {
	account:"",//把要修改的写下来，不用修改的就不写
	phonenum:""
}

var result_set_client_info = {
	status:true
}

/**
 * 设置密码
 * 
 * url https://xxx.xxx.xxx.xxx:8443/client/changePassword
 * 方法：post
 * controller:userController
 */
var set_password = {
	oldPassword:"",
	newPassword:""	
}

var result_set_password = {
	status:true
}

//400 bad request
var error_set_info = {
	status:false,
	error:"参数错误"
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
 * 
 * url http://xxx.xxx.xxx.xxx:8080/client/findMarketId
 * 方法 get
 * (extention)
 * 说明:通过连入商场的网络从而使用商场ip访问服务器,根据服务器记录的ip返回商场id
 */
var find_market_by_ip = {}

var result_find_market_by_ip = {
	ip:"202.119.48.38",
	id:123
}

/*
 * 在搜索商店完毕后，用户点击某个商店就会进入商场详细信息的页面，
 * 这时就意味着用户已经确认在这个商场购物，要把商场id要用于整个
 * 购买过程。如果用户点返回，可以重新选择进入哪个商店。但是一旦
 * 购物车里有商品的时候要确认才能退出
 */

/**
 * 商店详情
 * 
 * url http://xxx.xxx.xxx.xxx:8080/client/MarketInfo?mid={market id}
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
 * url http://xxx.xxx.xxx.xxx:8080/client/MarketSpecialProducts?mid={market id}&page={pageNum}
 * 方法：get
 * 说明：1.特价商品是商场放在门口的那些商品，往往要帮某个品牌推销，或者清仓的那种 2 每页10个 
 *       3.商品图像url用相对地址,像/images/143_234324234_min.gif,要添加前缀http://xxx.xxx.xxx.xxx:8080
 */
var get_market_specialProducts = {}

var result_get_market_specialProducts = {
		specialProducts:[{name:"",oldPrice:12.5,newPrice:8.5,adWords:"",id:123,minImgUrl:""},
		                  {},//分别是：商品名，原价，现价，广告语，商品id（不是特价商品id），图像地址
		                  {}]
}

/**
 * 热门商品
 * 
 * url http://xxx.xxx.xxx.xxx:8080/client/MarketHotProducts?mid={market id}&page={pageNum}
 * 方法 get
 * 说明：1.热门商品就是卖的最多的商品 2.每页10个
 */
var get_market_hotProducts = {}

var result_get_market_hotProducts = {
		hotProducts:[{id:123,name:"",price:12.5,minImgUrl:""},//可以根据客户端需要的信息改动
		             {},
		             {}]
}

//=================================================================
//扫描商品
//
//只有在进入商店的时候才能查看购物车，才能扫描，购买商品等。
//所以进入商场前，顶部栏等操作按钮是暗的，在进入商场后，顶部栏才能亮起来
//

/**
 * 根据条形码获取商品信息
 * 
 * url http://xxx.xxx.xxx.xxx:8080/client/ProductInfoByCode?mid={market id}&code={barcode}
 * 方法 get
 * 说明：1.统一商品在不同商场的id是不同的，所以放入购物车时只要把商品id放进去进行了
 */
var get_productInfo_by_barcode ={}

var result_get_productInfo_by_barcode = {
		id:123,
		name:"",
		banner:"",
		barcode:"",
		minImgUrl:"",
		midImgUrl:"",//查看大图
		price:12.4,
		quantity:50,
		attributes:[{key:"",value:""},
		            {},
		            {}
		            //...
		            ]
}

/**
 * 根据条形码获取商品ID
 * 
 * url http://xxx.xxx.xxx.xxx:8080/client/ProductIdByCode?mid={market id}&code={barcode}
 * 方法 get
 * 说明：1.可通过组合各种api来定制你要得功能，如组合get_productId_by_barcode和get_productInfo_by_id
 *        得到和get_productInfo_by_barcode一样的功能
 */
var get_productId_by_barcode = {}

var result_get_productId_by_barcode = {
	id:123
}

/**
 * 获取商品详细信息
 * 
 * url http://xxx.xxx.xxx.xxx:8080/client/ProductInfoById?pid={product id}
 * 方法 get
 * 说明：1.可用于在购物车中的商品要查看详细信息
 */
var get_productInfo_by_id = {}

var result_get_productInfo_by_id = {
		name:"",
		banner:"",
		barcode:"",
		minImgUrl:"",
		midImgUrl:"",//查看大图
		price:12.4,
		quantity:50,
		attributes:[{key:"",value:""},
		            {},
		            {}
		            //...
		            ]			
}

//404 Bad Request
var error_get_product = {//查看上面统一的错误处理风格
	statu:false,
	error:"你搜索的商品不存在"
}

//=================================================================
//搜索商品

/**
 * 搜索商品
 * 
 * url http://xxx.xxx.xxx.xxx:8080/client/SearchProduct?mid={market id}&name={productName}&page={pageNum}
 * 方法 get
 * 说明 1.实时搜索，只要部分名字就能开始搜索 2.每页10个
 */
var search_product = {}

var result_search_product = {
	products:[{id:123,name:"",price:12.5,minImgUrl:""},//可以根据客户端需要的信息改动
		      {},
		      {}]	
}

//=================================================================

/* 
 * 扫描完商品后立即跳转到商品的详细信息页面,然后顾客可以点击购买等操作,
 * 把商品放入购物车. 购物车的相关才做与服务器并没有太多联系.购物车页面
 * 应该能对已购买的商品做操作,如增加数量,减少数量,查看商品详细信息等
 * 
 * 
 */

//=================================================================
//结算支付
//=================================================================
/*
 * 客户注册
 * 
 * 客户端注册时将获得一个加密证书，保存到本地
 * 
 * 说明：1.证书就是RSA非对称加密的私钥 2.对证书进行AES加密，加密密码是 帐号+支付密码
 * 作用：即使证书被偷走也无法取得证书内容
 * 
 * 细节：商场服务器把支付密码+帐号发送给银行，银行生成加密的私钥和公钥保存到数据库，等待下载
 */

/**
 * 获取加密的私钥
 * 
 * url https://xxx.xxx.xxx.xxx:8443/client/getEncryptPrivateKey
 * 方法 get
 * 说明：1.将获取一个即时生成的文件，然后就像下载文件一样下载，其实就是byte流 
 *       2.文件大小是656字节，使用aes 256加密的私钥。把下载的文件保存在一个地方。公钥大小162
 *       3.只要下载一次就行了，不需要每次用的时候又下载。
 *       4.私钥已经加密过了，保存的时候不要再加密了，用私钥的时候要先解密
 * 
 */
var get_encrypt_privatekey ={}

var result_get_encrypt_privatekey = {}

/*
 * 商家注册
 * 
 * 商家注册时将获得一个加密证书，保存到服务器
 */

/**
 * 发送订单
 * 
 * url https://xxx.xxx.xxx.xxx:8443/client/SendOrder
 * 方法 post
 */
var send_order = {
	mid:123,
	orders:[{pid:123,quantity:1},
	        {},
	        //商品id，商品的数量
	        ]	
}

/*
 * 返回的结果有信息和签名，签名是证明信息是从银行发出的，
 * 你需要使用银行的公钥验证签名和信息是否符合。source是一个string化的json，sign是签名的byte数组
 */
var result_send_order = {
	source:"{\"tranId\":32344,\"amount\":2343.4}",
	sign:"VrSoodiVPiw+hkCCiT7qBTMSL77mLQh34LTJRzRBUPDAEnIVtjdiLKx1K9XvhJSXpAmEWzKQH5w4e+vCHJI2aIhgzNDOoLpv4E+C+8R+YmykieKBh8HdS+Q/NubWmk7HVwasgSs9H2mI/CxOYuOdSEh65Htt/a7jSgvrshIFBaU="
}

/**
 * url http://xxx.xxx.xxx.xxx:8080/client/getMarketPublickey?mid={market id}
 * 
 * 返回market的公钥
 */
var get_market_publicKey = {}



/**
 * 支付
 * 
 * url https://xxx.xxx.xxx.xxx:8443/client/PayRequest
 * 
 * OI:"{\tranId\":123}"
 * PI:"{\tranId\":123}"
 */
var pay_request = {
	mid:12,
	encryptOI:"",//商场公钥
	encryptPI:"",//银行公钥
	OIMD:"",//客户端私钥对encryptOI的sign
	PIMD:"",//客户端私钥私钥sign
}

/*
 * statusCode为0就是成功，如果是其他的话就是失败，具体代码再定义吧
 */
var result_pay_request = {
	bankResult:"{\"tranId\":123,\"statusCode\":0}",
	sign:"fdsafdsaf8709df0ds98ufudsifuds90fuds"
}

var error_pay_request = {
	bankResult:"{\"tranId\":123,\"statusCode\":0}",
	sign:"fdsafdsaf8709df0ds98ufudsifuds90fuds"
}

//////////////////////////////////////////////
//废弃

/*
 * 商场服务器向银行申请支付订单
 * 
 * 商场服务器把账单信息用银行公钥加密,商店私钥作为信息摘要，发送给银行，然后银行用私钥打开，生成订单信息（OI）
 * 并用私钥加密，商场服务器把OI发送给客户端
 */

var result_send_order = {
	total:234.9,
	OI:"djfsdkjflkj4kl3jkltjkrengkjdngkjdfjglrtij34lj3lkj"
}

/*
 * 拿到OI后，使用银行公钥解密，查看信息是否符合，符合的话产生支付信息.要产生支付信息，必须用客户端的私钥
 * 
 * 说明：1.客户端私钥使用AES加密，必须填写支付密码才能解压私钥 2.银行的公钥直接签到程序里面
 */

var pay_order = {
	PI:""
}

/*
 * 商场服务器把oi，pi发给银行，让银行处理，返回结果(MI)给商场，商场根据银行返回的结果返回给客户端
 * 
 * 说明：1.银行的返回结果应该是代号的，而且用银行私钥加密
 */

var result_pay_order = {
	MI:""
}
//
//////////////////////////////////////////////

/*
 * 客户端根据结果提示用户下一步此操作
 */

//================================================================
//购买记录

/**
 * 
 * 
 */
var get_records = {}

var result_get_records = {
	records:[{id:134,createDate:"",marketName:"",total:""}]
}


//=================================================================
//安全说明“
/*
 * 1.客户端保存帐号和密码，用于自动登录的风险
 * 建议：1.在用户不勾选自动登录时，应该及时清除保存的帐号和密码。
 *       2.帐号和密码应该加密保存，加密和解密算法的程序要混淆，以免反编译
 *       3.客户端注意杀毒和防止恶意程序，这可能导致你的帐号信息被窃取
 *       
 * 2.网络连接的监听和信息窃取
 * 
 * 
 * 
 * 
 * 
 */
//=================================================================
