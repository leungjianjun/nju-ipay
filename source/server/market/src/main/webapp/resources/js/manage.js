/*
 * 用于处理页面事件
 */
window.onload= function() {
	function $(id) {
		return document.getElementById(id)
	}
		
	var menu=$("topTags").getElementsByTagName("ul")[0];//顶部菜单容器
	var tags=menu.getElementsByTagName("li");//顶部菜单
	var ck=$("leftMenu").getElementsByTagName("ul")[0].getElementsByTagName("li");//左侧菜单
	var j;
	//点击左侧菜单增加新标签
	for(i=0;i<ck.length;i++) {
		ck[i].onclick= function() {
			$("welcome").style.display="none"//欢迎内容隐藏
			clearMenu();
			this.style.background='url(../resources/image/f1_03.gif)'
			//循环取得当前索引
			for(j=0;j<8;j++) {
				if(this==ck[j]) {
					if($("p"+j)==null) {
						openNew(j,this.innerHTML);//设置标签显示文字
					}
					clearStyle();
					$("p"+j).style.background='url(../resources/image/tabbg1.gif)';
					$("p"+j).style.color ='#000000';
					clearContent();
					$("c"+j).style.display="block";
				}
			}
			return false;
		}
	}
	//增加或删除标签
	function openNew(id,name) {
		var tagMenu=document.createElement("li");
		tagMenu.id="p"+id;
		tagMenu.innerHTML=name+""+"<img src='../resources/image/off.gif' style='vertical-align:middle'/>";
		//标签点击事件
		tagMenu.onclick= function(evt) {
			clearMenu();
			ck[id].style.background='url(../resources/image/f1_03.gif)'
			clearStyle();
			tagMenu.style.background='url(../resources/image/tabbg1.gif)';
			tagMenu.style.color ='#000000';
			clearContent();
			$("c"+id).style.display="block";
		}
		//标签内关闭图片点击事件
		tagMenu.getElementsByTagName("img")[0].onclick= function(evt) {
			evt=(evt)?evt:((window.event)?window.event:null);
			if(evt.stopPropagation) {
				evt.stopPropagation()
			} //取消opera和Safari冒泡行为;
			this.parentNode.parentNode.removeChild(tagMenu);//删除当前标签
			var color=tagMenu.style.backgroundColor;
			//设置如果关闭一个标签时，让最后一个标签得到焦点
			
				if(tags.length-1>=0) {
					clearStyle();
					tags[tags.length-1].style.background='url(../resources/image/tabbg1.gif)';
					clearContent();
					var cc=tags[tags.length-1].id.split("p");
					$("c"+cc[1]).style.display="block";
					clearMenu();
					ck[cc[1]].style.background='url(../resources/image/tabbg1.gif)';
				} else {
					clearContent();
					clearMenu();
					$("welcome").style.display="block"
				}
			
		}
		menu.appendChild(tagMenu);
	}

	//清除菜单样式
	function clearMenu() {
		for(i=0;i<ck.length;i++) {
			ck[i].style.background='url(../resources/image/tabbg01.gif)';
		}
	}

	//清除标签样式
	function clearStyle() {
		for(i=0;i<tags.length;i++) {
			var dTag = menu.getElementsByTagName("li")[i];
			dTag.style.background='url(../resources/image/tabbg2.gif)';
			dTag.style.color ='#59814C';
			//alert(dTag.outerHTML);
		}
	}

	//清除内容
	function clearContent() {
		for(i=0;i<7;i++) {
			$("c"+i).style.display="none";
		}
	}
	
	//表格分页功能,每10条数据一页
	page=new Page(10,'','');
}
function showhide1() {
	if(document.getElementById("leftMenu").style.display=="block") {
		document.getElementById("leftMenu").style.display="none";
				
		
	} else {
		document.getElementById("leftMenu").style.display="block";		
		
	}
}

function showhide2(){
	if(document.getElementById("linkmenu").style.display=="block")
	{
		document.getElementById("linkmenu").style.display="none";
	}
	else 
	{
		document.getElementById("linkmenu").style.display="block";
	}
}

//用于更改页面语言，提供中文，英文支持
function changelan(val)
{
	var convert={
		购物资料:"Purcase Data",
		记录管理:"Record Management"
	};
	if(val=="Chinese")
	{
		//设置为中文页面		
		var sysmanage=document.getElementById("sys");
		sysmanage.innerHTML="<img id=\"leftMenuPic_img\" src=\"../resources/image/icon02.gif\" width=\"11\" height=\"6\" />系统管理"
	
		var link=document.getElementById("sys2");
		link.innerHTML="<img src=\"../resources/image/icon01.gif\" width=\"11\" height=\"6\" />一键链接";
		
		var tag_1=document.getElementById("c0");
		var tag_2=document.getElementById("c1");
		var tag_3=document.getElementById("c2");
		var tag_4=document.getElementById("c3");
		var tag_5=document.getElementById("c4");
		var tag_6=document.getElementById("c5");
		var tag_7=document.getElementById("c6");
	}
	else 
	{
		//设置为英文页面
		var sysmanage=document.getElementById("sys");
		sysmanage.innerHTML="<img id=\"leftMenuPic_img\" src=\"../resources/image/icon02.gif\" width=\"11\" height=\"6\" />System Management";
		
		var link=document.getElementById("sys2");
		link.innerHTML="<img src=\"../resources/image/icon01.gif\" width=\"11\" height=\"6\" />More Sites";
		
		var tag_1=document.getElementById("c0");
		var tag_2=document.getElementById("c1");
		var tag_3=document.getElementById("c2");
		var tag_4=document.getElementById("c3");
		var tag_5=document.getElementById("c4");
		var tag_6=document.getElementById("c5");
		var tag_7=document.getElementById("c6");	
		
	}
}


