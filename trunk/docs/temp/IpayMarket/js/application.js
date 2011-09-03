/**
 * Created by JetBrains WebStorm.
 * User: softwware
 * Date: 11-9-2
 * Time: 上午10:43
 * To change this template use File | Settings | File Templates.
 */
$(document).ready(function(){
    // Dropdown menu for topbar nav
    // ===============================

    $("body").bind("click", function (e) {
        $('a.menu').parent("li").removeClass("open");
    });

    $("a.menu").click(function (e) {
        var $li = $(this).parent("li").toggleClass('open');
        return false;
    });

    $('#sidebar-nav > li > a').click(function(){
	    if ($(this).attr('class') != 'active'){
	      $('#sidebar-nav li ul').slideUp();
	      $(this).next().slideToggle();
	      $('#sidebar-nav li a').removeClass('active');
	      $(this).addClass('active');
		  return false;
	    }
	  });

    $('#close_sidebar').click(function(){
        $('.sidebar').hide();
        $(this).hide();
        $('#open_sidebar').show();
    })

     $('#open_sidebar').click(function(){
        $('.sidebar').show();
        $(this).hide();
        $('#close_sidebar').show();
    })


})
